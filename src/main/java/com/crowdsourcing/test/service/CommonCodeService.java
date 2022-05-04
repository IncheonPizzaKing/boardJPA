package com.crowdsourcing.test.service;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.controller.form.CommonCodeForm;
import com.crowdsourcing.test.domain.*;
import com.crowdsourcing.test.repository.CommonCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommonCodeService {

    private final CommonGroupService commonGroupService;
    private final CommonCodeRepository commonCodeRepository;

    /**
     * 공통 코드 저장
     * @param commonCodeForm
     */
    @Transactional
    public void save(CommonCodeForm commonCodeForm) {
        CommonCode commonCode = CommonCode.builder()
                .code(commonCodeForm.getCode())
                .groupCode(commonCodeForm.getCommonGroupCode())
                .commonGroup(commonGroupService.findById(commonCodeForm.getCommonGroupCode()))
                .codeName(commonCodeForm.getCodeName())
                .codeNameKor(commonCodeForm.getCodeNameKor())
                .description(commonCodeForm.getDescription())
                .isUse(commonCodeForm.getUse())
                .build();
        commonCodeRepository.save(commonCode);
    }

    /**
     * 공통 코드 삭제
     * @param code
     */
    @Transactional
    public void remove(String code) {
        String[] codes = code.split("_");
        CommonCode commonCode = findById(new CommonCodeId(codes[0], codes[1]));
        commonCodeRepository.delete(commonCode);
    }

    /**
     * 공통 코드 정보 수정
     * @param code
     * @param isUse
     * @param description
     */
    @Transactional
    public void update(String code, Boolean isUse, String description) {
        String[] codes = code.split("_");
        CommonCode commonCode = findById(new CommonCodeId(codes[0], codes[1]));
        commonCode.setUse(isUse);
        commonCode.setDescription(description);
    }

    /**
     * 공통 코드 검색(id)
     * @param id
     * @return
     */
    public CommonCode findById(CommonCodeId id) {
        return commonCodeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * 공통 코드 검색(groupCode)
     * @param groupCode
     * @return
     */
    public List<CommonCode> findByGroupCode(String groupCode) {
        return commonCodeRepository.findByGroupCodeEquals(groupCode);
    }

    /**
     * 조건에 맞는 공통 코드 탐색
     * @param commonCodeSearch
     * @param pageable
     * @return
     */
    public Page<CommonCode> findCommonCode(BoardSearch commonCodeSearch, Pageable pageable) {
        String commonCodeNameKor = commonCodeSearch.getSearch();
        String types = commonCodeSearch.getTypes();
        if(!StringUtils.hasText(commonCodeNameKor)) {
            commonCodeNameKor = "";
        }
        if(!StringUtils.hasText(types) || types.equals("none")) {
            types = "";
            return commonCodeRepository.findByCodeNameKorContaining(commonCodeNameKor, pageable);
        } else {
            CommonGroup commonGroup = commonGroupService.findById(types);
            return commonCodeRepository.findByCodeNameKorContainingAndCommonGroupEquals(commonCodeNameKor, commonGroup, pageable);
        }
    }

    /**
     * 공통 코드 수정페이지 접속시
     * @param code
     * @return
     */
    public CommonCodeForm updateCommonCodeForm(String code) {
        String codeOne[] = code.split("_");
        CommonCode commonCode = (CommonCode) findById(new CommonCodeId(codeOne[0], codeOne[1]));
        CommonCodeForm form = CommonCodeForm.builder()
                .code(commonCode.getCode())
                .codeName(commonCode.getCodeName())
                .codeNameKor(commonCode.getCodeNameKor())
                .commonGroup(commonCode.getCommonGroup())
                .use(commonCode.isUse())
                .description(commonCode.getDescription())
                .build();

        return form;
    }
}

package com.crowdsourcing.test.service;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.controller.form.CommonCodeForm;
import com.crowdsourcing.test.domain.*;
import com.crowdsourcing.test.repository.CommonCodeRepository;
import com.crowdsourcing.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommonCodeService {

    private final CommonGroupService commonGroupService;
    private final CommonCodeRepository commonCodeRepository;

    /**
     * 사용자 저장, 삭제
     */
    @Transactional
    public void save(CommonCodeForm commonCodeForm) {
        CommonCode commonCode = CommonCode.builder()
                .code(commonCodeForm.getCode())
                .groupCode(commonCodeForm.getCommonGroupCode())
                .codeName(commonCodeForm.getCodeName())
                .codeNameKor(commonCodeForm.getCodeNameKor())
                .description(commonCodeForm.getDescription())
                .isUse(commonCodeForm.getUse())
                .build();
        CommonGroup commonGroup = commonGroupService.findById(commonCodeForm.getCommonGroupCode());
        commonCode.addCommonGroup(commonGroup);
    }

    @Transactional
    public void remove(String code) {
        String[] codes = code.split("_");
        CommonCode commonCode = findById(new CommonCodeId(codes[0], codes[1]));
        commonCodeRepository.delete(commonCode);
    }
    @Transactional
    public void update(String code, Boolean isUse, String description) {
        String[] codes = code.split("_");
        CommonCode commonCode = findById(new CommonCodeId(codes[0], codes[1]));
        commonCode.setUse(isUse);
        commonCode.setDescription(description);
    }

    /**
     * 사용자 한 명 검색(id)
     * @return
     */
    public CommonCode findById(CommonCodeId id) {
        return commonCodeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

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
}

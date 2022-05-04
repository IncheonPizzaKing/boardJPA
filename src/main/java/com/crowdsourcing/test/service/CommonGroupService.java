package com.crowdsourcing.test.service;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.controller.form.CommonGroupForm;
import com.crowdsourcing.test.domain.CommonGroup;
import com.crowdsourcing.test.repository.CommonGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommonGroupService {

    private final CommonGroupRepository commonGroupRepository;

    /**
     * 공통 그룹 저장
     */
    @Transactional
    public void save(CommonGroupForm commonGroupForm) {
        CommonGroup commonGroup = CommonGroup.builder()
                .groupCode(commonGroupForm.getGroupCode())
                .groupName(commonGroupForm.getGroupName())
                .groupNameKor(commonGroupForm.getGroupNameKor())
                .description(commonGroupForm.getDescription())
                .isUse(commonGroupForm.getIsUse())
                .build();
        commonGroupRepository.save(commonGroup);
    }

    /**
     * 공통 그룹 삭제
     * @param groupCode
     */
    @Transactional
    public void remove(String groupCode) {
        commonGroupRepository.delete(findById(groupCode));
    }

    /**
     * 공통 그룹 수정
     * @param code
     * @param isUse
     * @param description
     */
    @Transactional
    public void update(String code, Boolean isUse, String description) {
        CommonGroup commonGroup = findById(code);
        commonGroup.setUse(isUse);
        commonGroup.setDescription(description);
    }

    /**
     * 조건에 맞는 공통 그룹 탐색
     * @param commonGroupSearch
     * @param pageable
     * @return
     */
    public Page<CommonGroup> findCommonGroup(BoardSearch commonGroupSearch, Pageable pageable) {
        String commonGroupNameKor = commonGroupSearch.getSearch();
        if(!StringUtils.hasText(commonGroupNameKor)) {
            commonGroupNameKor = "";
        }
        return commonGroupRepository.findByGroupNameKorContaining(commonGroupNameKor, pageable);
    }

    /**
     * 공통 그룹 한 개 검색(id)
     * @return
     */
    public CommonGroup findById(String groupCode) {
        return commonGroupRepository.findById(groupCode).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * 공통 그룹 전체 검색
     * @return
     */
    public List<CommonGroup> findAll() {
        return commonGroupRepository.findAll();
    }

    /**
     * 공통 그룹 수정 페이지 접속시
     * @param code
     * @return
     */
    public CommonGroupForm updateCommonGroupForm(String code) {
        CommonGroup commonGroup = (CommonGroup) findById(code);
        CommonGroupForm form = CommonGroupForm.builder()
                .groupCode(commonGroup.getGroupCode())
                .groupName(commonGroup.getGroupName())
                .groupNameKor(commonGroup.getGroupNameKor())
                .description(commonGroup.getDescription())
                .isUse(commonGroup.isUse())
                .build();

        return form;
    }
}

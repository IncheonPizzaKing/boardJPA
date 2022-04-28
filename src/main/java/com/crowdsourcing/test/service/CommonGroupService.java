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
     * 공통코드 저장, 삭제
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
    @Transactional
    public void remove(String groupCode) {
        commonGroupRepository.delete(findById(groupCode));
    }

    @Transactional
    public void update(String code, Boolean isUse, String description) {
        CommonGroup commonGroup = findById(code);
        commonGroup.setUse(isUse);
        commonGroup.setDescription(description);
    }

    public Page<CommonGroup> findCommonGroup(BoardSearch commonGroupSearch, Pageable pageable) {
        String commonGroupNameKor = commonGroupSearch.getSearch();
        if(!StringUtils.hasText(commonGroupNameKor)) {
            commonGroupNameKor = "";
        }
        return commonGroupRepository.findByGroupNameKorContaining(commonGroupNameKor, pageable);
    }

    /**
     * 공통코드 한 개 검색(id)
     * @return
     */
    public CommonGroup findById(String groupCode) {
        return commonGroupRepository.findById(groupCode).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * 공통코드 전체 검색
     * @return
     */
    public List<CommonGroup> findAll() {
        return commonGroupRepository.findAll();
    }
}

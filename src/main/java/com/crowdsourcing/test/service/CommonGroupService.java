package com.crowdsourcing.test.service;

import com.crowdsourcing.test.dto.SearchDto;
import com.crowdsourcing.test.dto.commongroup.CommonGroupDto;
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
@RequiredArgsConstructor /** final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 추가 */
@Service
public class CommonGroupService {

    private final CommonGroupRepository commonGroupRepository;
    /**
     * 공통 그룹 저장
     */
    @Transactional
    public Boolean save(CommonGroupDto commonGroupDto) {
        if(commonGroupRepository.existsById(commonGroupDto.getGroupCode())) {
            return false;
        }
        CommonGroup commonGroup = CommonGroup.builder()
                .groupCode(commonGroupDto.getGroupCode())
                .groupName(commonGroupDto.getGroupName())
                .groupNameKor(commonGroupDto.getGroupNameKor())
                .description(commonGroupDto.getDescription())
                .isUse(commonGroupDto.getIsUse())
                .build();
        commonGroupRepository.save(commonGroup);
        return true;
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
    public Page<CommonGroup> findCommonGroup(SearchDto commonGroupSearch, Pageable pageable) {
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
    public CommonGroupDto updateCommonGroupDto(String code) {
        CommonGroup commonGroup = (CommonGroup) findById(code);
        CommonGroupDto form = CommonGroupDto.builder()
                .groupCode(commonGroup.getGroupCode())
                .groupName(commonGroup.getGroupName())
                .groupNameKor(commonGroup.getGroupNameKor())
                .description(commonGroup.getDescription())
                .isUse(commonGroup.isUse())
                .build();

        return form;
    }
}

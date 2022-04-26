package com.crowdsourcing.test.service;

import com.crowdsourcing.test.domain.CommonGroup;
import com.crowdsourcing.test.repository.CommonGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void save(CommonGroup commonGroup) {
        commonGroupRepository.save(commonGroup);
    }
    @Transactional
    public void remove(CommonGroup commonGroup) {
        commonGroupRepository.delete(commonGroup);
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

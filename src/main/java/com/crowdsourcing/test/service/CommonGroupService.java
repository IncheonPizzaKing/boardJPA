package com.crowdsourcing.test.service;

import com.crowdsourcing.test.domain.CommonCode;
import com.crowdsourcing.test.domain.CommonCodeId;
import com.crowdsourcing.test.domain.CommonGroup;
import com.crowdsourcing.test.repository.CommonCodeRepository;
import com.crowdsourcing.test.repository.CommonGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommonGroupService {

    private final CommonGroupRepository commonGroupRepository;

    /**
     * 사용자 저장, 삭제
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
     * 사용자 한 명 검색(id)
     * @return
     */
    public CommonGroup findById(String id) {
        return commonGroupRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }


}

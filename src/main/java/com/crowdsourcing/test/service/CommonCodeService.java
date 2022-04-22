package com.crowdsourcing.test.service;

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

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;

    /**
     * 사용자 저장, 삭제
     */
    @Transactional
    public void save(CommonCode commonCode) {
        commonCodeRepository.save(commonCode);
    }
    @Transactional
    public void remove(CommonCode commonCode) {
        commonCodeRepository.delete(commonCode);
    }

    /**
     * 사용자 한 명 검색(id)
     * @return
     */
    public CommonCode findById(CommonCodeId id) {
        return commonCodeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }


}

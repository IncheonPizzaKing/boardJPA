package com.crowdsourcing.test.service;

import com.crowdsourcing.test.domain.CommonCode;
import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.domain.UserId;
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
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 사용자 저장, 삭제
     */
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
    @Transactional
    public void remove(User user) {
        userRepository.delete(user);
    }

    /**
     * 사용자 한 명 검색(username)
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameEquals(username);
    }

    /**
     * 사용자 한 명 검색(id)
     * @return
     */
    public User findOne(UserId id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * 조건에 맞는 사용자 전체 검색(role, search 값 전달 받았을때)
     */
    @Transactional(readOnly = true)
    public Page<User> findByUsernameContainingAndRoleEquals(String search, CommonCode commonCode, Pageable pageable) {
        return userRepository.findByUsernameContainingAndCommonCodeEquals(search, commonCode, pageable);
    }

    /**
     * 조건에 맞는 사용자 전체 검색(search 값만 전달 받았을때)
     */
    @Transactional(readOnly = true)
    public Page<User> findByUsernameContaining(String search, Pageable pageable) {
        return userRepository.findByUsernameContaining(search, pageable);
    }
}

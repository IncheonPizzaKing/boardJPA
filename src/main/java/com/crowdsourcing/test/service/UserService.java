package com.crowdsourcing.test.service;

import com.crowdsourcing.test.controller.BoardSearch;
import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void remove(User user) {
        userRepository.remove(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User findOne(Long id) {
       return userRepository.findOne(id);
    }

    public List<User> findUser(BoardSearch userSearch) {
        return userRepository.findUser(userSearch);
    }
}

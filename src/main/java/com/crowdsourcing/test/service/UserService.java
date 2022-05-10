package com.crowdsourcing.test.service;

import com.crowdsourcing.test.dto.user.UserDto;
import com.crowdsourcing.test.domain.CommonCode;
import com.crowdsourcing.test.domain.CommonCodeId;
import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.domain.UserId;
import com.crowdsourcing.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor /** final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 추가 */
@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CommonCodeService commonCodeService;

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
    public Page<User> findByUsernameContainingAndRoleEquals(String search, CommonCode commonCode, Pageable pageable) {
        return userRepository.findByUsernameContainingAndCommonCodeEquals(search, commonCode, pageable);
    }

    /**
     * 조건에 맞는 사용자 전체 검색(search 값만 전달 받았을때)
     */
    public Page<User> findByUsernameContaining(String search, Pageable pageable) {
        return userRepository.findByUsernameContaining(search, pageable);
    }

    /**
     * 회원가입
     * @param userDto
     */
    @Transactional
    public void signup(UserDto userDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String[] commonCodeOne = userDto.getCommonCodeId().split("_");
        CommonCode one = commonCodeService.findById(new CommonCodeId(commonCodeOne[0], commonCodeOne[1]));
        User user = User.builder()
                .username(userDto.getUsername())
                .password(encoder.encode(userDto.getPassword()))
                .commonCode(one)
                .build();
        userRepository.save(user);
    }

    /**
     * 사용자 삭제
     * @param selectedValues
     */
    @Transactional
    public void deleteUsers(List<String> selectedValues) {
        for (String user : selectedValues) {
            String[] userOne = user.split("_");
            User one = findOne(new UserId(Long.parseLong(userOne[0]), userOne[1]));
            userRepository.delete(one);
        }
    }
}

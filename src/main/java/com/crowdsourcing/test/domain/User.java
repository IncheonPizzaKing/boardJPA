package com.crowdsourcing.test.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter @Setter /** 클래스 내 모든 필드의 Getter/Setter 메소드 생성 */
@NoArgsConstructor /** 기본 생성자를 자동으로 추가(접근권한은 protected로 제한) */
@AllArgsConstructor /** 모든 필드 값을 파라미터로 받는 생성자를 추가 */
@Builder
@IdClass(UserId.class)
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "groupCode"),
            @JoinColumn(name = "code")
    })
    private CommonCode commonCode;

    //Board 엔티티와 양방향 매핑
    //저자가 계정을 수정, 삭제하면 게시글도 같이 수정,삭제된다(영속성 전이)
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : commonCode.getCodeName().split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true;
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true;
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true;
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true;
    }
}

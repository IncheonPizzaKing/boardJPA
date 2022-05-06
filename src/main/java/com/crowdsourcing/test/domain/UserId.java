package com.crowdsourcing.test.domain;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User 엔티티 IdClass
 */
@NoArgsConstructor /** 기본 생성자를 자동으로 추가(접근권한은 protected로 제한) */
@AllArgsConstructor /** 모든 필드 값을 파라미터로 받는 생성자를 추가 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserId implements Serializable {

    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @EqualsAndHashCode.Include
    @Id
    private String username;
}

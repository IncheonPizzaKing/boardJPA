package com.crowdsourcing.test.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User 엔티티 IdClass
 */
@AllArgsConstructor /** 모든 필드 값을 파라미터로 받는 생성자를 추가 */
@NoArgsConstructor /** 기본 생성자를 자동으로 추가(접근권한은 protected로 제한) */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CommonCodeId implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    private String groupCode;

    @EqualsAndHashCode.Include
    @Id @Column(columnDefinition = "char(4)", unique = true)
    private String code;
}

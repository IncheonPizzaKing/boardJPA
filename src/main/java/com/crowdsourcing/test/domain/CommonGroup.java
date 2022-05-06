package com.crowdsourcing.test.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter /** 클래스 내 모든 필드의 Getter/Setter 메소드 생성 */
@NoArgsConstructor /** 기본 생성자를 자동으로 추가(접근권한은 protected로 제한) */
@AllArgsConstructor /** 모든 필드 값을 파라미터로 받는 생성자를 추가 */
@Builder
public class CommonGroup {

    @Id @Column(columnDefinition = "char(4)")
    private String groupCode;

    @Column(nullable = false)
    private String groupName;

    private String groupNameKor;
    private String description;
    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean isUse;
}
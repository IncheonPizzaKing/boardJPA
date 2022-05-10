package com.crowdsourcing.test.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor /** 기본 생성자를 자동으로 추가(접근권한은 protected로 제한) */
@AllArgsConstructor /** 모든 필드 값을 파라미터로 받는 생성자를 추가 */
@Getter @Setter /** 클래스 내 모든 필드의 Getter/Setter 메소드 생성 */
@IdClass(CommonCodeId.class)
public class CommonCode {

    @Id
    private String groupCode;

    @Id @Column(columnDefinition = "char(4)", unique = true)
    private String code;

    @Column(nullable = false)
    private String codeName;

    @Column(nullable = false)
    private String codeNameKor;
    private String description;

    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean isUse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupCode", insertable = false, updatable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private CommonGroup commonGroup;
}

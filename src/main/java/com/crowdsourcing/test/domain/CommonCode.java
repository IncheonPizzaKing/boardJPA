package com.crowdsourcing.test.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@IdClass(CommonCodeId.class)
public class CommonCode {

    @Id
    private String groupCode;

    @Id @Column(columnDefinition = "char(4)", unique = true)
    private String code;

    @Column(nullable = false)
    private String codeName;

    private String codeNameKor;
    private String description;

    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean isUse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupCode", insertable = false, updatable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private CommonGroup commonGroup;
}

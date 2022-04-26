package com.crowdsourcing.test.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
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

    @Builder
    public CommonCode(String groupCode, String code, String codeName, String codeNameKor, String description, boolean isUse) {
        this.groupCode = groupCode;
        this.code = code;
        this.codeName = codeName;
        this.codeNameKor = codeNameKor;
        this.description = description;
        this.isUse = isUse;
    }

    public void addCommonGroup(CommonGroup commonGroup) {
        this.commonGroup = commonGroup;
        commonGroup.getCommonCodeList().add(this);
    }
}

package com.crowdsourcing.test.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter @Setter
@IdClass(CommonCodeId.class)
public class CommonCode {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupCode")
    private CommonGroup commonGroup;

    @Id @Column(columnDefinition = "char(4)")
    private String code;

    @Column(nullable = false)
    private String codeName;

    private String codeNameKor;
    private String description;

    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean isUse;

    public void addCommonGroup(CommonGroup commonGroup) {
        this.commonGroup = commonGroup;
        commonGroup.getCommonCodeList().add(this);
    }
}

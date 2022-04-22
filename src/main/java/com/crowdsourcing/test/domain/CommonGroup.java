package com.crowdsourcing.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class CommonGroup {

    @Id @Column(columnDefinition = "char(4)")
    private String groupCode;

    @Column(nullable = false)
    private String groupName;

    private String groupNameKor;
    private String description;
    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean isUse;

    @OneToMany(mappedBy = "commonGroup", cascade = {CascadeType.ALL})
    private List<CommonCode> commonCodeList = new ArrayList<>();
}
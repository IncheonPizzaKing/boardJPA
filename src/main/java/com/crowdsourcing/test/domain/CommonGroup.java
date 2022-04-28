package com.crowdsourcing.test.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
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
package com.crowdsourcing.test.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User 엔티티 IdClass
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CommonCodeId implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupCode")
    private CommonGroup commonGroup;

    @EqualsAndHashCode.Include
    @Id @Column(columnDefinition = "char(4)")
    private String code;
}

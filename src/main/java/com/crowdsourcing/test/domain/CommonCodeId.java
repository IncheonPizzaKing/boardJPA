package com.crowdsourcing.test.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User 엔티티 IdClass
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CommonCodeId implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    private String groupCode;

    @EqualsAndHashCode.Include
    @Id
    private String code;
}

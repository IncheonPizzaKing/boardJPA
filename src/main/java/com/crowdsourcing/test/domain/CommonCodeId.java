package com.crowdsourcing.test.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private String groupCode;

    @EqualsAndHashCode.Include
    @Id
    private String code;
}

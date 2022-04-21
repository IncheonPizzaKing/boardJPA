package com.crowdsourcing.test.domain;

import lombok.*;
import org.apache.tomcat.jni.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter @Setter
@IdClass(CommonCodeId.class)
public class CommonCode {

    @Id
    private String groupCode;

    @Id @Size(max = 4, min = 4)
    private String code;

    @Column(nullable = false)
    private String codeName;

    private String codeNameKor;
    private String description;

    @Column(nullable = false)
    private String issuerId;
    @Column(nullable = false)
    private LocalDateTime issued;
    @Column(nullable = false)
    private String modifierId;
    @Column(nullable = false)
    private LocalDateTime modified;
    @Column(nullable = false)
    private boolean isUse;

    private String etc;
    private int sortOrder;

    //Board 엔티티와 양방향 매핑
    //저자가 계정을 수정, 삭제하면 게시글도 같이 수정,삭제된다(영속성 전이)
//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
//    private List<Board> boardList = new ArrayList<>();


}

package com.crowdsourcing.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter /** 클래스 내 모든 필드의 Getter/Setter 메소드 생성 */
public class FileMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileMasterId")
    private Long id;

    //file 엔티티와 양방향 매핑, fileMaster 엔티티 저장 시 file 엔티티도 같이 저장
    @OneToMany(mappedBy = "fileMaster", cascade = {CascadeType.PERSIST})
    private List<File> fileList = new ArrayList<>();

    private String useFile;
}
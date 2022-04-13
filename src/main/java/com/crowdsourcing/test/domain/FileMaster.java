package com.crowdsourcing.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class FileMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileMasterId")
    private Long id;

    @OneToMany(mappedBy = "fileMaster", cascade = {CascadeType.PERSIST})
    private List<File> fileList = new ArrayList<>();

    private String useFile;
}
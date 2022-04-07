package com.crowdsourcing.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String originFileName;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}

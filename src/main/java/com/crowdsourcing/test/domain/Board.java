package com.crowdsourcing.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Board {

    @Id @GeneratedValue
    private Long id;

    private String contentType;
    private String title;
    private String author;
    private String content;
    private LocalDateTime time;
    @OneToMany(
            mappedBy = "board",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<BoardFile> boardFile = new ArrayList<>();

    // Board에서 파일 처리 위함
    public void addBoardFile(BoardFile boardFile) {
        this.boardFile.add(boardFile);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(boardFile.getBoard() != this)
            // 파일 저장
            boardFile.setBoard(this);
    }
}

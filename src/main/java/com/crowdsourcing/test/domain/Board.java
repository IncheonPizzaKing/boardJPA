package com.crowdsourcing.test.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long id;
    @Column(nullable = false)
    private String contentType;
    @Column(nullable = false)
    private String title;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "userId"),
            @JoinColumn(name = "username")
    })
    private User author;
    @Column(nullable = false)
    private String content;
    private LocalDateTime time;

    @OneToOne
    @JoinColumn(name = "fileMasterId")
    private FileMaster fileMaster;

    public void addUser(User author) {
        this.author = author;
        author.getBoardList().add(this);
    }
}

package com.crowdsourcing.test.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long id;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "codeGroup"),
            @JoinColumn(name = "code")
    })
    private CommonCode commonCode;
    @Column(nullable = false)
    private String title;

    //User 엔티티와 다대일 양방향 매핑
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "username"),
            @JoinColumn(name = "userId")
    })
    private User author;
    @Column(nullable = false)
    private String content;
    private LocalDateTime time;

    //FileMaster 엔티티와 1대1 단방향 매핑
    @OneToOne
    @JoinColumn(name = "fileMasterId")
    private FileMaster fileMaster;

    public void addUser(User author) {
        this.author = author;
        author.getBoardList().add(this);
    }
}

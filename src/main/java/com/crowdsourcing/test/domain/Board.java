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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
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

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<File> fileList = new ArrayList<>();

    public void addUser(User author) {
        this.author = author;
        author.getBoardList().add(this);
    }
}

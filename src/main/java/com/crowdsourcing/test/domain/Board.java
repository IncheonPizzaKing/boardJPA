package com.crowdsourcing.test.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter @Setter /** 클래스 내 모든 필드의 Getter/Setter 메소드 생성 */
@NoArgsConstructor /** 기본 생성자를 자동으로 추가(접근권한은 protected로 제한) */
@AllArgsConstructor /** 모든 필드 값을 파라미터로 받는 생성자를 추가 */
@Builder
@Table(name = "board")
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long id;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "commonGroup"),
            @JoinColumn(name = "code")
    })
    private CommonCode commonCode;
    @NotEmpty
    private String title;

    /** User 엔티티와 다대일 양방향 매핑 */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "username"),
            @JoinColumn(name = "userId")
    })
    private BoardUser author;
    @NotEmpty
    private String content;
    private LocalDateTime time;

    /** FileMaster 엔티티와 1대1 단방향 매핑 */
    @OneToOne
    @JoinColumn(name = "fileMasterId")
    private FileMaster fileMaster;

    public void addUser(BoardUser author) {
        this.author = author;
        author.getBoardList().add(this);
    }
}

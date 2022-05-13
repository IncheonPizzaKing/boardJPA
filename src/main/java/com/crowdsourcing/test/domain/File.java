package com.crowdsourcing.test.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter /** 클래스 내 모든 필드의 Getter/Setter 메소드 생성 */
@NoArgsConstructor /** 기본 생성자를 자동으로 추가(접근권한은 protected로 제한) */
@AllArgsConstructor /** 모든 필드 값을 파라미터로 받는 생성자를 추가 */
@Builder
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileId")
    private Long id;

    @Column(nullable = false)
    private String originFileName;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private String filePath;
    @Column(nullable = false)
    private String useFile;

    //fileMaster 엔티티와 양방향 매핑
    @ManyToOne
    @JoinColumn(name = "fileMasterId")
    private FileMaster fileMaster;

    public void addFileMaster(FileMaster fileMaster) {
        this.fileMaster = fileMaster;
        fileMaster.getFileList().add(this);
    }
}

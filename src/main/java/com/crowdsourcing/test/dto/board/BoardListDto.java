package com.crowdsourcing.test.dto.board;

import com.crowdsourcing.test.domain.File;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter /** 클래스 내 모든 필드의 Getter/Setter 메소드 생성 */
@NoArgsConstructor /** 기본 생성자를 자동으로 추가(접근권한은 protected로 제한) */
@AllArgsConstructor /** 모든 필드 값을 파라미터로 받는 생성자를 추가 */
@Builder
public class BoardListDto {

    private Long id;
    private String codeNameKor;
    private String title;
    private String author;
    private LocalDateTime time;
    private Long fileMasterId;
}

package com.crowdsourcing.test.controller.form;

import com.crowdsourcing.test.domain.CommonCode;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter @Setter /** 클래스 내 모든 필드의 Getter/Setter 메소드 생성 */
@NoArgsConstructor /** 기본 생성자를 자동으로 추가(접근권한은 protected로 제한) */
@AllArgsConstructor /** 모든 필드 값을 파라미터로 받는 생성자를 추가 */
@Builder
public class BoardForm {

    private String commonCodeId;
    @NotEmpty(message = "제목은 필수 입력 값입니다")
    private String title;
    private String author;
    private String content;
    private List<CommonCode> commonCodeList;
    private CommonCode commonCode;
}

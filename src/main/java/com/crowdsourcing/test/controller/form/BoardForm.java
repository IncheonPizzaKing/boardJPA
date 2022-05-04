package com.crowdsourcing.test.controller.form;

import com.crowdsourcing.test.domain.CommonCode;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

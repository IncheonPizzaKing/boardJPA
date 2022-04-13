package com.crowdsourcing.test.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BoardForm {

    @NotEmpty
    private String contentType;
    @NotEmpty(message = "제목은 필수 입력 값입니다")
    private String title;
    @NotEmpty
    private String author;
    private String content;
}

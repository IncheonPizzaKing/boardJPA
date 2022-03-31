package com.crowdsourcing.test.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BoardForm {

    private String contentType;

    @NotEmpty
    private String title;
    private String author;
    private String content;
}

package com.crowdsourcing.test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter /** 클래스 내 모든 필드의 Getter/Setter 메소드 생성 */
public class SearchDto {

    private String types;
    private String search;
}


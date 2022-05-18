package com.crowdsourcing.test.dto.user;

import com.crowdsourcing.test.domain.CommonCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter /** 클래스 내 모든 필드의 Getter/Setter 메소드 생성 */
public class UserListDto {

    private Long id;
    private String username;
    private CommonCode commonCode;
}

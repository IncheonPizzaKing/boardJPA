package com.crowdsourcing.test.dto.user;

import com.crowdsourcing.test.domain.CommonCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter @Setter /** 클래스 내 모든 필드의 Getter/Setter 메소드 생성 */
public class UserDto {

    @NotEmpty(message = "이메일은 필수 입력 값입니다")
    private String username;
    @NotEmpty(message = "비밀번호는 필수 입력 값입니다")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;
    @NotEmpty
    private String commonCodeId;
    private CommonCode commonCode;
}
package com.crowdsourcing.test.controller.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter @Setter /** 클래스 내 모든 필드의 Getter/Setter 메소드 생성 */
@Builder
@NoArgsConstructor /** 기본 생성자를 자동으로 추가(접근권한은 protected로 제한) */
@AllArgsConstructor /** 모든 필드 값을 파라미터로 받는 생성자를 추가 */
public class CommonGroupForm {

    @NotEmpty
    @Length(min=4, max=4, message = "코드는 4자리")
    private String groupCode;
    @NotEmpty
    private String groupName;
    @NotEmpty
    private String groupNameKor;
    private Boolean isUse;
    private String description;
}


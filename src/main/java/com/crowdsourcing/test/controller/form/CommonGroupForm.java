package com.crowdsourcing.test.controller.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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


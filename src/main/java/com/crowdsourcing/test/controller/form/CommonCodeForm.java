package com.crowdsourcing.test.controller.form;

import com.crowdsourcing.test.domain.CommonGroup;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonCodeForm {

    private String commonGroupCode;
    @NotEmpty
    @Length(min=4, max=4, message = "코드는 4자리")
    private String code;
    @NotEmpty
    private String codeName;
    @NotEmpty
    private String codeNameKor;
    private Boolean use;
    private String description;
    private CommonGroup commonGroup;
}


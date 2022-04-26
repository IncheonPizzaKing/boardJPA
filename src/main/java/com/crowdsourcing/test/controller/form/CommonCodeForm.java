package com.crowdsourcing.test.controller.form;

import com.crowdsourcing.test.domain.CommonGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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
    private List<CommonGroup> commonGroupList;
    private CommonGroup commonGroup;

    @Builder
    public CommonCodeForm(String commonGroupCode, String code, String codeName, String codeNameKor, Boolean use, String description, List<CommonGroup> commonGroupList, CommonGroup commonGroup) {
        this.commonGroupCode = commonGroupCode;
        this.code = code;
        this.codeName = codeName;
        this.codeNameKor = codeNameKor;
        this.use = use;
        this.description = description;
        this.commonGroupList = commonGroupList;
        this.commonGroup = commonGroup;
    }
}


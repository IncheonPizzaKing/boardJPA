package com.crowdsourcing.test.controller.form;

import com.crowdsourcing.test.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SelectedForm {

    private List<String> selectedUser;
}

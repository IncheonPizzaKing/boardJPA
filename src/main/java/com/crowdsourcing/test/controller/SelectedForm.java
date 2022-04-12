package com.crowdsourcing.test.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SelectedForm {

    private List<String> open;
    private List<Long> id;
    private List<String> username;
}

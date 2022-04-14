package com.crowdsourcing.test.repository;


import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.User;

import java.util.List;

public interface CustomUserRepository {

    List<User> findUser(BoardSearch userSearch);
}

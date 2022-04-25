package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.domain.*;
import com.crowdsourcing.test.service.BoardService;
import com.crowdsourcing.test.service.CommonCodeService;
import com.crowdsourcing.test.service.CommonGroupService;
import com.crowdsourcing.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class TestDatabaseController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommonCodeService commonCodeService;
    private final CommonGroupService commonGroupService;

    /**
     * 테스트 데이터 주입
     */
    public void create() {
        CommonGroup commonGroup = createCommonGroup("001", "board_select", "게시판_선택");
        createCommonCode("001", "free", "자유", true, commonGroup);
        createCommonCode("002", "music", "음악", true, commonGroup);
        createCommonCode("003", "movie", "영화", true, commonGroup);
        createCommonCode("004", "sports", "스포츠", true, commonGroup);
        commonGroupService.save(commonGroup);

        CommonGroup commonGroup2 = createCommonGroup("002", "user_role", "사용자_권한");
        createCommonCode2("001", "USER", "사용자", true, commonGroup2);
        createCommonCode2("002", "ADMIN,USER", "관리자", true, commonGroup2);
        commonGroupService.save(commonGroup2);
        for(int i = 0; i < 100; i++) {
            createTestDB("user"+i, "user", commonCodeService.findById(new CommonCodeId(commonGroup2, "U001")));
        }
        createTestDB("admin", "admin", commonCodeService.findById(new CommonCodeId(commonGroup2, "U002")));


        for(int i = 0; i < 100; i++) {
            createTestDB2("free", "test"+i, "user"+i, "test"+i, commonCodeService.findById(new CommonCodeId(commonGroup, "B001")));
        }
        createTestDB2("sports", "test2", "user0", "test2" ,commonCodeService.findById(new CommonCodeId(commonGroup, "B002")));
        createTestDB2("movie", "test3", "admin", "test3" ,commonCodeService.findById(new CommonCodeId(commonGroup, "B003")));
        createTestDB2("music", "test4", "admin", "test4" ,commonCodeService.findById(new CommonCodeId(commonGroup, "B004")));
    }

    /**
     * 테스트 사용자 데이터 메소드
     */
    public void createTestDB(String name, String password, CommonCode commonCode) {
        User user = new User();
        user.setUsername(name);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));
        user.setCommonCode(commonCode);

        userService.save(user);
    }

    /**
     * 테스트 게시글 데이터 메소드
     */
    public void createTestDB2(String type, String title, String author, String content, CommonCode commonCode) {
        Board board = new Board();
        board.setCommonCode(commonCode);
        board.setTitle(title);
        board.setContent(content);
        board.setTime(LocalDateTime.now());

        boardService.write(board, author);
    }

    public CommonGroup createCommonGroup(String groupCode, String groupName, String groupNameKor) {
        CommonGroup commonGroup = new CommonGroup();
        commonGroup.setGroupCode("G" + groupCode);
        commonGroup.setGroupName(groupName);
        commonGroup.setGroupNameKor(groupNameKor);
        return commonGroup;
    }
    public void createCommonCode(String code, String codeName, String codeNameKor, Boolean isUse, CommonGroup commonGroup) {
        CommonCode commonCode = new CommonCode();
        commonCode.setCode("B" + code);
        commonCode.setCodeName(codeName);
        commonCode.setCodeNameKor(codeNameKor);
        commonCode.setUse(isUse);
        commonCode.addCommonGroup(commonGroup);
    }
    public void createCommonCode2(String code, String codeName, String codeNameKor, Boolean isUse, CommonGroup commonGroup) {
        CommonCode commonCode = new CommonCode();
        commonCode.setCode("U" + code);
        commonCode.setCodeName(codeName);
        commonCode.setCodeNameKor(codeNameKor);
        commonCode.setUse(isUse);
        commonCode.addCommonGroup(commonGroup);
    }
}

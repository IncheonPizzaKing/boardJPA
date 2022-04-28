package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.domain.*;
import com.crowdsourcing.test.repository.CommonCodeRepository;
import com.crowdsourcing.test.repository.CommonGroupRepository;
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
    private final CommonCodeRepository commonCodeRepository;
    private final CommonGroupRepository commonGroupRepository;

    /**
     * 테스트 데이터 주입
     */
    public void create() {
        CommonGroup commonGroup = createCommonGroup("001", "board_select", "게시판_선택", true);
        createCommonCode("001", "free", "자유", true, commonGroup.getGroupCode());
        createCommonCode("002", "music", "음악", true, commonGroup.getGroupCode());
        createCommonCode("003", "movie", "영화", true, commonGroup.getGroupCode());
        createCommonCode("004", "sports", "스포츠", true, commonGroup.getGroupCode());

        CommonGroup commonGroup2 = createCommonGroup("002", "user_role", "사용자_권한", true);
        createCommonCode2("001", "USER", "사용자", true, commonGroup2.getGroupCode());
        createCommonCode2("002", "ADMIN,USER", "관리자", true, commonGroup2.getGroupCode());

        CommonGroup commonGroup3 = createCommonGroup("003", "file_is_use", "파일사용여부", true);
        createCommonCode3("001", "true", "사용중", true, commonGroup3.getGroupCode());
        createCommonCode3("002", "false", "사용x", true, commonGroup3.getGroupCode());

        CommonGroup commonGroup4 = createCommonGroup("004", "list_size", "리스트_출력_개수", true);
        createCommonCode4("001", "10", "10", true, commonGroup4.getGroupCode());
        createCommonCode4("002", "20", "20", true, commonGroup4.getGroupCode());
        createCommonCode4("003", "30", "30", true, commonGroup4.getGroupCode());
        createCommonCode4("004", "50", "50", true, commonGroup4.getGroupCode());

        for(int i = 0; i < 105; i++) {
            createTestDB("user"+i, "user", commonCodeService.findById(new CommonCodeId(commonGroup2.getGroupCode(), "U001")));
        }
        createTestDB("admin", "admin", commonCodeService.findById(new CommonCodeId(commonGroup2.getGroupCode(), "U002")));


        for(int i = 0; i < 100; i++) {
            createTestDB2("free", "test"+i, "user"+i, "test"+i, commonCodeService.findById(new CommonCodeId(commonGroup.getGroupCode(), "B001")));
        }
        createTestDB2("sports", "test2", "user0", "test2" ,commonCodeService.findById(new CommonCodeId(commonGroup.getGroupCode(), "B002")));
        createTestDB2("movie", "test3", "admin", "test3" ,commonCodeService.findById(new CommonCodeId(commonGroup.getGroupCode(), "B003")));
        createTestDB2("music", "test4", "admin", "test4" ,commonCodeService.findById(new CommonCodeId(commonGroup.getGroupCode(), "B004")));
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

    public CommonGroup createCommonGroup(String groupCode, String groupName, String groupNameKor, Boolean isUse) {
        CommonGroup commonGroup = CommonGroup.builder()
                .groupCode("G" + groupCode)
                .groupName(groupName)
                .groupNameKor(groupNameKor)
                .isUse(isUse)
                .build();
        commonGroupRepository.save(commonGroup);
        return commonGroup;
    }
    public void createCommonCode(String code, String codeName, String codeNameKor, Boolean isUse, String commonGroup) {
        CommonCode commonCode = CommonCode.builder()
                .code("B" + code)
                .codeName(codeName)
                .codeNameKor(codeNameKor)
                .isUse(isUse)
                .groupCode(commonGroup)
                .commonGroup(commonGroupService.findById(commonGroup))
                .build();
        commonCodeRepository.save(commonCode);
    }
    public void createCommonCode2(String code, String codeName, String codeNameKor, Boolean isUse, String commonGroup) {
        CommonCode commonCode = CommonCode.builder()
                .code("U" + code)
                .codeName(codeName)
                .codeNameKor(codeNameKor)
                .isUse(isUse)
                .groupCode(commonGroup)
                .commonGroup(commonGroupService.findById(commonGroup))
                .build();
        commonCodeRepository.save(commonCode);

    }
    public void createCommonCode3(String code, String codeName, String codeNameKor, Boolean isUse, String commonGroup) {
        CommonCode commonCode = CommonCode.builder()
                .code("I" + code)
                .codeName(codeName)
                .codeNameKor(codeNameKor)
                .isUse(isUse)
                .groupCode(commonGroup)
                .commonGroup(commonGroupService.findById(commonGroup))
                .build();
        commonCodeRepository.save(commonCode);

    }
    public void createCommonCode4(String code, String codeName, String codeNameKor, Boolean isUse, String commonGroup) {
        CommonCode commonCode = CommonCode.builder()
                .code("S" + code)
                .codeName(codeName)
                .codeNameKor(codeNameKor)
                .isUse(isUse)
                .groupCode(commonGroup)
                .commonGroup(commonGroupService.findById(commonGroup))
                .build();
        commonCodeRepository.save(commonCode);

    }
}

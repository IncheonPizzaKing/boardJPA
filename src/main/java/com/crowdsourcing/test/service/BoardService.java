package com.crowdsourcing.test.service;

import com.crowdsourcing.test.controller.form.BoardForm;
import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.*;
import com.crowdsourcing.test.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor /** final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 추가 */
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;
    private final FileMasterService fileMasterService;
    private final CommonCodeService commonCodeService;

    /**
     * 게시글 작성
     * @param boardForm
     * @param multipartFile
     * @throws Exception
     */
    @Transactional
    public void write(BoardForm boardForm, List<MultipartFile> multipartFile) throws Exception {
        String[] commonCodeOne = boardForm.getCommonCodeId().split("_");
        CommonCode one = commonCodeService.findById(new CommonCodeId(commonCodeOne[0], commonCodeOne[1]));
        Board board;
        if (!multipartFile.get(0).isEmpty()) {
            FileMaster fileMasterIn = fileMasterService.upload(multipartFile);
            board = Board.builder()
                    .commonCode(one)
                    .title(boardForm.getTitle())
                    .content(boardForm.getContent())
                    .time(LocalDateTime.now())
                    .fileMaster(fileMasterIn)
                    .build();
        } else {
            board = Board.builder()
                    .commonCode(one)
                    .title(boardForm.getTitle())
                    .content(boardForm.getContent())
                    .time(LocalDateTime.now())
                    .build();
        }
        // 세션에서 username get
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = ((UserDetails) principal).getUsername();
        User user = userService.loadUserByUsername(username);
        board.addUser(user);
        boardRepository.save(board);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        if(board.getFileMaster() != null) {
            fileMasterService.deleteBoard(board.getFileMaster());
        }
        boardRepository.delete(board);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void update(Long boardId, String title, String content) {
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        board.setTitle(title);
        board.setContent(content);
    }

    /**
     * 조건에 맞는 게시글 전체 조회
     */
    public Page<Board> findBoard(BoardSearch boardSearch, Pageable pageable) {
        return boardRepository.findAll(boardSearch, pageable);
    }

    /**
     * 게시글 한 개 조회(id)
     */
    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
    }

    public BoardForm updateBoardForm(Long boardId) {
        Board board = (Board) findOne(boardId);
        BoardForm form = BoardForm.builder()
                .commonCode(board.getCommonCode())
                .title(board.getTitle())
                .author(board.getAuthor().getUsername())
                .content(board.getContent())
                .build();
        return form;
    }
}
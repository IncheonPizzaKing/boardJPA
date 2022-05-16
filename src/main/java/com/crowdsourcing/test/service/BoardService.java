package com.crowdsourcing.test.service;

import com.crowdsourcing.test.dto.board.BoardDto;
import com.crowdsourcing.test.dto.SearchDto;
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
     * @param boardDto
     * @param multipartFile
     * @throws Exception
     */
    @Transactional
    public void write(BoardDto boardDto, List<MultipartFile> multipartFile) throws Exception {
        String[] commonCodeOne = boardDto.getCommonCodeId().split("_");
        CommonCode one = commonCodeService.findById(new CommonCodeId(commonCodeOne[0], commonCodeOne[1]));
        Board board;
        /** 첨부파일이 있으면 첨부파일 저장 */
        if (!multipartFile.get(0).isEmpty()) {
            FileMaster fileMasterIn = fileMasterService.upload(multipartFile);
            board = Board.builder()
                    .commonCode(one)
                    .title(boardDto.getTitle())
                    .content(boardDto.getContent())
                    .time(LocalDateTime.now())
                    .fileMaster(fileMasterIn)
                    .build();
        } else {
            board = Board.builder()
                    .commonCode(one)
                    .title(boardDto.getTitle())
                    .content(boardDto.getContent())
                    .time(LocalDateTime.now())
                    .build();
        }
        /** 세션에서 username get */
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = ((UserDetails) principal).getUsername();
        BoardUser boardUser = userService.loadUserByUsername(username);
        board.addUser(boardUser);
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
    public Page<Board> findBoard(SearchDto searchDto, Pageable pageable) {
        return boardRepository.findAll(searchDto, pageable);
    }

    /**
     * 게시글 한 개 조회(id)
     */
    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
    }

    public BoardDto updateBoardDto(Long boardId) {
        Board board = (Board) findOne(boardId);
        BoardDto form = BoardDto.builder()
                .id(board.getId())
                .commonCode(board.getCommonCode())
                .title(board.getTitle())
                .author(board.getAuthor().getUsername())
                .content(board.getContent())
                .fileMaster(board.getFileMaster())
                .build();
        return form;
    }
}
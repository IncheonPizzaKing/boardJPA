package com.crowdsourcing.test.service;

import com.crowdsourcing.test.dto.board.BoardDto;
import com.crowdsourcing.test.dto.SearchDto;
import com.crowdsourcing.test.domain.*;
import com.crowdsourcing.test.dto.board.BoardListDto;
import com.crowdsourcing.test.dto.board.UpdateBoardDto;
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
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor /** final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 추가 */
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

    private final FileService fileService;
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = ((UserDetails) principal).getUsername();
        BoardUser boardUser = userService.loadUserByUsername(username);
        Board board;
        /** 첨부파일이 있으면 첨부파일 저장 */
        if (!multipartFile.get(0).isEmpty()) {
            FileMaster fileMasterIn = fileMasterService.upload(multipartFile);
            board = Board.builder()
                    .userId(boardUser.getId())
                    .username(boardUser.getUsername())
                    .commonCode(boardDto.getCommonCode())
                    .title(boardDto.getTitle())
                    .content(boardDto.getContent())
                    .time(LocalDateTime.now())
                    .fileMasterId(fileMasterIn.getId())
                    .build();
        } else {
            board = Board.builder()
                    .userId(boardUser.getId())
                    .username(boardUser.getUsername())
                    .commonCode(boardDto.getCommonCode())
                    .title(boardDto.getTitle())
                    .content(boardDto.getContent())
                    .time(LocalDateTime.now())
                    .build();
        }
        /** 세션에서 username get */
        boardRepository.save(board);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        if(board.getFileMasterId() != null) {
            fileMasterService.deleteBoard(board.getFileMasterId());
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
    public Page<BoardListDto> findBoard(Map<String, Object> param, Pageable pageable) {
        Page<BoardListDto> list;
        SearchDto searchDto = new SearchDto();
        if (param.get("types") != null) {
            searchDto.setTypes(param.get("types").toString());
        }
        if (param.get("search") != null) {
            searchDto.setSearch(param.get("search").toString());
        }
        String search = searchDto.getSearch();
        String type = searchDto.getTypes();
        list = boardRepository.findDslAll(searchDto, pageable);
        return list;
//        if(!StringUtils.hasText(search)) {
//            if(!StringUtils.hasText(type)) {
//                list = boardRepository.findListByAll(search, type);
//            } else {
//                list = boardRepository.findListBySearch(search);
//            }
//        } else {
//            if(!StringUtils.hasText(type)) {
//                list = boardRepository.findListByType(type);
//            } else {
//                list = boardRepository.findListByNull();
//            }
//        }
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), list.size());
//        Page<BoardListDto> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
//        return page;
    }

    /**
     * 게시글 한 개 조회(id)
     */
    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
    }

    public UpdateBoardDto updateBoardDto(Long boardId) {
        Board board = (Board) findOne(boardId);
        if(board.getFileMasterId() != null) {
            UpdateBoardDto form = UpdateBoardDto.builder()
                    .id(board.getId())
                    .commonCodeNameKor(commonCodeService.findById(new CommonCodeId("G001", board.getCommonCode())).getCodeNameKor())
                    .title(board.getTitle())
                    .author(board.getUsername())
                    .content(board.getContent())
                    .fileList(fileMasterService.findByFileMasterEquals(board.getFileMasterId()))
                    .build();
            return form;
        } else {
            UpdateBoardDto form = UpdateBoardDto.builder()
                    .id(board.getId())
                    .commonCodeNameKor(commonCodeService.findById(new CommonCodeId("G001", board.getCommonCode())).getCodeNameKor())
                    .title(board.getTitle())
                    .author(board.getUsername())
                    .content(board.getContent())
                    .build();
            return form;
        }
    }
}
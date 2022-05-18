package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.Board;
import com.crowdsourcing.test.dto.board.BoardListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {

//    @Query(value = "select b.board_id as id, cc.code_name_kor as common_code_name_kor, b.title as title, b.username as author, b.time as time, b.file_master_id" +
//            " from board b" +
//            " inner join common_code cc" +
//            " on b.common_code=cc.code" +
//            " where b.title like %:search%", nativeQuery = true)
//    List<BoardListDto> findListBySearch(String search);
//
//    @Query(value = "select b.board_id as id, cc.code_name_kor as common_code_name_kor, b.title as title, b.username as author, b.time as time, b.file_master_id" +
//            " from board b" +
//            " inner join common_code cc" +
//            " on b.common_code=cc.code" +
//            " where cc.code=:type", nativeQuery = true)
//    List<BoardListDto> findListByType(String type);
//
//    @Query(value = "select b.board_id as id, cc.code_name_kor as common_code_name_kor, b.title as title, b.username as author, b.time as time, b.file_master_id as file_master_id" +
//            " from board b" +
//            " inner join common_code cc" +
//            " on b.common_code=cc.code" +
//            " where cc.code=:type and b.title like %:search%", nativeQuery = true)
//    List<BoardListDto> findListByAll(String search, String type);
//
//    @Query(value = "select b.board_id as id, cc.code_name_kor as common_code_name_kor, b.title as title, b.username as author, b.time as time, b.file_master_id" +
//            " from board b" +
//            " inner join common_code cc" +
//            " on b.common_code=cc.code", nativeQuery = true)
//    List<BoardListDto> findListByNull();
}
package com.crowdsourcing.test.service;

import com.crowdsourcing.test.domain.Board;
import com.crowdsourcing.test.domain.File;
import com.crowdsourcing.test.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    /**
     * 파일 업로드
     */
    @Transactional
    public File upload(Board board, File file) {
        file.addBoard(board);
        fileRepository.save(file);
        return file;
    }
//
//    @Transactional
//    public void remove(File file) {
//        fileRepository.remove(file);
//    }
//
//    /**
//     * 조건에 맞는 파일 전체 검색
//     */
//    public List<File> findAll() {
//        return fileRepository.findAll();
//    }
//
//    public List<File> findFiles(Long boardId) {
//        return fileRepository.findFiles(boardId);
//    }
//
//    public File findOne(Long fileId) {
//        return fileRepository.findOne(fileId);
//    }
}

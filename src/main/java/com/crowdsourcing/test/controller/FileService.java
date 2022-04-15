package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.File;
import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public File findById(Long id) {
        return fileRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * 조건에 맞는 파일 전체 검색
     */
    public List<File> findFile(BoardSearch fileSearch) {
        return fileRepository.findFile(fileSearch);
    }

    @Transactional
    public void deleteFile(File file) {
        fileRepository.delete(file);
    }
}

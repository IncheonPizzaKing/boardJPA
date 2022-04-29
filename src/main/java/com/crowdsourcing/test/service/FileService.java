package com.crowdsourcing.test.service;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.File;
import com.crowdsourcing.test.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    /**
     * 파일 검색(id)
     * @param id
     * @return
     */
    public File findById(Long id) {
        return fileRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * 조건에 맞는 파일 전체 검색
     */
    @Transactional(readOnly = true)
    public Page<File> findFile(BoardSearch fileSearch, Pageable pageable) {
        return fileRepository.findFile(fileSearch, pageable);
    }

    /**
     * 파일 삭제
     * @param file
     */
    @Transactional
    public void deleteFile(File file) {
        fileRepository.delete(file);
    }
}

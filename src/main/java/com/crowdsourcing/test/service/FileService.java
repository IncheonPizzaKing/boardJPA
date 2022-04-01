package com.crowdsourcing.test.service;

import com.crowdsourcing.test.domain.File;
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

    //파일 업로드
    @Transactional
    public Long upload(File file) {
        fileRepository.save(file);
        return file.getId();
    }

    @Transactional
    public void delete(Long fileId) {
        File file = fileRepository.findOne(fileId);
        fileRepository.remove(file);
    }

    //파일 조회
    public List<File> findFile() {
        return fileRepository.findAll();
    }

    public File findOne(Long fileId) {
        return fileRepository.findOne(fileId);
    }
}

package com.crowdsourcing.test.service;

import com.crowdsourcing.test.dto.SearchDto;
import com.crowdsourcing.test.domain.File;
import com.crowdsourcing.test.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor /** final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 추가 */
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
    public Page<File> findFile(SearchDto fileSearch, Pageable pageable) {
        return fileRepository.findFile(fileSearch, pageable);
    }

    /**
     * 파일 삭제
     * @param selectedValues
     */
    @Transactional
    public void deleteFile(List<Integer> selectedValues) {
        for (int file : selectedValues) {
            Long fileId = Long.parseLong(String.valueOf(file));
            File findFile = findById(fileId);
            fileRepository.delete(findFile);
        }
    }

    /**
     * 파일 다운로드
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseEntity<Resource> downloadFile(Long file) throws Exception {
        com.crowdsourcing.test.domain.File fileDto = findById(file);
        Path path = Paths.get(fileDto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;fileName=\\" + new String(fileDto.getOriginFileName().getBytes("UTF-8"), "ISO-8859-1"))
                .body(resource);
    }
}

package com.crowdsourcing.test.service;

import com.crowdsourcing.test.domain.File;
import com.crowdsourcing.test.domain.FileMaster;
import com.crowdsourcing.test.repository.FileMasterRepository;
import com.crowdsourcing.test.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor /** final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 추가 */
public class FileMasterService {


    private final FileMasterRepository fileMasterRepository;
    private final FileService fileService;

    /**
     * 파일 업로드
     */
    @Transactional
    public FileMaster upload(List<MultipartFile> multipartFile) throws Exception {

        FileMaster fileMaster = new FileMaster();
        fileMaster.setUseFile("true");
        fileMasterRepository.save(fileMaster);
        fileService.uploadFile(multipartFile, fileMaster);

        return fileMaster;
    }

    /**
     * 게시판 삭제시 useFile=false
     */
    @Transactional
    public void deleteBoard(Long fileMasterId) {
        FileMaster fileMaster = fileMasterRepository.findById(fileMasterId).orElseThrow(IllegalArgumentException::new);
        List<com.crowdsourcing.test.domain.File> list = fileService.findByFileMasterEquals(fileMaster);
        if(list != null) {
            fileMaster.setUseFile("false");
            for(com.crowdsourcing.test.domain.File file : list) {
                file.setUseFile("false");
            }
        }
    }

    public List<File> findByFileMasterEquals(Long fileMasterId) {
        return fileService.findByFileMasterEquals(fileMasterRepository.findById(fileMasterId).orElseThrow(IllegalArgumentException::new));
    }
}

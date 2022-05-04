package com.crowdsourcing.test.service;

import com.crowdsourcing.test.domain.FileMaster;
import com.crowdsourcing.test.repository.FileMasterRepository;
import com.crowdsourcing.test.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileMasterService {


    private final FileMasterRepository fileMasterRepository;

    /**
     * 파일 업로드
     */
    @Transactional
    public FileMaster upload(List<MultipartFile> multipartFile) throws Exception {

        FileMaster fileMaster = new FileMaster();
        fileMaster.setUseFile("true");
        for (MultipartFile multipartFileIn : multipartFile) {
            if (!multipartFileIn.isEmpty()) {
                String originFilename = multipartFileIn.getOriginalFilename();
                String filename = new MD5Generator(originFilename).toString();
                String savePath = System.getProperty("user.dir") + "\\files";
                if (!new File(savePath).exists()) {

                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "\\" + filename;
                multipartFileIn.transferTo(new File(filePath));

                com.crowdsourcing.test.domain.File fileDto = com.crowdsourcing.test.domain.File.builder()
                        .fileName(filename)
                        .filePath(filePath)
                        .originFileName(originFilename)
                        .useFile("true")
                        .build();
                fileDto.addFileMaster(fileMaster);
            }
        }
        fileMasterRepository.save(fileMaster);
        return fileMaster;
    }

    /**
     * 게시판 삭제시 useFile=false
     */
    @Transactional
    public void deleteBoard(FileMaster fileMaster) {
        List<com.crowdsourcing.test.domain.File> list = fileMaster.getFileList();
        if(list != null) {
            fileMaster.setUseFile("false");
            for(com.crowdsourcing.test.domain.File file : list) {
                file.setUseFile("false");
            }
        }
    }
}

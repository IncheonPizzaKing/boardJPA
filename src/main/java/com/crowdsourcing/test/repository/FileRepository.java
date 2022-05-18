package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.File;
import com.crowdsourcing.test.domain.FileMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long>, CustomFileRepository {

    List<File> findByFileMasterEquals(FileMaster fileMaster);
}

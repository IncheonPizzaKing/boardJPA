package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileRepository {
    private final EntityManager em;

    public void save(File file) {
        em.persist(file);
    }

    public void remove(File file) {
        em.remove(file);
    }

    public File findOne(Long id) {
        return em.find(File.class, id);
    }

    public List<File> findAll() {
        return em.createQuery("select f from File f", File.class)
                .getResultList();
    }
}

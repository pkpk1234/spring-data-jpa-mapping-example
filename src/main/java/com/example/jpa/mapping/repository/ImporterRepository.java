package com.example.jpa.mapping.repository;

import com.example.jpa.mapping.store.Importer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pkpk1234 on 2017/6/3.
 */
@Repository
public interface ImporterRepository extends JpaRepository<Importer, Long> {
}

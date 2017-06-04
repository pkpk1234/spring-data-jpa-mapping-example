package com.example.jpa.mapping.repository;

import com.example.jpa.mapping.store.SubVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pkpk1234 on 2017/6/3.
 */
@Repository
public interface SubVersionRepository extends JpaRepository<SubVersion,Long> {
}

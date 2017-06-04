package com.example.jpa.mapping.repository;

import com.example.jpa.mapping.store.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pkpk1234 on 2017/6/3.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

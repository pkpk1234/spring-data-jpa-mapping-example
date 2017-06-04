package com.example.jpa.mapping;

import com.example.jpa.mapping.repository.ProductRepository;
import com.example.jpa.mapping.store.Company;
import com.example.jpa.mapping.store.Image;
import com.example.jpa.mapping.store.Product;
import com.example.jpa.mapping.store.WarehouseProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
//re-create-database-before-each-test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void getProduct() {
        log.info("start test method getProduct");
        List<Product> list = this.productRepository.findAll();
        assertEquals(1, list.size());
    }

    @Test
    @Transactional
    public void getWarehouse() {
        log.info("start test method getWarehouse");
        List<Product> list = this.productRepository.findAll();
        Product product = list.get(0);
        log.info("start get warehouseProductInfo from product");
        //OneToOne ,LAZY
        WarehouseProductInfo warehouseProductInfo = product.getWarehouseProductInfo();
        Product product1 = warehouseProductInfo.getProduct();
        assertEquals(product, product1);
    }

    @Test
    @Transactional
    public void getCompany() {
        log.info("start test method getCompany");
        List<Product> list = this.productRepository.findAll();
        Product product = list.get(0);
        log.info("start get company from product");
        //@ManyToOne(fetch = FetchType.EAGER)
        Company company = product.getCompany();
        assertEquals("company1", company.getName());
    }

    @Test
    @Transactional
    public void getImages() {
        log.info("start test method getImages");
        List<Product> list = this.productRepository.findAll();
        Product product = list.get(0);
        log.info("start get images from product");
        Image image = (Image) (product.getImages().toArray())[0];
        assertEquals("image1", image.getName());
    }

}

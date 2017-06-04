package com.example.jpa.mapping;

import com.example.jpa.mapping.repository.*;
import com.example.jpa.mapping.store.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
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
    CompanyRepository companyRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ImporterRepository importerRepository;

    @Autowired
    MainVersionRepository mainVersionRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SubVersionRepository subVersionRepository;

    @Autowired
    WarehouseProductInfoRepository warehouseProductInfoRepository;

    @Before
    @Transactional
    public void initData() {
        log.info("start init data ... ... ... ... ");
        //nullable = false,and no cascade , must save manual
        Company company = new Company("company1");
        this.companyRepository.save(company);
        //no cascade , must save manual
        Importer importer = new Importer("pkpk1234");
        this.importerRepository.save(importer);

        //cascade = CascadeType.ALL , auto saved
        SubVersion subVersion = new SubVersion("r100");

        MainVersion mainVersion = new MainVersion("1.0");
        mainVersion.addSubVersion(subVersion);

        Image image = new Image("image1", 0);
        image.addMainVersion(mainVersion);

        WarehouseProductInfo warehouseProductInfo = new WarehouseProductInfo(100);

        Product product = new Product("new product", "prd123", 100, company, importer);
        product.addImage(image);
        product.addWarehouse(warehouseProductInfo);
        productRepository.save(product);
    }

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
        Image image = (Image)(product.getImages().toArray())[0];
        assertEquals("image1", image.getName());
    }

}

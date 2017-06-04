package com.example.jpa.mapping;

import com.example.jpa.mapping.repository.CompanyRepository;
import com.example.jpa.mapping.repository.ImporterRepository;
import com.example.jpa.mapping.repository.ProductRepository;
import com.example.jpa.mapping.store.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class DataInitlizer implements CommandLineRunner {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    ImporterRepository importerRepository;
    @Autowired
    ProductRepository productRepository;


    @Override
    public void run(String... args) throws Exception {
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

}

@SpringBootApplication

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

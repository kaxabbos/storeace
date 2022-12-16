package com.storeace.repo;

import com.storeace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByName();
    Product findByName(String name);

    List<Product> findAllByOrderByIdDesc();

    Product findProductById(Long id);
}

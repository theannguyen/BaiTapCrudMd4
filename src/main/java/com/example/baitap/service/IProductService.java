package com.example.baitap.service;

import com.example.baitap.common.ICRUDService;
import com.example.baitap.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService extends ICRUDService<Product> {
    Page<Product> findPage(Pageable pageable);
    Page<Product> findPageBySearch(String name, Pageable pageable);
    Page<Product> findBySearch(String name, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findMaxPrice(Pageable pageable);

    Page<Product> findMinPrice(Pageable pageable);

    Page<Product> findMaxQuantity(Pageable pageable);

    Page<Product> findMinQuantity(Pageable pageable);
}

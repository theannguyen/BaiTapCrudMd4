package com.example.baitap.service.impl;

import com.example.baitap.model.Product;
import com.example.baitap.repository.IProductRepository;
import com.example.baitap.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;


public class ProductService implements IProductService {

    @Autowired
    public IProductRepository iProductRepository;

    @Override
    public Product save(Product product) {
        return iProductRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        iProductRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return iProductRepository.findById(id);
    }

    @Override
    public Page<Product> findAll() {
        return null;
    }

    @Override
    public Page<Product> findBySearch(String name, Pageable pageable) {
        return iProductRepository.findByName("%" + name +"%", pageable);
    }


    @Override
    public Page<Product> findAll(Pageable pageable) {
        return iProductRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findMaxPrice(Pageable pageable) {
        return iProductRepository.findMaxPrice(pageable);
    }

    @Override
    public Page<Product> findMinPrice(Pageable pageable) {
        return iProductRepository.findMinPrice(pageable);
    }

    @Override
    public Page<Product> findMaxQuantity(Pageable pageable) {
        return iProductRepository.findMaxQuantity(pageable);
    }

    @Override
    public Page<Product> findMinQuantity(Pageable pageable) {
        return iProductRepository.findMinQuantity(pageable);
    }

    @Override
    public Page<Product> findPage(Pageable pageable) {
        return iProductRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findPageBySearch(String name, Pageable pageable) {
        return iProductRepository.findAllByNameContaining(name, pageable);
    }
}

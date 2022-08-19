package com.example.baitap.repository;

import com.example.baitap.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from product where name like :name", nativeQuery = true)
    Page<Product> findByName(@Param("name") String name, Pageable pageable);

    @Query(value = "select * from product where price = (select max(price) from product)", nativeQuery = true)
    Page<Product> findMaxPrice(Pageable pageable);

    @Query(value = "select * from product where price = (select min(price) from product)", nativeQuery = true)
    Page<Product> findMinPrice(Pageable pageable);

    @Query(value = "select *from product where quantity = (select max(quantity) from product)", nativeQuery = true)
    Page<Product> findMaxQuantity(Pageable pageable);

    @Query(value = "select *from product where quantity = (select min(quantity) from product)", nativeQuery = true)
    Page<Product> findMinQuantity(Pageable pageable);

    Page<Product> findAllByNameContaining(String name, Pageable pageable);
}

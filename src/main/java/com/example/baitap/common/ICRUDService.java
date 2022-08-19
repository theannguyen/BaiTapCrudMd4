package com.example.baitap.common;

import org.springframework.data.domain.Page;
import java.util.Optional;

public interface ICRUDService<E> {

    E save(E e);

    void delete(Long id);

    Optional<E> findById(Long id);

    Page<E> findAll();

}

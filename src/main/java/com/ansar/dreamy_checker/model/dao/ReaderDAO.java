package com.ansar.dreamy_checker.model.dao;

import org.springframework.stereotype.Repository;

import java.util.List;


public interface ReaderDAO <T, I>{

    List<T> get();

    T get(I id);

}

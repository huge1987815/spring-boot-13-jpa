package com.huge.springboot.dao;

import com.huge.springboot.entity.Book;
import org.springframework.data.repository.CrudRepository;

/**
 * @Desc:
 * @author:huge
 * @create:2020-07-21 20:42
 */
public interface BookCrudRepository extends CrudRepository<Book,Integer> {

}

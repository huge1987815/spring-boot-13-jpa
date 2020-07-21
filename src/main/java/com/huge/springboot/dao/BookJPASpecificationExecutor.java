package com.huge.springboot.dao;

import com.huge.springboot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Desc:
 * @author:huge
 * @create:2020-07-21 22:11
 */
public interface BookJPASpecificationExecutor extends JpaRepository<Book,Integer>, JpaSpecificationExecutor<Book> {
}

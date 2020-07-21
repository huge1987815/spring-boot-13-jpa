package com.huge.springboot.dao;

import com.huge.springboot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Desc:
 * @author:huge
 * @create:2020-07-20 7:23
 */
public interface BookDao extends JpaRepository<Book,Integer> {

}

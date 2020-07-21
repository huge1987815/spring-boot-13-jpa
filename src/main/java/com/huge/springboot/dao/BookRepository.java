package com.huge.springboot.dao;

import com.huge.springboot.entity.Book;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @Desc:Repository接口
 * @author:huge
 * @create:2020-07-20 7:48
 */
public interface BookRepository extends Repository<Book,Integer> {
    //方法名命名规范:findBy(关键字)+属性名称（首字母要大写）+查询条件（首字母要大写）
    List<Book> findByBname(String name);
    List<Book> findByBnameAndAuthor(String name,String author);
    List<Book> findByBnameLike(String name);
}

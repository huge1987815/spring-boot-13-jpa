package com.huge.springboot.dao;

import com.huge.springboot.entity.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @Desc: 使用@Query注解查询
 * @author:huge
 * @create:2020-07-21 7:14
 */
public interface BookRepositoryQuery extends Repository<Book,Integer> {
    //根据书名查询(hql语句)
    @Query("from Book where bname=:bname")
    List<Book> queryByBnameHql(String bname);
    //根据书名查询（标准SQL语句，使用nativeQuery = true不用转hql语句）
    @Query(value="select * from tb_book where bname=?",nativeQuery = true)
    List<Book> queryByBnameSql(String bname);
    //根据书号修改图书信息
    @Query("update Book set bname=:bname,author=:author,price=:price where bid=:bid")
    @Modifying//需要执行一个更新操作
    int updateBook(String bname,String author,Double price,Integer bid);
}

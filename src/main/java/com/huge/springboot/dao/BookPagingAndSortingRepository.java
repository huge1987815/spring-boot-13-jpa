package com.huge.springboot.dao;

import com.huge.springboot.entity.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Desc:
 * @author:huge
 * @create:2020-07-21 21:08
 */
public interface BookPagingAndSortingRepository extends PagingAndSortingRepository<Book,Integer> {

}

package com.huge.springboot;

import com.huge.springboot.dao.*;
import com.huge.springboot.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@SpringBootTest
class SpringBoot13JpaApplicationTests {
    @Autowired
    private BookDao bookDao;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookRepositoryQuery bookRepositoryQuery;
    @Autowired
    private BookCrudRepository bookCrudRepository;
    @Autowired
    private BookPagingAndSortingRepository bookPagingAndSortingRepository;
    @Autowired
    private BookJPASpecificationExecutor bookJPASpecificationExecutor;
    //第二种方法：JPASpecificationExecutor根据书名和价格查询
    @Test
    void testJPASpecificationExecutorPrice02(){
        Specification<Book> specification=new Specification<Book>(){
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //where (bname="西游记" and price=100.0) or bid=2
                return criteriaBuilder.or(criteriaBuilder.and(criteriaBuilder.equal(root.get("bname"),"西游记"),criteriaBuilder.equal(root.get("price"),100.0)),criteriaBuilder.equal(root.get("bid"),2));
            }
        };
        List<Book> bookList = this.bookJPASpecificationExecutor.findAll(specification,Sort.by(Sort.Direction.DESC,"bid"));
        for (Book book : bookList) {
            System.out.println(book);
        }
    }
    //JPASpecificationExecutor根据书名和价格查询
    @Test
    void testJPASpecificationExecutorPrice(){
        Specification<Book> specification=new Specification<Book>(){
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list=new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("bname"),"西游记"));
                list.add(criteriaBuilder.equal(root.get("price"),100.0));
                Predicate[] predicate=new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(predicate));
            }
        };
        List<Book> bookList = this.bookJPASpecificationExecutor.findAll(specification);
        for (Book book : bookList) {
            System.out.println(book);
        }
    }
    //JPASpecificationExecutor根据书名查询
    @Test
    void testJPASpecificationExecutor(){

        Specification<Book> specification= new Specification<Book>() {
            /**
             * Root root:查询对象的属性的封装
             * CriteriaQuery criteriaQuery：封装了我们要执行的查询中的各个部分的信息，select from order by
             * CriteriaBuilder criteriaBuilder:查询条件的构造器，定义不同的查询条件
             *
             * */
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //where bname="西游记"
                /*
                 * 参数一：查询条件的属性
                 * 参数二：条件值
                 * */
                Predicate equal = criteriaBuilder.equal(root.get("bname"), "西游记");
                return equal;
            }
        };
        List<Book> bookList = this.bookJPASpecificationExecutor.findAll(specification);
        for (Book book : bookList) {
            System.out.println(book);
        }
    }
    //JPARepository实现排序
    @Test
    void testJpaRepositoryOrder(){
        List<Book> bookList = bookDao.findAll(Sort.by(Sort.Direction.DESC, "bid"));
        for (Book book : bookList) {
            System.out.println(book);
        }
    }
    //PagingAndSortingRepository实现分页排序
    @Test
    void testPagingAndSortingRepositoryPageOrder(){
        PageRequest pageable=PageRequest.of(1,2,Sort.by(Sort.Direction.DESC,"bid"));
        Page<Book> bookPage = this.bookPagingAndSortingRepository.findAll(pageable);
        System.out.println("总记录数:"+bookPage.getTotalElements());
        System.out.println("总页数："+bookPage.getTotalPages());
        System.out.println("当前页:"+bookPage.getNumber());
        List<Book> books = bookPage.getContent();
        for (Book book : books) {
            System.out.println(book);
        }
    }
    //PagingAndSortingRepository实现分页
    @Test
    void testPagingAndSortingRepositoryPage(){
        PageRequest pageable=PageRequest.of(1,2);
        Page<Book> bookPage = this.bookPagingAndSortingRepository.findAll(pageable);
        System.out.println("总记录数:"+bookPage.getTotalElements());
        System.out.println("总页数："+bookPage.getTotalPages());
        System.out.println("当前页:"+bookPage.getNumber());
        List<Book> books = bookPage.getContent();
        for (Book book : books) {
            System.out.println(book);
        }

    }
    //PagingAndSortingRepository实现排序
    @Test
    void testPagingAndSortingRepositoryOrder(){
        //Sort里面封装了排序规则对象
        Sort sort = Sort.by(Sort.Direction.DESC, "bid");
        Iterable<Book> books = this.bookPagingAndSortingRepository.findAll(sort);
        for (Book book : books) {
            System.out.println(book);
        }
    }
    //crudRepository删除图书信息
    @Test
    void testCrudRepositoryDelete(){
        if(this.bookCrudRepository.existsById(1)){
            this.bookCrudRepository.deleteById(1);
        }else{
            System.out.println("图书不存在！");
        }
    }

    //crudRepository查询所有图书信息
    @Test
    void testCrudRepositoryFindAll(){
        Iterable<Book> books = this.bookCrudRepository.findAll();
        for (Book book : books) {
            System.out.println(book);
        }
    }
    //crudRepository根据id查询
    @Test
    void testCrudRepositoryFindOne(){
        Optional<Book> book = this.bookCrudRepository.findById(5);
        System.out.println(book);
    }
    //crudRepository修改图书信息
    @Test
    void testCrudRepositoryUpdate(){
        Book book=new Book();
        book.setBid(5);
        book.setBname("西游记");
        book.setAuthor("吴承恩");
        book.setPrice(12.0);
        this.bookCrudRepository.save(book);
    }
    //crudRepository添加图书信息
    @Test
    void testCrudRepositorySave(){
        Book book=new Book();
        book.setBname("西游记");
        book.setAuthor("吴承恩");
        book.setPrice(10.0);
        this.bookCrudRepository.save(book);
    }
    //修改图书信息
    @Test
    @Transactional//@Test和 @Transactional一起使用，使事务自动回滚
    @Rollback(false)//取消自动回滚
    void testUpdateBook(){
        int i = this.bookRepositoryQuery.updateBook("汤姆叔叔的小屋","歌德",100.0,1);
        System.out.println(i>0?"修改成功":"修改失败");
    }
    //@Query根据书名查询（SQL）
    @Test
    void testQueryByBnameSql(){
        List<Book> bookList=this.bookRepositoryQuery.queryByBnameSql("西游记");
        for (Book book : bookList) {
            System.out.println(book);
        }
    }
    //@Query根据书名查询(HQL)
    @Test
    void testQueryByBnameHql(){
        List<Book> bookList=this.bookRepositoryQuery.queryByBnameHql("西游记");
        for (Book book : bookList) {
            System.out.println(book);
        }

    }
    //根据书名模糊查询
    @Test
    void testFindByBnameLike(){
        List<Book> bookList = this.bookRepository.findByBnameLike("西%");
        for (Book book : bookList) {
            System.out.println(book);
        }
    }
    //根据书名和作者查询图书信息
    @Test
    void testFindByBnameAndAuthor(){
        List<Book> bookList = this.bookRepository.findByBnameAndAuthor("西游记","吴承恩");
        for (Book book1 : bookList) {
            System.out.println(book1);
        }
    }
    //根据书名查询图书信息
    @Test
    void testFindByBname(){
        List<Book> bookList = this.bookRepository.findByBname("三国演义");
        for (Book book : bookList) {
            System.out.println(book);
        }
    }
    //添加数据
    @Test
    void contextLoads() {
        Book book=new Book();
        book.setBname("红楼梦");
        book.setAuthor("曹雪芹");
        book.setPrice(150.0);
        bookDao.save(book);
    }
}

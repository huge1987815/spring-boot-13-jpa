package com.huge.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.Value;
import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Desc:
 * @author:huge
 * @create:2020-07-20 7:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_book")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "bid")
    private Integer bid;
    @Column(name = "bname")
    private String bname;
    @Column(name = "author")
    private String author;
    @Column(name = "price")
    private Double price;

}

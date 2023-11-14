package com.api.watchshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8")
    private String name;
    @Column(unique = true)
    private String url;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8")
    private String description;
    private String imageUrl;
    @OneToMany(mappedBy = "category" , fetch = FetchType.LAZY)
    private List<Product> products;
//    private String test;


}

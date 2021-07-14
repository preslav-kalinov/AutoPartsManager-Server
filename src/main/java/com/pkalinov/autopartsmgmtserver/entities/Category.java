package com.pkalinov.autopartsmgmtserver.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "categories")
public class Category implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column (name = "name")
    private String name;
    public static final String nameFormat = "^.{1,128}$";

    public Category() {
    }//default constructor

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

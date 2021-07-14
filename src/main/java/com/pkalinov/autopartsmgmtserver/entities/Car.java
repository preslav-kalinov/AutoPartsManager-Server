package com.pkalinov.autopartsmgmtserver.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cars")
public class Car implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "brand")
    private String brand;
    public static final String brandFormat = "^.{1,128}$";

    @Column(name = "model")
    private String model;
    public static final String modelFormat = "^.{1,1024}$";

    public Car() {
    }

    public Car(Long id, String brand, String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}

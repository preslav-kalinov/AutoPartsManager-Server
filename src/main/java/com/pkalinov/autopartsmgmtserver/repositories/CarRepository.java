package com.pkalinov.autopartsmgmtserver.repositories;

import com.pkalinov.autopartsmgmtserver.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

}

package com.pkalinov.autopartsmgmtserver.repositories;

import com.pkalinov.autopartsmgmtserver.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}

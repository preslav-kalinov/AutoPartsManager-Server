package com.pkalinov.autopartsmgmtserver.dao;

import com.pkalinov.autopartsmgmtserver.entities.Car;
import com.pkalinov.autopartsmgmtserver.entities.Category;
import com.pkalinov.autopartsmgmtserver.entities.Part;
import com.pkalinov.autopartsmgmtserver.exceptions.AutoPartsManagerException;
import com.pkalinov.autopartsmgmtserver.models.PartModel;
import com.pkalinov.autopartsmgmtserver.models.SaleModel;

import java.util.List;

public interface PartDao {
    Part get(Long id) throws AutoPartsManagerException;
    List<Part> getAll() throws AutoPartsManagerException;
    Part save (PartModel p) throws AutoPartsManagerException;
    void update (Long id, PartModel p) throws AutoPartsManagerException;
    void delete (Long id) throws AutoPartsManagerException;
    Part sell(SaleModel sale, Long partId) throws AutoPartsManagerException;
    List<Category> getAllCategories() throws AutoPartsManagerException;
    List<Car> getAllCars() throws AutoPartsManagerException;

}

package com.pkalinov.autopartsmgmtserver.dao;

import com.pkalinov.autopartsmgmtserver.entities.Part;
import com.pkalinov.autopartsmgmtserver.exceptions.AutoPartsManagerException;
import com.pkalinov.autopartsmgmtserver.models.PartModel;

import java.util.List;

public interface PartDao {
    Part get(Long id) throws AutoPartsManagerException;
    List<Part> getAll() throws AutoPartsManagerException;
    Part save (PartModel p) throws AutoPartsManagerException;
    void update (PartModel p);
    void delete (PartModel p);
}
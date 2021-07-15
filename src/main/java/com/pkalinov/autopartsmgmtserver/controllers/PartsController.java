package com.pkalinov.autopartsmgmtserver.controllers;

import com.pkalinov.autopartsmgmtserver.dao.PartDao;
import com.pkalinov.autopartsmgmtserver.entities.Part;
import com.pkalinov.autopartsmgmtserver.exceptions.AutoPartsManagerException;
import com.pkalinov.autopartsmgmtserver.models.PartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import java.util.List;

@RequestMapping("/parts")
@RestController
public class PartsController {

    private final PartDao partDao;

    @Autowired
    public PartsController(PartDao partDao) {
        this.partDao = partDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity parts() throws AutoPartsManagerException {
        List<Part> partList = partDao.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(partList);
    }

    @RequestMapping(value = "/{partId}", method = RequestMethod.GET)
    public ResponseEntity getPart(@PathVariable Long partId) throws AutoPartsManagerException {
        Part part = partDao.get(partId);
        return ResponseEntity.status(HttpStatus.OK).body(part);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity createPart(@RequestBody PartModel part) throws AutoPartsManagerException {
        Part newPart = partDao.save(part);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPart);
    }
}

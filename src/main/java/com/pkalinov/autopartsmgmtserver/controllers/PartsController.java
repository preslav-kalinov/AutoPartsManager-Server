package com.pkalinov.autopartsmgmtserver.controllers;

import com.pkalinov.autopartsmgmtserver.dao.PartDao;
import com.pkalinov.autopartsmgmtserver.entities.Part;
import com.pkalinov.autopartsmgmtserver.exceptions.AutoPartsManagerException;
import com.pkalinov.autopartsmgmtserver.models.PartModel;
import com.pkalinov.autopartsmgmtserver.models.SaleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/{partId}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity editPart(@RequestBody PartModel part, @PathVariable Long partId) throws AutoPartsManagerException {
        partDao.update(partId, part);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/{partId}", method = RequestMethod.DELETE)
    public ResponseEntity deletePart(@PathVariable Long partId) throws AutoPartsManagerException {
        partDao.delete(partId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/{partId}/sale", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity sellPart(@RequestBody SaleModel sale, @PathVariable Long partId) throws AutoPartsManagerException {
        Part part = partDao.sell(sale, partId);
        return ResponseEntity.status(HttpStatus.CREATED).body(part);
    }
}

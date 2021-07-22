package com.pkalinov.autopartsmgmtserver.controllers;

import com.pkalinov.autopartsmgmtserver.dao.PartDao;
import com.pkalinov.autopartsmgmtserver.entities.Car;
import com.pkalinov.autopartsmgmtserver.entities.Category;
import com.pkalinov.autopartsmgmtserver.entities.Log;
import com.pkalinov.autopartsmgmtserver.entities.Part;
import com.pkalinov.autopartsmgmtserver.exceptions.AutoPartsManagerException;
import com.pkalinov.autopartsmgmtserver.models.PartModel;
import com.pkalinov.autopartsmgmtserver.models.SaleModel;
import com.pkalinov.autopartsmgmtserver.repositories.LogRepository;
import com.pkalinov.autopartsmgmtserver.services.FileService;
import com.pkalinov.autopartsmgmtserver.services.MailService;
import org.apache.tomcat.util.http.parser.HttpParser;
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
    private final MailService mailService;
    private final FileService fileService;
    private final LogRepository logRepository;

    @Autowired
    public PartsController(PartDao partDao, MailService mailService, FileService fileService, LogRepository logRepository) {
        this.partDao = partDao;
        this.mailService = mailService;
        this.fileService = fileService;
        this.logRepository = logRepository;
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

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity getCategories() throws AutoPartsManagerException {
        List<Category> categoryList = partDao.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categoryList);
    }

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public ResponseEntity getCars() throws AutoPartsManagerException {
        List<Car> carList = partDao.getAllCars();
        return ResponseEntity.status(HttpStatus.OK).body(carList);
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
        Log log = new Log();
        Part part = partDao.sell(sale, partId);
        String mailSubject = "New sale for part ID " + partId;
        String mailBody = "Dear AutoPartsManagement Admin,\n\nThere has been a successful sale of part ID " + partId + ", " + part.getName() + ", and there are " + part.getQuantity() + " items left of it.\n\nThis is an automated message by AutoPartsManagement Server.";
        try {
            mailService.sendSimpleMessage(mailSubject, mailBody);

        } catch (Exception e) {
            log.setErrorMessage("Cannot send mail: " + e.getMessage());
            logRepository.save(log);
        }

        try {
            fileService.writeToFile("There has been a successful sale of part ID " + partId + ", " + part.getName() + ", and there are " + part.getQuantity() + " items left of it.");
        } catch (Exception e) {
            log.setErrorMessage("Cannot write to log: " + e.getMessage());
            logRepository.save(log);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(part);
    }
}

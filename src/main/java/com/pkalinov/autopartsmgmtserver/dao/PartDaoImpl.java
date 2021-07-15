package com.pkalinov.autopartsmgmtserver.dao;

import com.pkalinov.autopartsmgmtserver.entities.Car;
import com.pkalinov.autopartsmgmtserver.entities.Category;
import com.pkalinov.autopartsmgmtserver.entities.Log;
import com.pkalinov.autopartsmgmtserver.entities.Part;
import com.pkalinov.autopartsmgmtserver.exceptions.AutoPartsManagerException;
import com.pkalinov.autopartsmgmtserver.models.PartModel;
import com.pkalinov.autopartsmgmtserver.models.SaleModel;
import com.pkalinov.autopartsmgmtserver.repositories.CarRepository;
import com.pkalinov.autopartsmgmtserver.repositories.CategoryRepository;
import com.pkalinov.autopartsmgmtserver.repositories.LogRepository;
import com.pkalinov.autopartsmgmtserver.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PartDaoImpl implements PartDao {

    private final PartRepository partRepository;
    private final LogRepository logRepository;
    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public PartDaoImpl(PartRepository partRepository, LogRepository logRepository, CarRepository carRepository, CategoryRepository categoryRepository) {
        this.partRepository = partRepository;
        this.logRepository = logRepository;
        this.carRepository = carRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Part get(Long id) throws AutoPartsManagerException {
        this.validatePartId(id);
        return partRepository.findById(id).get();
    }

    @Override
    public List<Part> getAll() throws AutoPartsManagerException {
        if(partRepository.count() == 0) {
            throw new AutoPartsManagerException(HttpServletResponse.SC_NOT_FOUND, "No parts found");
        }
        return partRepository.findAll();
    }

    @Override
    public Part save(PartModel p) throws AutoPartsManagerException {
        this.validatePartData(p);
        Category category = categoryRepository.findById(p.getCategoryId()).get();
        Car car = carRepository.findById(p.getCarId()).get();
        Part part = new Part(null, p.getName(), p.getQuantity(), p.getPrice(), category, car);
        return partRepository.save(part);
    }

    private void validatePartId(Long id) throws AutoPartsManagerException {
        Log log = new Log();
        if(partRepository.count() == 0) {
            throw new AutoPartsManagerException(HttpServletResponse.SC_NOT_FOUND, "No parts found");
        }

        if(id == null) {
            log.setErrorMessage("ID cannot be empty");
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "ID cannot be null");
        }

        if(!partRepository.existsById(id)) {
            log.setErrorMessage("This id does not exist: provided " + id);
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_NOT_FOUND, "Part not found");
        }
    }

    private void validatePartData(PartModel p) throws AutoPartsManagerException {
        Log log = new Log();

        if(p.getName() == null){
            log.setErrorMessage("Name cannot be empty");
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "Name cannot be null");
        }

        if(p.getCarId() == null){
            log.setErrorMessage("Car cannot be empty");
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "Car id cannot be null");
        }

        if(p.getCategoryId() == null){
            log.setErrorMessage("Category cannot be empty");
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "Category id cannot be null");
        }

        if(p.getQuantity() == null){
            log.setErrorMessage("Quantity cannot be empty");
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "Quantity cannot be null");
        }

        if(p.getPrice() == null){
            log.setErrorMessage("Price cannot be empty");
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "Price cannot be null");
        }

        Matcher partNameMatcher = Pattern.compile(Part.nameFormat).matcher(p.getName());

        //check for invalid part name
        if(!partNameMatcher.matches()){
            log.setErrorMessage("Invalid part name");
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "Part name must be in range 1 to 1024 characters.");
        }

        //check for cars existence
        if(carRepository.count() == 0) {
            log.setErrorMessage("No cars found");
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "Cars not found.");
        }

        //check for particular car existence
        if(!carRepository.existsById(p.getCarId())) {
            log.setErrorMessage("This car does not exist: provided " + p.getCarId());
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "This car does not exist");
        }

        //check for category existence
        if(categoryRepository.count() == 0){
            log.setErrorMessage("No categories found");
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "Categories not found.");
        }

        //check for particular category existence
        if(!categoryRepository.existsById(p.getCategoryId())) {
            log.setErrorMessage("This category does not exist: provided " + p.getCategoryId());
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "This category does not exist");
        }

        //check if quantity is a positive number
        if(p.getQuantity() <= 0){
            log.setErrorMessage("Quantity must be a positive number: provided " + p.getQuantity());
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "Quantity must be a positive number");
        }

        if(p.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            log.setErrorMessage("Price must be a positive number: provided " + p.getPrice());
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "Price must be a positive number");
        }
    }

    @Override
    public void update(Long id, PartModel p) throws AutoPartsManagerException {
        this.validatePartId(id);
        this.validatePartData(p);
        Category category = categoryRepository.findById(p.getCategoryId()).get();
        Car car = carRepository.findById(p.getCarId()).get();
        Part part = new Part(id, p.getName(), p.getQuantity(), p.getPrice(), category, car);
        partRepository.save(part);
    }

    @Override
    public void delete(Long id) throws AutoPartsManagerException {
        this.validatePartId(id);
        partRepository.deleteById(id);
    }

    @Override
    public Part sell(SaleModel sale, Long id) throws AutoPartsManagerException {
        this.validatePartId(id);
        Part part = partRepository.findById(id).get();
        this.validatePartSale(sale, part);
        Long partQuantity = part.getQuantity();
        partQuantity -= sale.getSoldQuantity();
        part.setQuantity(partQuantity);
        partRepository.save(part);
        return part;
    }

    private void validatePartSale(SaleModel sale, Part part) throws AutoPartsManagerException {
        Long partQuantity = part.getQuantity();
        Log log = new Log();
        if(sale.getSoldQuantity() <= 0 || sale.getSoldQuantity() > partQuantity) {
            log.setErrorMessage("You can buy between 1 and " + partQuantity);
            logRepository.save(log);
            throw new AutoPartsManagerException(HttpServletResponse.SC_BAD_REQUEST, "You can buy between 1 and " + partQuantity);
        }
    }
}

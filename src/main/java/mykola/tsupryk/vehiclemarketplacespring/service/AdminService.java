package mykola.tsupryk.vehiclemarketplacespring.service;

import mykola.tsupryk.vehiclemarketplacespring.entity.*;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AdminService {

    void addAdmin(String nane, String password);
    Admin findById(Long id) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    String confirm();
    List<Vehicle> findAllVehicles();
    List<Owner> findAllOwners();
    void addBrand(String brand);
    void addModel(String brand, String model);
    void addBodyType(String bodyType);

}

package mykola.tsupryk.vehiclemarketplacespring.service;

import mykola.tsupryk.vehiclemarketplacespring.dto.response.AppUserResponse;
import mykola.tsupryk.vehiclemarketplacespring.entity.*;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    void addAdmin(String email) throws NotFoundException;
    ResponseEntity<AppUserResponse> findById(Long id) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    void confirm();
    List<Vehicle> findAllVehicles();
    void addBrand(String brand);
    void addModel(String brand, String model);
    void addBodyType(String bodyType);
    public List<AppUser> findAllOwners();


}

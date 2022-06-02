package mykola.tsupryk.vehiclemarketplacespring.controller;


import lombok.extern.slf4j.Slf4j;
import mykola.tsupryk.vehiclemarketplacespring.dto.response.AppUserResponse;
import mykola.tsupryk.vehiclemarketplacespring.entity.AppUser;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*", maxAge = 3600)

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/make/admin")
    public void makeAdmin(@RequestParam String email) {
        adminService.addAdmin(email);
        log.info("In makeAdmin AdminController - user with email: '{}' now is admin", email);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<AppUserResponse> findById(@PathVariable Long id) throws NotFoundException {
        log.info("In findById AdminController - user: found by id: '{}'", id);
        return adminService.findById(id);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException {
        log.info("In delete AdminController - user detele with id '{}'", id);
        adminService.delete(id);
    }

    @GetMapping("/confirm/vehicles")
    public void confirmVehicles() {
        log.info("In confirmVehicles AdminController");
        adminService.confirm();
    }
    @GetMapping("/getAllVehicles")
    public List<Vehicle> getAllVehicles() {
        log.info("In getAllVehicles AdminController");
        return adminService.findAllVehicles();
    }

    @GetMapping("/getAllOwners")
    public List<AppUser> getAllOwners(){
        log.info("In getAllOwners AdminController");
        return adminService.findAllOwners();
    }

    @PostMapping("/add/brand")
    public void addBrand (@RequestParam String brand) {
        log.info("In addBrand AdminController");
        adminService.addBrand(brand);
    }
    @PostMapping("/add/model")
    public void addModel (@RequestParam String brand, @RequestParam String model) {
        log.info("In addModel AdminController");
        adminService.addModel(brand, model);
    }
    @PostMapping("/add/bodyType")
    public void addBodyType (@RequestParam String bodyType) {
        log.info("In addBodyType AdminController");
        adminService.addBodyType(bodyType);
    }


}

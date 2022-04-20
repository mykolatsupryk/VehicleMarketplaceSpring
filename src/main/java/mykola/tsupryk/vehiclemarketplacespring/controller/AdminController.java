package mykola.tsupryk.vehiclemarketplacespring.controller;


import mykola.tsupryk.vehiclemarketplacespring.entity.Admin;
import mykola.tsupryk.vehiclemarketplacespring.entity.Owner;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create/admin")
    public void save(@RequestParam String name, @RequestParam String password) {
        adminService.addAdmin(name, password);
    }

    @GetMapping("/search/{id}")
    public Admin findById(@PathVariable Long id) {
        return adminService.findById(id);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        adminService.delete(id);
    }

    @GetMapping("/confirm/vehicles")
    public String confirmVehicles() {
        return adminService.confirm();
    }
    @GetMapping("/getAllVehicles")
    public List<Vehicle> getAllVehicles() {
        return adminService.findAllVehicles();
    }
    @GetMapping("/getAllOwners")
    public List<Owner> getAllOwners(){
        return adminService.findAllOwners();
    }


}

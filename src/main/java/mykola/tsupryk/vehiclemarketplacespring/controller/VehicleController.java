package mykola.tsupryk.vehiclemarketplacespring.controller;


import lombok.Getter;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleCreateRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleSearchRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Owner;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.enums.BodyType;
import mykola.tsupryk.vehiclemarketplacespring.enums.Color;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;
import mykola.tsupryk.vehiclemarketplacespring.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;


    @PostMapping("/create")
    public void create (@RequestBody VehicleCreateRequest vehicleCreateRequest, @RequestParam Long ownerId) throws UnreachebleTypeException {
        vehicleService.addCar(vehicleCreateRequest, ownerId);
    }

    @PostMapping("/addphoto/{id}")
    public void addPhoto(@PathVariable Long id) {
        vehicleService.addPhoto(id);
    }

    @PostMapping("/delete/{id}")
    public void delete (@PathVariable Long id) {
        vehicleService.delete(id);
    }

    @GetMapping("/find/{id}")
    public Vehicle findById (@PathVariable Long id) {
        return vehicleService.findById(id);
    }

    @GetMapping("/findall")
    public List<Vehicle> findAll () {
//        vehicleService.findAllVehicles().stream().forEach(System.out::println);
        return vehicleService.findAllVehicles();
    }

    @GetMapping("/search")
    public List<Vehicle> search (@RequestBody VehicleSearchRequest vehicleSearchRequest
                                , @RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sortBy) {
        return vehicleService.search(vehicleSearchRequest, pageNumber, pageSize, sortBy);
    }






}

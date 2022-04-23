package mykola.tsupryk.vehiclemarketplacespring.controller;


import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleCreateRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleSearchRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;
import mykola.tsupryk.vehiclemarketplacespring.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;


    @PostMapping("/create")
    public void create (@RequestBody VehicleCreateRequest vehicleCreateRequest, @RequestParam Long ownerId) throws UnreachebleTypeException, IOException, NotFoundException {
        vehicleService.addCar(vehicleCreateRequest, ownerId);
    }

    @PostMapping("/addPhoto/{id}")
    public void addPhoto(@PathVariable Long id) throws IOException {
        vehicleService.addPhoto(id);
    }

    @PostMapping("/delete/{id}")
    public void delete (@PathVariable Long id) throws NotFoundException {
        vehicleService.delete(id);
    }

    @GetMapping("/find/{id}")
    public Vehicle findById (@PathVariable Long id) throws NotFoundException {
        return vehicleService.findById(id);
    }

    @GetMapping("/findall")
    public List<Vehicle> findAll (@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sortBy, @RequestParam String sortFrom) {
        return vehicleService.findAllVehicles(pageNumber, pageSize, sortBy, sortFrom);
    }

    @GetMapping("/search")
    public List<Vehicle> search (@RequestBody VehicleSearchRequest vehicleSearchRequest
                                , @RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sortBy, @RequestParam String sortFrom) {
        return vehicleService.search(vehicleSearchRequest, pageNumber, pageSize, sortBy, sortFrom);
    }

    @GetMapping("/similarVehicles{id}")
    public List<Vehicle> getSimilarVehicles (@PathVariable Long id) throws NotFoundException {
        return vehicleService.findSimilarVehicles(id);
    }





}

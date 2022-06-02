package mykola.tsupryk.vehiclemarketplacespring.controller;


import lombok.extern.slf4j.Slf4j;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleCreateRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleSearchRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.response.VehicleResponse;
import mykola.tsupryk.vehiclemarketplacespring.entity.Model;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;
import mykola.tsupryk.vehiclemarketplacespring.service.FilesStorageService;
import mykola.tsupryk.vehiclemarketplacespring.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private FilesStorageService filesStorageService;


    @PostMapping("/create")
    public void create (@RequestBody VehicleCreateRequest vehicleCreateRequest, @RequestParam MultipartFile file, @RequestParam Long ownerId) throws UnreachebleTypeException, IOException, NotFoundException {
        log.info("In create VehicleController");
        vehicleService.addCar(vehicleCreateRequest, file, ownerId);
    }

    @PostMapping("/{id}/addPhoto")
    public void addPhoto(@PathVariable Long id, @RequestParam MultipartFile file) throws NotFoundException {
        log.info("In addPhoto VehicleController");
        filesStorageService.savePhoto(id, file);
    }

    @GetMapping("/{id}/photo")
    @ResponseBody
    public ResponseEntity<Resource> getPhoto (@PathVariable Long id) throws NotFoundException {
        Resource file = filesStorageService.loadByVehicleId(id);
        log.info("In getPhoto VehicleController");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }


    @PostMapping("/delete/{id}")
    public void delete (@PathVariable Long id) throws NotFoundException {
        log.info("In delete VehicleController");
        vehicleService.delete(id);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<VehicleResponse> findById (@PathVariable Long id) throws NotFoundException {
        log.info("In findById VehicleController");
        return vehicleService.findById(id);
    }

    @GetMapping("/findall")
    public List<Vehicle> findAll (@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sortBy, @RequestParam String sortFrom) {
        log.info("In findAll VehicleController");
        return vehicleService.findAllVehicles(pageNumber, pageSize, sortBy, sortFrom);
    }

    @GetMapping("/search")
    public List<Vehicle> search (@RequestBody VehicleSearchRequest vehicleSearchRequest
                                , @RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sortBy, @RequestParam @Nullable String sortFrom) {
        log.info("In search VehicleController");
        return vehicleService.search(vehicleSearchRequest, pageNumber, pageSize, sortBy, sortFrom);
    }

    @GetMapping("/{id}/similarVehicles")
    public List<Vehicle> getSimilarVehicles (@PathVariable Long id) throws NotFoundException {
        log.info("In getSimilarVehicles VehicleController");
        return vehicleService.findSimilarVehicles(id);
    }

    @GetMapping("/showAllModels")
    public List<Model> getAllModels () {
        log.info("In getAllModels VehilceController");
        return vehicleService.getAllModels();
    }

    @PostMapping("/{id}/exchenge")
    public void exchangeVehicle (@RequestParam Long idVehicle, @PathVariable Long id) throws NotFoundException {
        log.info("In exchangeVehicle VehicleController");
        vehicleService.exchangeVehicle (id, idVehicle);
    }





}

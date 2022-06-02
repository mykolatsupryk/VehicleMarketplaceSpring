package mykola.tsupryk.vehiclemarketplacespring.service;

import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleCreateRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleSearchRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.response.VehicleResponse;
import mykola.tsupryk.vehiclemarketplacespring.entity.Model;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VehicleService {


    void addCar (VehicleCreateRequest vehicleCreateRequest, MultipartFile file, Long ownerId) throws UnreachebleTypeException, IOException, NotFoundException;
    void delete(Long id) throws NotFoundException;
    ResponseEntity<VehicleResponse> findById(Long id) throws NotFoundException;
    List<Vehicle> findAllVehicles (int pageNumber, int pageSize, String sortBy, String sortFrom);
    List<Vehicle> search (VehicleSearchRequest vehicleSearchRequest, int pageNumber, int pageSize, String sortBy, String sortFrom);
    List<Vehicle> findSimilarVehicles (Long id) throws NotFoundException;
    List<Model> getAllModels ();
    void exchangeVehicle(Long idFirstCar, Long idSecondCar) throws NotFoundException;


}

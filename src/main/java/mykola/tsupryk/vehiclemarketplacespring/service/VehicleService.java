package mykola.tsupryk.vehiclemarketplacespring.service;

import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleCreateRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleSearchRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Owner;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;

import java.io.IOException;
import java.util.List;

public interface VehicleService {


    void addCar (VehicleCreateRequest vehicleCreateRequest, Long ownerId) throws UnreachebleTypeException, IOException, NotFoundException;
    void addPhoto (Long id) throws IOException;
    void delete(Long id) throws NotFoundException;
    Vehicle findById(Long id) throws NotFoundException;
    List<Vehicle> findAllVehicles (int pageNumber, int pageSize, String sortBy, String sortFrom);
    List<Vehicle> search (VehicleSearchRequest vehicleSearchRequest, int pageNumber, int pageSize, String sortBy, String sortFrom);
    List<Vehicle> findSimilarVehicles (Long id) throws NotFoundException;
    String getAllModels ();
    void exchangeVehicle(Long idFirstCar, Long idSecondCar) throws NotFoundException;


}

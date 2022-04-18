package mykola.tsupryk.vehiclemarketplacespring.service;

import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleCreateRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleSearchRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Owner;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;

import java.util.List;

public interface VehicleService {


    void addCar (VehicleCreateRequest vehicleCreateRequest, Long ownerId) throws UnreachebleTypeException;
    void addPhoto (Long id);
    void delete(Long id);
    Vehicle findById(Long id);
    List<Vehicle> findAllVehicles ();
    List<Vehicle> search (VehicleSearchRequest vehicleSearchRequest, int pageNumber, int pageSize, String sortBy);



}

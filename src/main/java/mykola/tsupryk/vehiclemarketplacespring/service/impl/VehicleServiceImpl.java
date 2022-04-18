package mykola.tsupryk.vehiclemarketplacespring.service.impl;

import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleCreateRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleSearchRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Owner;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.enums.BodyType;
import mykola.tsupryk.vehiclemarketplacespring.enums.Color;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;
import mykola.tsupryk.vehiclemarketplacespring.repository.OwnerRepository;
import mykola.tsupryk.vehiclemarketplacespring.repository.VehicleRepository;
import mykola.tsupryk.vehiclemarketplacespring.repository.spec.VehicleSpecification;
import mykola.tsupryk.vehiclemarketplacespring.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;


@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private OwnerRepository ownerRepository;


    @Override
    public void addCar(VehicleCreateRequest vehicleCreateRequest, Long ownerId) throws UnreachebleTypeException {

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(vehicleCreateRequest.getBrand());
        vehicle.setModel(vehicleCreateRequest.getModel());
        vehicle.setYearOfManufacture(vehicleCreateRequest.getYearOfManufacture());
        vehicle.setBodyType(BodyType.checkBodyType(vehicleCreateRequest.getBodyType()));
        vehicle.setEnginePower(vehicleCreateRequest.getEnginePower());
        vehicle.setColor(Color.checkColor(vehicleCreateRequest.getColor()));
        vehicle.setMileAge(vehicleCreateRequest.getMileAge());
        vehicle.setPrice(vehicleCreateRequest.getPrice());

        Owner owner = ownerRepository.findAllById(ownerId);
        vehicle.setOwner(owner);
        vehicleRepository.save(vehicle);
    }

    @Override
    public void addPhoto(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).get();
        vehicle.setPhoto(new File("/home/mykolatsupryk/Desktop/KindGeek Project/" +
                                            "VehicleMarketplaceSpring/src/main/java/mykola/tsupryk/" +
                                            "vehiclemarketplacespring/photo/" + vehicle.getId() + ". " + vehicle.getBrand() + " " + vehicle.getModel()));
    }

    @Override
    public void delete(Long id) {

        vehicleRepository.delete(findById(id));
    }

    @Override
    public Vehicle findById(Long id) {

        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This car is not found"));
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAllByIsConfirm(true);
    }

    @Override
    public List<Vehicle> search(VehicleSearchRequest vehicleSearchRequest) {
        VehicleSpecification vehicleSpecification = new VehicleSpecification(vehicleSearchRequest);
        return vehicleRepository.findAll(vehicleSpecification);
    }
}

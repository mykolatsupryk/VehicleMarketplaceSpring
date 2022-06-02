package mykola.tsupryk.vehiclemarketplacespring.service.impl;

import lombok.extern.slf4j.Slf4j;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleCreateRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleSearchRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.response.VehicleResponse;
import mykola.tsupryk.vehiclemarketplacespring.entity.AppUser;
import mykola.tsupryk.vehiclemarketplacespring.entity.Model;
import mykola.tsupryk.vehiclemarketplacespring.entity.Photo;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.enums.Color;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;
import mykola.tsupryk.vehiclemarketplacespring.repository.*;
import mykola.tsupryk.vehiclemarketplacespring.repository.spec.VehicleSpecification;
import mykola.tsupryk.vehiclemarketplacespring.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private BodyTypeRepository bodyTypeRepository;
    @Autowired
    private FilesStorageServiceImpl filesStorageService;

    @Override
    public void addCar(VehicleCreateRequest vehicleCreateRequest, MultipartFile file, Long ownerId) throws UnreachebleTypeException, NotFoundException {

        Vehicle vehicle = createVehicle(vehicleCreateRequest);

        AppUser owner = appUserRepository.findById(ownerId).orElseThrow(() -> new NotFoundException("Owner"));
        vehicle.setOwner(owner);
        filesStorageService.savePhoto(vehicle, file);
        log.info("In addCar VehicleService - user: '{}' add '{} {}'", owner.getEmail(), vehicle.getBrand(), vehicle.getModel());
        vehicleRepository.save(vehicle);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle"));
        log.info("In delete VehicleService - delete vehicle with id: '{}'", id);
        vehicleRepository.delete(vehicle);
    }

    @Override
    public ResponseEntity<VehicleResponse> findById(Long id) throws NotFoundException {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle"));

        log.info("In findById VehicleService - found vehicle id: '{}', brand: '{}', model: '{}'", id, vehicle.getBrand().getName(), vehicle.getModel().getName());
        return ResponseEntity.ok(buildVehicleResponse(vehicle));
    }

    @Override
    public List<Vehicle> findAllVehicles(int pageNumber, int pageSize, String sortBy, String sortFrom) {
        PageRequest pageRequest;
        if (sortFrom.equals("end")) {
            pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        } else {
            pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        }
        List<Vehicle> vehicles = vehicleRepository.findAllByIsConfirm(true, pageRequest).getContent();
        log.info("In findAllVehicles VehicleService - found '{}' confirmed vehilcles", vehicles.size());
        return vehicles;
    }

    @Override
    public List<Vehicle> search(VehicleSearchRequest vehicleSearchRequest
                                , int pageNumber, int pageSize, String sortBy, String sortFrom) {
        VehicleSpecification vehicleSpecification = new VehicleSpecification(vehicleSearchRequest);
        PageRequest pageRequest;
        if (sortFrom != null) {
            if (sortFrom.equals("end")) {
                pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
            } else {
                pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
            }
        } else {
            pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        }
        List<Vehicle> vehicles = vehicleRepository.findAll(vehicleSpecification, pageRequest).getContent();
        log.info("In search VehicleService - found '{}' custom vehilcles", vehicles.size());
        return vehicles;
    }

    @Override
    public List<Vehicle> findSimilarVehicles(Long id) throws NotFoundException {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehicle"));
        List<Vehicle> similarVehicles = new ArrayList<>();
        AtomicInteger countOfIdealChoice = new AtomicInteger();
        vehicleRepository.findAllByIsConfirm(true).stream().filter(v -> v.getBodyType().equals(vehicle.getBodyType())).forEach(v -> {
            if (v.getPrice() >= (vehicle.getPrice() * 0.8)
                    && v.getPrice() <= (vehicle.getPrice() * 1.2)) {
                if (v.getYearOfManufacture() >= (vehicle.getYearOfManufacture() - 3)
                    && v.getYearOfManufacture() <= (vehicle.getYearOfManufacture() + 3)) {
                    countOfIdealChoice.getAndIncrement();
                    similarVehicles.add(0, v);
                } else {
                    similarVehicles.add(countOfIdealChoice.get(), v);
                }
            } else {
                similarVehicles.add(v);
            }
        });
        similarVehicles.remove(vehicle);
        log.info("In findSimilarVehicles VehicleService - '{} {}' have similar car with id: '{}', '{}', '{}'", vehicle.getBrand(), vehicle.getModel()
                                                                                                                , similarVehicles.get(0).getId()
                                                                                                                , similarVehicles.get(1).getId()
                                                                                                                , similarVehicles.get(2).getId());
        return similarVehicles.subList(0, 3);
    }

    @Override
    public List<Model> getAllModels() {
        List<Model> models = modelRepository.findAll();
        log.info("In getAllModels VehicleService - found '{}' models", models.size());
        return models;
    }

    @Override
    public void exchangeVehicle(Long idFirstCar, Long idSecondCar) throws NotFoundException {
        Vehicle firstVehicle = vehicleRepository.findById(idFirstCar).orElseThrow(() -> new NotFoundException("First vehicle"));
        Vehicle secondVehicle = vehicleRepository.findById(idSecondCar).orElseThrow(() -> new NotFoundException("Second vehicle"));
        AppUser firstOwner = firstVehicle.getOwner();
        AppUser secondOwner = secondVehicle.getOwner();
        log.info("In exchangeVehicle VehicleService - user: '{}' changed '{}. {} {}' for '{}. {} {}' '{}'"  , firstOwner.getEmail()
                                                                                                            , firstVehicle.getId()
                                                                                                            , firstVehicle.getBrand()
                                                                                                            , firstVehicle.getModel()
                                                                                                            , secondVehicle.getId()
                                                                                                            , secondVehicle.getBrand()
                                                                                                            , secondVehicle.getModel()
                                                                                                            , secondOwner.getEmail());
        firstVehicle.setOwner(secondOwner);
        secondVehicle.setOwner(firstOwner);
        vehicleRepository.save(firstVehicle);
        vehicleRepository.save(secondVehicle);
    }

    private Vehicle createVehicle (VehicleCreateRequest vehicleCreateRequest) throws UnreachebleTypeException, NotFoundException {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brandRepository.findById(vehicleCreateRequest.getBrandId()).orElseThrow(() -> new NotFoundException("brandId")));
        vehicle.setModel((modelRepository.findById(vehicleCreateRequest.getModelId()).orElseThrow(() -> new NotFoundException("modelId"))));
        vehicle.setYearOfManufacture(vehicleCreateRequest.getYearOfManufacture());
        vehicle.setBodyType(bodyTypeRepository.findById(vehicleCreateRequest.getBodyTypeId()).orElseThrow(() -> new NotFoundException("bodyTypeId")));
        vehicle.setEnginePower(vehicleCreateRequest.getEnginePower());
        vehicle.setColor(Color.checkColor(vehicleCreateRequest.getColor()));
        vehicle.setMileAge(vehicleCreateRequest.getMileAge());
        vehicle.setPrice(vehicleCreateRequest.getPrice());
        return vehicle;
    }

    private VehicleResponse buildVehicleResponse (Vehicle vehicle) {
        VehicleResponse response = new VehicleResponse();

        response.setId(vehicle.getId());
        response.setBrand(vehicle.getBrand().getName());
        response.setModel(vehicle.getModel().getName());
        response.setYearOfManufacture(vehicle.getYearOfManufacture());
        response.setBodyType(vehicle.getBodyType().getName());
        response.setEnginePower(vehicle.getEnginePower());
        response.setColor(vehicle.getColor().name());
        response.setMileAge(vehicle.getMileAge());
        response.setPrice(vehicle.getPrice());

        return response;
    }
}



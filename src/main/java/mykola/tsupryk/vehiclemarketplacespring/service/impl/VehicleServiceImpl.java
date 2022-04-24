package mykola.tsupryk.vehiclemarketplacespring.service.impl;

import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleCreateRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleSearchRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Owner;
import mykola.tsupryk.vehiclemarketplacespring.entity.Photo;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.entity.BodyType;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.enums.Color;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;
import mykola.tsupryk.vehiclemarketplacespring.repository.*;
import mykola.tsupryk.vehiclemarketplacespring.repository.spec.VehicleSpecification;
import mykola.tsupryk.vehiclemarketplacespring.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private BodyTypeRepository bodyTypeRepository;

    @Override
    public void addCar(VehicleCreateRequest vehicleCreateRequest, Long ownerId) throws UnreachebleTypeException, NotFoundException {

        Vehicle vehicle = createVehicle(vehicleCreateRequest);

        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new NotFoundException("Owner"));
        vehicle.setOwner(owner);
        vehicleRepository.save(vehicle);
    }

    @Override
    public void addPhoto(Long id) throws IOException {
        Vehicle vehicle = vehicleRepository.findById(id).get();

        Photo photo = new Photo();

        File folder = new File(Photo.PATH + vehicle.getId() + ". " + vehicle.getBrand() + " " + vehicle.getModel());
        if (!folder.exists()) {
            folder.mkdir();
        }
        int nameNewPhoto = folder.listFiles().length + 1;
        File file = new File(folder.getPath() + "/" + nameNewPhoto + ".jpg" );
        photo.setVehicle(vehicle);
        photo.setPhoto(file);
        file.createNewFile();

        photoRepository.save(photo);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        vehicleRepository.delete(findById(id));
    }

    @Override
    public Vehicle findById(Long id) throws NotFoundException {
        return vehicleRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Vehicle"));
    }

    @Override
    public List<Vehicle> findAllVehicles(int pageNumber, int pageSize, String sortBy, String sortFrom) {
        PageRequest pageRequest;
        if (sortFrom.equals("end")) {
            pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        } else {
            pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        }
        return vehicleRepository.findAllByIsConfirm(true, pageRequest).getContent();
    }

    @Override
    public List<Vehicle> search(VehicleSearchRequest vehicleSearchRequest
                                , int pageNumber, int pageSize, String sortBy, String sortFrom) {
        VehicleSpecification vehicleSpecification = new VehicleSpecification(vehicleSearchRequest);
        PageRequest pageRequest;
        if (sortFrom.equals("end")) {
            pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        } else {
            pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        }
        return vehicleRepository.findAll(vehicleSpecification, pageRequest).getContent();
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
        return similarVehicles.subList(0, 3);
    }

    @Override
    public String getAllModels() {
        StringBuilder builder = new StringBuilder();
        brandRepository.findAll().stream().forEach(brand -> {
            builder.append("\n").append(brand.getBrand()).append("\n\t");
            modelRepository.findAll().stream().filter(model -> model.getBrand().equals(brand)).forEach(model -> {
                builder.append(model.getModel()).append(", ");
            });
        });
        return builder.toString();
    }

    @Override
    public void exchangeVehicle(Long idFirstCar, Long idSecondCar) throws NotFoundException {
        Vehicle firstVehicle = vehicleRepository.findById(idFirstCar).orElseThrow(() -> new NotFoundException("First vehicle"));
        Vehicle secondVehicle = vehicleRepository.findById(idSecondCar).orElseThrow(() -> new NotFoundException("Second vehicle"));
        Owner firstOwner = firstVehicle.getOwner();
        Owner secondOwner = secondVehicle.getOwner();
        firstVehicle.setOwner(secondOwner);
        secondVehicle.setOwner(firstOwner);
        vehicleRepository.save(firstVehicle);
        vehicleRepository.save(secondVehicle);
    }

    private Vehicle createVehicle (VehicleCreateRequest vehicleCreateRequest) throws UnreachebleTypeException, NotFoundException {
        Vehicle vehicle = new Vehicle();
        if (brandRepository.findAll().stream().anyMatch(brand -> brand.getBrand().equalsIgnoreCase(vehicleCreateRequest.getBrand()))) {
            vehicle.setBrand(brandRepository.findByBrandIgnoreCase(vehicleCreateRequest.getBrand()));
        } else throw new NotFoundException("Admin must first create this brand. It");

        if (modelRepository.findAll().stream().anyMatch(model -> model.getModel().equalsIgnoreCase(vehicleCreateRequest.getModel()))) {
            vehicle.setModel(modelRepository.findByModelIgnoreCase(vehicleCreateRequest.getModel()));
        } else throw new NotFoundException("Admin must first create this model. It");

        vehicle.setYearOfManufacture(vehicleCreateRequest.getYearOfManufacture());

        if (bodyTypeRepository.findAll().stream().anyMatch(bodyType -> bodyType.getBodyType().equalsIgnoreCase(vehicleCreateRequest.getBodyType()))) {
            vehicle.setBodyType(bodyTypeRepository.findByBodyTypeIgnoreCase(vehicleCreateRequest.getBodyType()));
        } else throw new NotFoundException("Admin must first create this bodyType. It");

        vehicle.setEnginePower(vehicleCreateRequest.getEnginePower());
        vehicle.setColor(Color.checkColor(vehicleCreateRequest.getColor()));
        vehicle.setMileAge(vehicleCreateRequest.getMileAge());
        vehicle.setPrice(vehicleCreateRequest.getPrice());

        return vehicle;
    }
}



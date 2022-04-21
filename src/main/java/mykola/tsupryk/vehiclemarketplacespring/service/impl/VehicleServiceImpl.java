package mykola.tsupryk.vehiclemarketplacespring.service.impl;

import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleCreateRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleSearchRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Owner;
import mykola.tsupryk.vehiclemarketplacespring.entity.Photo;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.enums.BodyType;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.enums.Color;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;
import mykola.tsupryk.vehiclemarketplacespring.repository.OwnerRepository;
import mykola.tsupryk.vehiclemarketplacespring.repository.PhotoRepository;
import mykola.tsupryk.vehiclemarketplacespring.repository.VehicleRepository;
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
    public void delete(Long id) {
        vehicleRepository.delete(findById(id));
    }

    @Override
    public Vehicle findById(Long id) {
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
    public List<Vehicle> findSimilarVehicles(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehicle"));
        List<Vehicle> similarVehicles = new ArrayList<>();
        AtomicInteger countOfIdealChoice = new AtomicInteger();
        vehicleRepository.findAll().stream().filter(v -> v.getBodyType().equals(vehicle.getBodyType())).forEach(v -> {
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
}



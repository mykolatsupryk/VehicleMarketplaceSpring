package mykola.tsupryk.vehiclemarketplacespring.service.impl;

import lombok.extern.slf4j.Slf4j;
import mykola.tsupryk.vehiclemarketplacespring.entity.Photo;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.repository.PhotoRepository;
import mykola.tsupryk.vehiclemarketplacespring.repository.VehicleRepository;
import mykola.tsupryk.vehiclemarketplacespring.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public void savePhoto(Long id, MultipartFile file) throws NotFoundException {

        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehicle"));
        Photo photo = new Photo();
        photo.setVehicle(vehicle);

        Path pathFolder = Photo.PATH.resolve(vehicle.getId() + ". " + vehicle.getBrand().getName() + " " + vehicle.getModel().getName());
        File folder = new File(pathFolder.toString());
        if (!folder.exists()) {
            folder.mkdir();
        }

        String[] fileExtension = file.getContentType().split("/");

        int countOfPhotos = folder.listFiles().length;
        try {
            Path pathFile = pathFolder.resolve((countOfPhotos + 1) + " photo." + fileExtension[1]);
            Files.copy(file.getInputStream(), pathFile, StandardCopyOption.REPLACE_EXISTING);
            photo.setPhotoUrl(pathFile.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        log.info("In savePhoto FilesStorageServiceImpl - added photo to vehicle.id: '{}'", vehicle.getId());
        photoRepository.save(photo);
    }

    @Override
    public Resource loadByVehicleId(Long id) throws NotFoundException {

        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehicle"));
        try {
            Path file = Paths.get(photoRepository.findByVehicle(vehicle).getPhotoUrl());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                log.info("In loadByVehicleId FilesStorageServiceImpl - load photo to vehicle.id: '{}'", vehicle.getId());
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void savePhoto(Vehicle vehicle, MultipartFile file) throws NotFoundException {

        Photo photo = new Photo();
        photo.setVehicle(vehicle);

        Path pathFolder = Photo.PATH.resolve(vehicle.getId() + ". " + vehicle.getBrand().getName() + " " + vehicle.getModel().getName());
        File folder = new File(pathFolder.toString());
        if (!folder.exists()) {
            folder.mkdir();
        }

        String[] fileExtension = file.getContentType().split("/");

        int countOfPhotos = folder.listFiles().length;
        try {
            Path pathFile = pathFolder.resolve((countOfPhotos + 1) + " photo." + fileExtension[1]);
            Files.copy(file.getInputStream(), pathFile, StandardCopyOption.REPLACE_EXISTING);
            photo.setPhotoUrl(pathFile.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        log.info("In savePhoto FilesStorageServiceImpl - added photo to vehicle.id: '{}'", vehicle.getId());
        photoRepository.save(photo);
    }


}

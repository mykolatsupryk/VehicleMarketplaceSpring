package mykola.tsupryk.vehiclemarketplacespring.service;


import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FilesStorageService {

    void savePhoto(Long id, MultipartFile file) throws NotFoundException;
    void savePhoto(Vehicle vehicle, MultipartFile file) throws NotFoundException;

    Resource loadByVehicleId(Long id) throws NotFoundException;


}
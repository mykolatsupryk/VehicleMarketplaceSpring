package mykola.tsupryk.vehiclemarketplacespring.service;


import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {

    void savePhoto(Long id, MultipartFile file) throws NotFoundException;

    Resource loadByVehicleId(Long id);

}
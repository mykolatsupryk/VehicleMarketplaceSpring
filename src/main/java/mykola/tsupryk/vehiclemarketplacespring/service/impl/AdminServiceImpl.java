package mykola.tsupryk.vehiclemarketplacespring.service.impl;


import mykola.tsupryk.vehiclemarketplacespring.entity.*;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.Company;
import mykola.tsupryk.vehiclemarketplacespring.exception.NoMoneyException;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.repository.*;
import mykola.tsupryk.vehiclemarketplacespring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private BodyTypeRepository bodyTypeRepository;

    public void addAdmin(String name, String passwword) {

        Admin admin = new Admin();
        admin.setName(name);
        admin.setPassword(passwword);
        adminRepository.save(admin);
    }

    @Override
    public Admin findById(Long id) throws NotFoundException {
        return adminRepository.findById(id).orElseThrow(() -> new NotFoundException("Admin"));
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        adminRepository.delete(findById(id));
    }

    @Override
    public String confirm() {
        StringBuilder stringBuilder = new StringBuilder("Admin confirm ");
        AtomicReference<Integer> count = new AtomicReference<>(0);
        vehicleRepository.findAllByIsConfirm(false).stream().forEach((vehicle -> {
            if (payToOrder(vehicle)) {
                vehicle.setIsConfirm(true);
                vehicleRepository.save(vehicle);
                count.getAndSet(count.get() + 1);
            } else throw new NoMoneyException(vehicle);
        }));

        return String.valueOf(stringBuilder.append(count).append(" vehicles"));
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public List<Owner> findAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public void addBrand(String brand) {
        Brand newBrand;

        boolean isBrandCreated = brandRepository.findAll().stream().anyMatch(b -> b.getBrand().equalsIgnoreCase(brand));
        if (!isBrandCreated) {
            newBrand = new Brand();
            newBrand.setBrand(brand);
        } else {
            newBrand = brandRepository.findByBrandIgnoreCase(brand);
        }
        brandRepository.save(newBrand);
    }

    @Override
    public void addModel(String brand, String model) {
        Brand brandValue;
        Model newModel;
        boolean isBrandCreated = brandRepository.findAll().stream().anyMatch(b -> b.getBrand().equalsIgnoreCase(brand));
        if (!isBrandCreated) {
            addBrand(brand);
        }
        brandValue = brandRepository.findByBrandIgnoreCase(brand);
        boolean isModelCreated = modelRepository.findAll().stream().anyMatch(b -> b.getModel().equalsIgnoreCase(model));
        modelRepository.findByModelIgnoreCase(model);
        if (!isModelCreated) {
            newModel = new Model();
            newModel.setBrand(brandRepository.findByBrandIgnoreCase(brand));
            newModel.setModel(model);
            newModel.setBrand(brandValue);
            modelRepository.save(newModel);
        }
    }

    @Override
    public void addBodyType(String bodyType) {
        if (!bodyTypeRepository.findAll().stream().anyMatch(b -> b.getBodyType().equalsIgnoreCase(bodyType))) {
            BodyType newBodyType = new BodyType();
            newBodyType.setBodyType(bodyType);
            bodyTypeRepository.save(newBodyType);
        }
    }



    private boolean payToOrder(Vehicle vehicle) {
        Owner owner = vehicle.getOwner();
        if (owner.getMoney() != null) {
            if (owner instanceof Company) {
                if (owner.getMoney() >= 20) {
                    owner.setMoney(owner.getMoney() - 20);
                    return true;
                } else {
                    return false;
                }
            } else {
                if (owner.getMoney() >= 30) {
                    owner.setMoney(owner.getMoney() - 30);
                    return true;
                } else {
                    return false;
                }
            }
        }else {
            return false;
        }
    }
}

package mykola.tsupryk.vehiclemarketplacespring.service.impl;


import lombok.extern.slf4j.Slf4j;
import mykola.tsupryk.vehiclemarketplacespring.dto.response.AppUserResponse;
import mykola.tsupryk.vehiclemarketplacespring.entity.*;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.enums.AppUserRole;
import mykola.tsupryk.vehiclemarketplacespring.exception.NoMoneyException;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.repository.*;
import mykola.tsupryk.vehiclemarketplacespring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {


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
    private RoleRepository roleRepository;

    public void addAdmin(String email) throws NotFoundException {

        AppUser user = appUserRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Error: appUser with email '" + email + "'"));
        user.getRoles().add(roleRepository.findByName(AppUserRole.ROLE_ADMIN).orElseThrow(() -> new NotFoundException("Error: ROLE_ADMIN")));
        log.info("In addAdmin AdminService - make user: '{}' to admin", user.getEmail());
        appUserRepository.save(user);
    }

    @Override
    public ResponseEntity<AppUserResponse> findById(Long id) throws NotFoundException {
        AppUser user = appUserRepository.findById(id).orElseThrow(() -> new NotFoundException("AppUser"));
        log.info("In findById AdminService - found admin id: '{}'", id);
        return ResponseEntity.ok(buildAppUserResponse(user));
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        AppUser user = appUserRepository.findById(id).orElseThrow(() -> new NotFoundException("AppUser"));
        log.info("In delete AdminService - delete admin id: '{}'", id);
        appUserRepository.delete(user);
    }

    @Override
    public void confirm() {
        AtomicReference<Integer> count = new AtomicReference<>(0);
        vehicleRepository.findAllByIsConfirm(false).stream().forEach((vehicle -> {
            if (payToOrder(vehicle)) {
                vehicle.setIsConfirm(true);
                vehicleRepository.save(vehicle);
                count.getAndSet(count.get() + 1);
            } else throw new NoMoneyException(vehicle);
        }));
        log.info("In confirm AdminService - {} vehicles confirmed", count);
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        log.info("In findAllVehicles AdminService - found {} vehicles", vehicles.size());
        return vehicles;
    }

    @Override
    public List<AppUser> findAllOwners() {
        List<AppUser> users = appUserRepository.findAll();
        List<AppUser> result = new ArrayList<>();
        Role companyRole = roleRepository.findByName(AppUserRole.ROLE_USER_COMPANY).orElseThrow(() -> new NotFoundException("ROLE_USER_COMPANY"));
        Role personRole = roleRepository.findByName(AppUserRole.ROLE_USER_PERSON).orElseThrow(() -> new NotFoundException("ROLE_USER_PERSON"));
        users.stream().forEach(user -> {
            if (user.getRoles().contains(companyRole) || user.getRoles().contains(personRole)) {
                result.add(user);
            }
        });
        log.info("In findAllOwners AdminService - found {} owners", result.size());
        return result;
    }

    @Override
    public void addBrand(String brand) {
        boolean isBrandCreated = brandRepository.findAll().stream().anyMatch(b -> b.getName().equalsIgnoreCase(brand));
        if (!isBrandCreated) {
            Brand newBrand = new Brand();
            newBrand.setName(brand);
            brandRepository.save(newBrand);
            log.info("In addBrand AdminService - added brand: '{}'", brand);
        } else {
            log.info("In addBrand AdminService - brand: '{}' was exist", brand);
        }
    }

    @Override
    public void addModel(String brand, String model) {
        Brand brandValue;
        Model newModel;
        boolean isBrandCreated = brandRepository.findAll().stream().anyMatch(b -> b.getName().equalsIgnoreCase(brand));
        if (!isBrandCreated) {
            addBrand(brand);
        }
        brandValue = brandRepository.findByNameIgnoreCase(brand);
        boolean isModelCreated = modelRepository.findAll().stream().anyMatch(b -> b.getName().equalsIgnoreCase(model));
        modelRepository.findByNameIgnoreCase(model);
        if (!isModelCreated) {
            newModel = new Model();
            newModel.setBrand(brandValue);
            newModel.setName(model);
            brandValue.getModels().add(newModel);
            modelRepository.save(newModel);
            brandRepository.save(brandValue);
            log.info("In addModel AdminService - added model: '{}'", model);
        } else {
            log.info("In addModel AdminService - model: '{}' was exist", model);
        }
    }

    @Override
    public void addBodyType(String bodyType) {
        if (!bodyTypeRepository.findAll().stream().anyMatch(b -> b.getName().equalsIgnoreCase(bodyType))) {
            BodyType newBodyType = new BodyType();
            newBodyType.setName(bodyType);
            bodyTypeRepository.save(newBodyType);
            log.info("In addBodyType AdminService - added bodyType: '{}'", bodyType);
        } else {
            log.info("In addBodyType AdminService - bodyType: '{}' was exist", bodyType);
        }
    }


    private boolean payToOrder(Vehicle vehicle) throws NotFoundException {

        AppUser user = appUserRepository.findById(vehicle.getOwner().getId()).orElseThrow(() -> new NotFoundException("Owner"));

        if (user.getMoney() != null) {
            if (user.getRoles().contains(roleRepository.findByName(AppUserRole.ROLE_USER_PERSON)
                                                        .orElseThrow(() -> new NotFoundException("ROLE_USER_PERSON")))) {
                user.setMoney(user.getMoney() - 30);
            } else if (user.getRoles().contains(roleRepository.findByName(AppUserRole.ROLE_USER_COMPANY)
                                                                .orElseThrow(() -> new NotFoundException("ROLE_USER_COMPANY")))) {
                user.setMoney(user.getMoney() - 20);
            }
            return user.getMoney() >= 0;
        }
        return false;
    }

    private AppUserResponse buildAppUserResponse (AppUser user) {
        AppUserResponse response = new AppUserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setContactNumber(user.getContactNumber());
        response.setCity(user.getCity());
        response.setMoney(user.getMoney());
        user.getRoles().stream().map(Role::getName).forEach(role -> response.getRoles().add(role.name()));

        return response;
    }
}
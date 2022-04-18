package mykola.tsupryk.vehiclemarketplacespring.service.impl;


import mykola.tsupryk.vehiclemarketplacespring.entity.Admin;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.repository.AdminRepository;
import mykola.tsupryk.vehiclemarketplacespring.repository.VehicleRepository;
import mykola.tsupryk.vehiclemarketplacespring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    public void addAdmin(String name, String passwword) {

        Admin admin = new Admin();
        admin.setName(name);
        admin.setPassword(passwword);
        adminRepository.save(admin);
    }

    @Override
    public Admin findById(Long id) {
        return adminRepository.findById(id).orElseThrow(() -> new RuntimeException("This admin is not found"));
    }

    @Override
    public void delete(Long id) {
        adminRepository.delete(findById(id));
    }

    @Override
    public String confirm() {
        StringBuilder stringBuilder = new StringBuilder("Admin confirm ");
        AtomicReference<Integer> count = new AtomicReference<>(0);
        vehicleRepository.findAllByIsConfirm(false).stream().forEach((vehicle -> {
            vehicle.setIsConfirm(true);
            vehicleRepository.save(vehicle);
            count.getAndSet(count.get() + 1);
        }));

        return String.valueOf(stringBuilder.append(count).append(" vehicles"));
    }











}

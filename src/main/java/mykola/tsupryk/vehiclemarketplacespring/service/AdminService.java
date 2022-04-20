package mykola.tsupryk.vehiclemarketplacespring.service;

import mykola.tsupryk.vehiclemarketplacespring.entity.Admin;
import mykola.tsupryk.vehiclemarketplacespring.entity.Owner;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;

import java.util.List;

public interface AdminService {

    void addAdmin(String nane, String password);

    Admin findById(Long id);

    void delete(Long id);

    String confirm();
    List<Vehicle> findAllVehicles();
    List<Owner> findAllOwners();

}

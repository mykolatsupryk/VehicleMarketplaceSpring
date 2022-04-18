package mykola.tsupryk.vehiclemarketplacespring.service;

import mykola.tsupryk.vehiclemarketplacespring.entity.Admin;

public interface AdminService {

    void addAdmin(String nane, String password);

    Admin findById(Long id);

    void delete(Long id);

    String confirm();

}

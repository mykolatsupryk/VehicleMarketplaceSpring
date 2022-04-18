package mykola.tsupryk.vehiclemarketplacespring.service.impl;

import mykola.tsupryk.vehiclemarketplacespring.dto.request.OwnerRegistryRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Owner;
import mykola.tsupryk.vehiclemarketplacespring.repository.OwnerRepository;
import mykola.tsupryk.vehiclemarketplacespring.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public void registry(OwnerRegistryRequest ownerRegistryRequest) {
        Owner owner = new Owner();
        owner.setName(ownerRegistryRequest.getName());
        owner.setEmail(ownerRegistryRequest.getEmail());
        owner.setPassword(ownerRegistryRequest.getPassword());
        ownerRepository.save(owner);
    }
}

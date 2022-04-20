package mykola.tsupryk.vehiclemarketplacespring.controller;


import mykola.tsupryk.vehiclemarketplacespring.dto.request.OwnerRegistryRequest;
import mykola.tsupryk.vehiclemarketplacespring.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping("/registry")
    public void registry(@RequestBody @Valid OwnerRegistryRequest ownerRegistryRequest) {
        ownerService.registry(ownerRegistryRequest);
    }






}

package mykola.tsupryk.vehiclemarketplacespring.controller;


import mykola.tsupryk.vehiclemarketplacespring.dto.request.OwnerRegistryRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Feedback;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping("/registry/company")
    public void registryCompany(@RequestBody @Valid OwnerRegistryRequest ownerRegistryRequest) {
        ownerService.registryCompany(ownerRegistryRequest);
    }

    @PostMapping("/registry/person")
    public void registryPerson(@RequestBody @Valid OwnerRegistryRequest ownerRegistryRequest) {
        ownerService.registryPerson(ownerRegistryRequest);
    }

    @PostMapping("/rating{id}:{rate}")
    public void addRating (@PathVariable Long id, @PathVariable Integer rate) throws NotFoundException {
        ownerService.setRating(id, rate);
    }

    @GetMapping("/{id}/getRating")
    public Double getRating(@PathVariable Long id) throws NotFoundException {
        return ownerService.getRating(id);
    }

    @PostMapping("/{id}/add/feedback")
    public void addFeedback (@RequestParam Long idComentator, @RequestParam String text, @PathVariable Long id) throws NotFoundException {
        ownerService.addFeedback(idComentator, text, id);
    }
    @GetMapping("/{id}/feedback")
    public List<Feedback> getFeedbacks (@PathVariable Long id) throws NotFoundException {
        return ownerService.getFeedbacks(id);
    }

    @PostMapping("/{id}/addMoney")
    public void addMoney (@PathVariable Long id, @RequestParam Integer money) throws NotFoundException {
        ownerService.addMoney(id, money);
    }








}

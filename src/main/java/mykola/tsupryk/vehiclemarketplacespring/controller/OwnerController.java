package mykola.tsupryk.vehiclemarketplacespring.controller;


import mykola.tsupryk.vehiclemarketplacespring.dto.request.OwnerRegistryRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Feedback;
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

    @PostMapping("/registry")
    public void registry(@RequestBody @Valid OwnerRegistryRequest ownerRegistryRequest) {
        ownerService.registry(ownerRegistryRequest);
    }

    @PostMapping("/rating{id}:{rate}")
    public void addRating (@PathVariable Long id, @PathVariable Integer rate) {
        ownerService.setRating(id, rate);
    }

    @GetMapping("/getRating{id}")
    public Double getRating(@PathVariable Long id) {
        return ownerService.getRating(id);
    }

    @PostMapping("/{id}/add/feedback")
    public void addFeedback (@RequestParam Long idComentator, @RequestParam String text, @PathVariable Long id) {
        ownerService.addFeedback(idComentator, text, id);
    }
    @GetMapping("/{id}/feedback")
    public List<Feedback> getFeedbacks (@PathVariable Long id) {
        return ownerService.getFeedbacks(id);
    }








}

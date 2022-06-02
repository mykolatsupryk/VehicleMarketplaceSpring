package mykola.tsupryk.vehiclemarketplacespring.controller;


import lombok.extern.slf4j.Slf4j;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.FeedbackAddRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.RatingAddRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.SignupRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleChangeRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.response.MessageResponse;
import mykola.tsupryk.vehiclemarketplacespring.entity.Feedback;
import mykola.tsupryk.vehiclemarketplacespring.exception.InvalidContactNumberException;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;
import mykola.tsupryk.vehiclemarketplacespring.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@RestController
@RequestMapping("/user")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/endpoint")
    public String endpoint(){
        log.info("In endpoint OwnerController - Success");
        return "Success";
    }


    @PostMapping("/addRating")
    public void addRating (@RequestBody RatingAddRequest ratingAddRequest) throws NotFoundException {
        log.info("In addRating OwnerController - user: id '{}', rate '{}'", ratingAddRequest.getId(), ratingAddRequest.getRate());
        ownerService.setRating(ratingAddRequest.getId(), ratingAddRequest.getRate());
    }

    @GetMapping("/{id}/getRating")
    public ResponseEntity<MessageResponse> getRating(@PathVariable Long id) throws NotFoundException {
        log.info("In getRating OwnerController - rating user with id '{}'", id);
        return ownerService.getRating(id);
    }

    @PostMapping("/add/feedback")
    public void addFeedback (@RequestBody FeedbackAddRequest feedbackAddRequest) throws NotFoundException {
        log.info("In addFeedback OwnerController");
        ownerService.addFeedback(feedbackAddRequest.getComentatorId(), feedbackAddRequest.getText(), feedbackAddRequest.getUserId());
    }
    @GetMapping("/{id}/feedback")
    public List<Feedback> getFeedbacks (@PathVariable Long id) throws NotFoundException {
        log.info("In getFeedbacks OwnerController");
        return ownerService.getFeedbacks(id);
    }

    @PostMapping("/{id}/add/money")
    public void addMoney (@PathVariable Long id, @RequestParam Integer money) throws NotFoundException {
        log.info("In addMoney OwnerController");
        ownerService.addMoney(id, money);
    }

    @PostMapping("/{id}/add/city")
    public void addCity (@PathVariable Long id, @RequestParam String city) throws NotFoundException {
        log.info("In addCity OwnerController");
        ownerService.addCity(id, city);
    }

    @PostMapping("/{id}/add/contactNumber")
    public void addContactNumber (@PathVariable Long id, @RequestParam String contactNumber) throws NotFoundException, InvalidContactNumberException {
        log.info("In addContactNumber OwnerController");
        ownerService.addContactNumber(id, contactNumber);
    }

    @PostMapping("/editVehicle")
    public void editVehicle (@RequestBody VehicleChangeRequest vehicleChangeRequest) throws NotFoundException, UnreachebleTypeException {
        log.info("In editVehicle OwnerController");
        ownerService.editVehicle(vehicleChangeRequest.getId(), vehicleChangeRequest);
    }







}

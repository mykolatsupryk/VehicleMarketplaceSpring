package mykola.tsupryk.vehiclemarketplacespring.service.impl;

import lombok.extern.slf4j.Slf4j;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.SignupRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleChangeRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.response.MessageResponse;
import mykola.tsupryk.vehiclemarketplacespring.entity.*;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.enums.AppUserRole;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.enums.Color;
import mykola.tsupryk.vehiclemarketplacespring.exception.InvalidContactNumberException;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;
import mykola.tsupryk.vehiclemarketplacespring.repository.*;
import mykola.tsupryk.vehiclemarketplacespring.service.OwnerService;
import mykola.tsupryk.vehiclemarketplacespring.validator.ContactNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private RoleRepository roleRepository;




    @Override
    public void setRating(Long id, Integer rate) throws NotFoundException {

        AppUser user = appUserRepository.findById(id).orElseThrow(() -> new NotFoundException("AppUser"));
        Rating rating = new Rating();
        rating.setRating(rate);
        rating.setOwner(user);
        log.info("In setRating OwnerService - user: '{}' get rate '{}'", user.getEmail(), rate);
        ratingRepository.save(rating);
    }

    @Override
    public ResponseEntity<MessageResponse> getRating(Long id) throws NotFoundException {

        AppUser user = appUserRepository.findById(id).orElseThrow(() -> new NotFoundException("AppUser"));
        List<Rating> rates = ratingRepository.findAllByOwner(user);
        AtomicReference<Double> suma = new AtomicReference<>(0.0);
        rates.stream().map(Rating::getRating).forEach(rate -> suma.updateAndGet(v -> v + rate));
        Double res = suma.get() / rates.stream().toList().size();
        log.info("In getRating OwnerService - user: '{}' have '{}' avg rate", user.getEmail(), res);
        return ResponseEntity.ok(new MessageResponse("Avg rate is " + res));
    }

    @Override
    public void addFeedback(Long idComentator, String text, Long id) throws NotFoundException {
        AppUser commentator = appUserRepository.findById(idComentator).orElseThrow(() -> new NotFoundException("Comentator"));
        AppUser user = appUserRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));
        Feedback feedback = new Feedback();
        feedback.setComentator(commentator);
        feedback.setUser(user);
        feedback.setComment(text);
        log.info("In addFeedback OwnerService - '{}' left feedback about '{}'", commentator.getEmail(), user.getEmail());
        feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getFeedbacks(Long id) throws NotFoundException {
        AppUser user = appUserRepository.findById(id).orElseThrow(() -> new NotFoundException("AppUser"));
        List<Feedback> feedbacks = feedbackRepository.findAllByUser(user);
        log.info("In getFeedbacks OwnerService - '{}' have '{}' feedbacks", user.getEmail(), feedbacks.size());
        return feedbacks;
    }

    @Override
    public void addMoney(Long id, Integer money) throws NotFoundException {
        AppUser user = appUserRepository.findById(id).orElseThrow(() -> new NotFoundException("AppUser"));
        if (user.getMoney() == null) {
            user.setMoney(money);
            log.info("In addMoney OwnerService - user: '{}' added his first '{}'$ in system", user.getEmail(), money);
        } else {
            user.setMoney(user.getMoney() + money);
            log.info("In addMoney OwnerService - user: '{}' add '{}'$. His balance is '{}'", user.getEmail(), money, user.getMoney());
        }
        appUserRepository.save(user);
    }

    @Override
    public void addCity(Long id, String city) throws NotFoundException {
        AppUser user = appUserRepository.findById(id).orElseThrow(() -> new NotFoundException("AppUser"));
        user.setCity(city);
        log.info("In addCity OwnerService - user: '{}' add '{}' city to his profile", user.getEmail(), city);
        appUserRepository.save(user);
    }

    @Override
    public void addContactNumber(Long id, String contactNumber) throws NotFoundException, InvalidContactNumberException {

        AppUser user = appUserRepository.findById(id).orElseThrow(() -> new NotFoundException("AppUser"));
        if (ContactNumberValidator.validatePhoneNumber(contactNumber)) {
            user.setContactNumber(contactNumber);
            log.info("In addContactNumber OwnerService - user: '{}' add '{}' contactNumber to his profile", user.getEmail(), contactNumber);
            appUserRepository.save(user);
        } else throw new InvalidContactNumberException(user.getName() + "'s contact number");
    }

    @Override
    public void editVehicle(Long idVehicle, VehicleChangeRequest vehicleChangeRequest) throws NotFoundException, UnreachebleTypeException {
        Vehicle vehicle = vehicleRepository.findById(idVehicle).orElseThrow(() -> new NotFoundException("Vehicle"));
        log.info("In editVehicle OwnerService - '{}. {} {}' update:", vehicle.getId(), vehicle.getBrand(), vehicle.getModel());
        changeVehicleProfile(vehicle, vehicleChangeRequest);
        vehicleRepository.save(vehicle);
    }







    private void changeVehicleProfile(Vehicle vehicle, VehicleChangeRequest vehicleChangeRequest) throws UnreachebleTypeException{
        if (vehicleChangeRequest.getColor() != null) {
            vehicle.setColor(Color.checkColor(vehicleChangeRequest.getColor()));
            log.info("Color");
        }
        if (vehicleChangeRequest.getEnginePower() != null) {
            vehicle.setEnginePower(vehicleChangeRequest.getEnginePower());
            log.info("EnginePower");
        }
        if (vehicleChangeRequest.getMileAge() != null) {
            vehicle.setMileAge(vehicleChangeRequest.getMileAge());
            log.info("MileAge");
        }
        if (vehicleChangeRequest.getPrice() != null) {
            vehicle.setPrice(vehicleChangeRequest.getPrice());
            log.info("Price");
        }
    }
}

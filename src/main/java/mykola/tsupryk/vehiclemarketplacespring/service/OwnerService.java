package mykola.tsupryk.vehiclemarketplacespring.service;

import mykola.tsupryk.vehiclemarketplacespring.dto.request.SignupRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleChangeRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.response.MessageResponse;
import mykola.tsupryk.vehiclemarketplacespring.entity.Feedback;
import mykola.tsupryk.vehiclemarketplacespring.exception.InvalidContactNumberException;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OwnerService {


    void setRating(Long id, Integer rate) throws NotFoundException;
    ResponseEntity<MessageResponse> getRating(Long id) throws NotFoundException;
    void addFeedback (Long idComentator, String text, Long id) throws NotFoundException;
    List<Feedback> getFeedbacks(Long id) throws NotFoundException;
    void addMoney(Long id, Integer money) throws NotFoundException;
    void addCity (Long id, String city) throws NotFoundException;
    void addContactNumber (Long id, String contactNumber) throws NotFoundException, InvalidContactNumberException;
    void editVehicle(Long idVehicle, VehicleChangeRequest vehicleChangeRequest) throws NotFoundException, UnreachebleTypeException;

}

package mykola.tsupryk.vehiclemarketplacespring.service;

import mykola.tsupryk.vehiclemarketplacespring.dto.request.OwnerRegistryRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Feedback;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;

import java.util.List;

public interface OwnerService {

    void registryPerson(OwnerRegistryRequest ownerRegistryRequest);
    void registryCompany(OwnerRegistryRequest ownerRegistryRequest);
    void setRating(Long id, Integer rate) throws NotFoundException;
    Double getRating(Long id) throws NotFoundException;
    void addFeedback (Long idComentator, String text, Long id) throws NotFoundException;
    List<Feedback> getFeedbacks(Long id) throws NotFoundException;
    void addMoney(Long id, Integer money) throws NotFoundException;

}

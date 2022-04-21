package mykola.tsupryk.vehiclemarketplacespring.service;

import mykola.tsupryk.vehiclemarketplacespring.dto.request.OwnerRegistryRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Feedback;

import java.util.List;

public interface OwnerService {

    void registry(OwnerRegistryRequest ownerRegistryRequest);
    void setRating(Long id, Integer rate);
    Double getRating(Long id);
    List<Feedback> getFeedbacks(Long id);

}

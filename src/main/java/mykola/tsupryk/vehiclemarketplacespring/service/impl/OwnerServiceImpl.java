package mykola.tsupryk.vehiclemarketplacespring.service.impl;

import mykola.tsupryk.vehiclemarketplacespring.dto.request.OwnerRegistryRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Feedback;
import mykola.tsupryk.vehiclemarketplacespring.entity.Owner;
import mykola.tsupryk.vehiclemarketplacespring.entity.Rating;
import mykola.tsupryk.vehiclemarketplacespring.exception.NotFoundException;
import mykola.tsupryk.vehiclemarketplacespring.repository.FeedbackRepository;
import mykola.tsupryk.vehiclemarketplacespring.repository.OwnerRepository;
import mykola.tsupryk.vehiclemarketplacespring.repository.RatingRepository;
import mykola.tsupryk.vehiclemarketplacespring.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;


    @Override
    public void registry(OwnerRegistryRequest ownerRegistryRequest) {
        Owner owner = new Owner();
        owner.setName(ownerRegistryRequest.getName());
        owner.setEmail(ownerRegistryRequest.getEmail());
        owner.setPassword(ownerRegistryRequest.getPassword());
        ownerRepository.save(owner);
    }

    @Override
    public void setRating(Long id, Integer rate) {
        Owner owner = ownerRepository.findById(id)
                                     .orElseThrow(() -> new NotFoundException("Owner"));
        Rating rating = new Rating();
        rating.setRating(rate);
        rating.setOwner(owner);
        ratingRepository.save(rating);
    }

    @Override
    public Double getRating(Long id) {
        Owner owner = ownerRepository.findById(id)
                                     .orElseThrow(() -> new NotFoundException("Owner"));
        List<Rating> rates = ratingRepository.findAllByOwner(owner);
        AtomicReference<Double> suma = new AtomicReference<>(0.0);
        rates.stream().map(Rating::getRating).forEach(rate -> suma.updateAndGet(v -> v + rate));
        Double res = suma.get() / rates.stream().toList().size();

        return res;
    }

    @Override
    public void addFeedback(Long idComentator, String text, Long id) {
        Owner comentator = ownerRepository.findById(idComentator).orElseThrow(() -> new NotFoundException("Comentator"));
        Owner user = ownerRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));
        Feedback feedback = new Feedback();
        feedback.setComentator(comentator);
        feedback.setUser(user);
        feedback.setComment(text);

        feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getFeedbacks(Long id) {
        Owner owner = ownerRepository.findById(id).orElseThrow(() -> new NotFoundException("Owner"));
        return feedbackRepository.findAllByUser(owner);
    }
}

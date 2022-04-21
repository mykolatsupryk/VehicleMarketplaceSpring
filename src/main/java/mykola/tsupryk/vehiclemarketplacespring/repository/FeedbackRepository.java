package mykola.tsupryk.vehiclemarketplacespring.repository;

import mykola.tsupryk.vehiclemarketplacespring.entity.Feedback;
import mykola.tsupryk.vehiclemarketplacespring.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {


    List<Feedback> findAllByOwner(Owner owner);
}

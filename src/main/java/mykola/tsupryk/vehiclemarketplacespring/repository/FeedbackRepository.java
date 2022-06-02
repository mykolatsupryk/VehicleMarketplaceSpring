package mykola.tsupryk.vehiclemarketplacespring.repository;

import mykola.tsupryk.vehiclemarketplacespring.entity.AppUser;
import mykola.tsupryk.vehiclemarketplacespring.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {


    List<Feedback> findAllByComentator(AppUser user);
    List<Feedback> findAllByUser(AppUser user);
}

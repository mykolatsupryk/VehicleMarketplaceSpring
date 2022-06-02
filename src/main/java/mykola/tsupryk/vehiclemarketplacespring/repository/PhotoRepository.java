package mykola.tsupryk.vehiclemarketplacespring.repository;


import mykola.tsupryk.vehiclemarketplacespring.entity.Photo;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long>{


    List<Photo> findAllByVehicle(Vehicle vehicle);

    Photo findByVehicle(Vehicle vehicle);
}

package mykola.tsupryk.vehiclemarketplacespring.repository;

import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {


    Page<Vehicle> findAllByIsConfirm(Boolean bool, Pageable pageable);
    List<Vehicle> findAllByIsConfirm(Boolean bool);






}

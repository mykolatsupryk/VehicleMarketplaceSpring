package mykola.tsupryk.vehiclemarketplacespring.repository;


import mykola.tsupryk.vehiclemarketplacespring.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    Model findByNameIgnoreCase (String model);


}

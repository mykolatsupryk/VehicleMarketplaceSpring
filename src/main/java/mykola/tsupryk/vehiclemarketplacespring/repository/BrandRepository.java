package mykola.tsupryk.vehiclemarketplacespring.repository;


import mykola.tsupryk.vehiclemarketplacespring.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByNameIgnoreCase (String brand);




}

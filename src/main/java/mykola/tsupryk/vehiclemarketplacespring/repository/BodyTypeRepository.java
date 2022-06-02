package mykola.tsupryk.vehiclemarketplacespring.repository;


import mykola.tsupryk.vehiclemarketplacespring.entity.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {

    BodyType findByNameIgnoreCase (String bodyType);





}

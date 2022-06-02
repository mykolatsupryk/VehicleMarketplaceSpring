package mykola.tsupryk.vehiclemarketplacespring.repository;

import mykola.tsupryk.vehiclemarketplacespring.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Override
    Optional<AppUser> findById(Long aLong);

    Optional<AppUser> findByName (String name);
    Optional<AppUser> findByEmail (String email);


    Boolean existsByName (String name);

    Boolean existsByEmail (String email);
}

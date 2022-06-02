package mykola.tsupryk.vehiclemarketplacespring.repository;

import mykola.tsupryk.vehiclemarketplacespring.entity.Role;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.enums.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

     Optional<Role> findByName (AppUserRole role);
}

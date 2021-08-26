package animo.realcom.mahakubera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

}

package animo.realcom.mahakubera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUserName(String userName);
}

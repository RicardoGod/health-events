package backend.thryve.epoch.repository;

import backend.thryve.epoch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

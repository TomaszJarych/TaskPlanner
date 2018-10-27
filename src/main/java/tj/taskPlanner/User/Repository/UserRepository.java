package tj.taskPlanner.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tj.taskPlanner.User.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

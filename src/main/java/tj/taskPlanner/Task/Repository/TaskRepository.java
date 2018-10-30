package tj.taskPlanner.Task.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tj.taskPlanner.Task.domain.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> getAllTaskByOwnerIdOrderByCreatedDesc(Long id);

}

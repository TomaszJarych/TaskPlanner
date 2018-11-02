package tj.taskPlanner.Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tj.taskPlanner.Project.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}

package tj.taskPlanner.Task.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tj.taskPlanner.Commons.Enum.Priority;
import tj.taskPlanner.Commons.Enum.TaskStatus;
import tj.taskPlanner.User.domain.User;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	@Enumerated(EnumType.STRING)
	private Priority priority;

	private LocalDateTime created = LocalDateTime.now();

	private LocalDateTime realization;

	@ManyToOne(fetch = FetchType.EAGER)
	private User owner;

	@Enumerated(EnumType.STRING)
	private TaskStatus status = TaskStatus.NEW;

}

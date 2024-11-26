package org.airtribe.employee_tracking_system.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NonNull
	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "department")
	private List<Employee> employees;

	@OneToMany(mappedBy = "department")
	private List<Project> projects;

	@NonNull
	private Double budget;

	public Department() {
	}

	public Department(Long id, List<Employee> employees, Double budget, String name, List<Project> projects) {
		this.id = id;
		this.employees = employees;
		this.budget = budget;
		this.name = name;
		this.projects = projects;
	}
}


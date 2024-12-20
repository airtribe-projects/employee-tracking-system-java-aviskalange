package org.airtribe.employee_tracking_system.Service;

import java.util.List;

import org.airtribe.employee_tracking_system.Entity.Project;
import org.airtribe.employee_tracking_system.Error.ResourceNotFoundException;
import org.airtribe.employee_tracking_system.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository fProjectRepository;

	public List<Project> getAll() {
		return fProjectRepository.findAll();
	}

	public Project getById(Long id) {
		return fProjectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
	}

	public Project save(Project project) {
		return fProjectRepository.save(project);
	}

	public Project update(Long id, Project project) {
		Project existing = getById(id);
		existing.setName(project.getName());
		existing.setEmployees(project.getEmployees());
		return fProjectRepository.save(existing);
	}

	public void delete(Long id) {
		fProjectRepository.deleteById(id);
	}
}


package org.airtribe.employee_tracking_system.Service;

import java.util.List;

import org.airtribe.employee_tracking_system.Entity.Department;
import org.airtribe.employee_tracking_system.Entity.Project;
import org.airtribe.employee_tracking_system.Error.ResourceNotFoundException;
import org.airtribe.employee_tracking_system.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository fDepartmentRepository;

	public List<Department> getAll() {
		return fDepartmentRepository.findAll();
	}

	public Department getById(Long id) {
		return fDepartmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
	}

	public Department save(Department department) {
		return fDepartmentRepository.save(department);
	}

	public Department update(Long id, Department department) {
		Department existing = getById(id);
		existing.setName(department.getName());
		return fDepartmentRepository.save(existing);
	}

	public void delete(Long id) {
		fDepartmentRepository.deleteById(id);
	}

	public List<Project> getProjectsByDepartment(Long departmentId) {
		Department department = getById(departmentId);
		return department.getProjects();
	}
}

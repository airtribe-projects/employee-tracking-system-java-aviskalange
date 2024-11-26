package org.airtribe.employee_tracking_system.Service;

import java.util.ArrayList;
import java.util.List;

import org.airtribe.employee_tracking_system.Entity.Employee;
import org.airtribe.employee_tracking_system.Error.ResourceNotFoundException;
import org.airtribe.employee_tracking_system.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository fEmployeeRepository;

	public List<Employee> getAll() {
		return fEmployeeRepository.findAll();
	}

	public Employee getById(Long id) {
		return fEmployeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
	}

	public Employee save(Employee employee) {
		return fEmployeeRepository.save(employee);
	}

	public Employee update(Long id, Employee employee) {
		Employee existing = getById(id);
		existing.setFirstName(employee.getFirstName());
		existing.setLastName(employee.getLastName());
		existing.setEmail(employee.getEmail());
		return fEmployeeRepository.save(existing);
	}

	public void delete(Long id) {
		fEmployeeRepository.deleteById(id);
	}

	public List<Employee> search(String firstName, String lastName, String email, Long departmentId) {
		return fEmployeeRepository.findAll((root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			// Search by first name
			if (firstName != null) {
				predicates.add(cb.like(root.get("firstName"), "%" + firstName + "%"));
			}

			// Search by last name
			if (lastName != null) {
				predicates.add(cb.like(root.get("lastName"), "%" + lastName + "%"));
			}

			// Search by email
			if (email != null) {
				predicates.add(cb.equal(root.get("email"), email));
			}

			// Search by department ID
			if (departmentId != null) {
				predicates.add(cb.equal(root.get("department").get("id"), departmentId));
			}

			// Combine all predicates with 'AND' logic
			return cb.and(predicates.toArray(new Predicate[0]));
		});
	}

}


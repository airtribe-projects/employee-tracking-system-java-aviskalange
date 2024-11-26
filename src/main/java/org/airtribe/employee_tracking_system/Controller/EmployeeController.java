package org.airtribe.employee_tracking_system.Controller;

import java.util.List;

import org.airtribe.employee_tracking_system.Entity.Employee;
import org.airtribe.employee_tracking_system.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService fEmployeeService;

	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	@Cacheable(value = "employeesCache")
	@GetMapping
	public List<Employee> getAllEmployees() {
		return fEmployeeService.getAll();
	}

	@PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and #id == principal.departmentId) or #id == principal.id")
	@Cacheable(value = "employeeCache", key = "#id")
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable Long id) {
		return fEmployeeService.getById(id);
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	@Cacheable(value = "employeeSearchCache", key = "{#firstName, #lastName, #email, #departmentId}")
	@GetMapping("/search")
	public List<Employee> searchEmployees(
			@RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) Long departmentId) {
		return fEmployeeService.search(firstName, lastName, email, departmentId);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@CacheEvict(value = "employeesCache", allEntries = true)  // Clear cache when new employee is added
	public Employee addEmployee(@RequestBody Employee employee) {
		return fEmployeeService.save(employee);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	@CacheEvict(value = "employeeCache", key = "#id")  // Clear cache for specific employee
	public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		return fEmployeeService.update(id, employee);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@CacheEvict(value = "employeeCache", key = "#id")  // Clear cache for specific employee
	public void deleteEmployee(@PathVariable Long id) {
		fEmployeeService.delete(id);
	}
}

package com.udacity.jdnd.course3.critter.user;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public EmployeeDTO mapToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId((employee.getId()));
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }
    public Employee mapTOEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId((employeeDTO.getId()));
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
      Employee employee = mapTOEmployee(employeeDTO);
     return mapToEmployeeDTO(employeeRepo.save(employee));
    }

    public EmployeeDTO getEmployee(long employeeId) {
        return mapToEmployeeDTO(employeeRepo.getOne(employeeId));
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
       Employee employee = employeeRepo.getOne(employeeId);
       employee.setDaysAvailable(daysAvailable);
       employeeRepo.save(employee);
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        List<Employee> employees = employeeRepo.getAllByDaysAvailableContains(employeeRequestDTO.getDate().getDayOfWeek());
        List<EmployeeDTO> employeeDTOS = employees.stream().filter(employee -> employee.getSkills().containsAll(employeeRequestDTO.getSkills())).map(this::mapToEmployeeDTO).collect(Collectors.toList());
       return employeeDTOS;
    }
}

package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepo;
import com.udacity.jdnd.course3.critter.user.CustomerRepo;
import com.udacity.jdnd.course3.critter.user.EmployeeRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;
    private final EmployeeRepo employeeRepo;

    private final CustomerRepo customerRepo;

    private final PetRepo petRepo;

    public ScheduleService(ScheduleRepo scheduleRepo, EmployeeRepo employeeRepo, CustomerRepo customerRepo, PetRepo petRepo) {
        this.scheduleRepo = scheduleRepo;
        this.employeeRepo = employeeRepo;
        this.customerRepo = customerRepo;
        this.petRepo = petRepo;
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = getSchedule(scheduleDTO);
        return getScheduleDTO(scheduleRepo.save(schedule));
    }

    public List<ScheduleDTO> getAll() {
      return scheduleRepo.findAll().stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForPet(long petId) {
        List<Schedule> schedules =  scheduleRepo.getAllByPetsContains(petRepo.getOne(petId));
        return schedules.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        List<Schedule> schedules =  scheduleRepo.getAllByEmployeesContains(employeeRepo.getOne(employeeId));
        return schedules.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
       List<Schedule> schedules = scheduleRepo.getAllByPetsIn(petRepo.findAllByCustomerId(customerId));
//        List<Schedule> schedules =  scheduleRepo.getAllByCustomersContains(customerRepo.getOne(customerId));
        return schedules.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    private Schedule getSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        schedule.setId(scheduleDTO.getId());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setPets(scheduleDTO.getPetIds().stream().map(id -> petRepo.findById(id).get()).collect(Collectors.toList()));
        schedule.setEmployees(scheduleDTO.getEmployeeIds().stream().map(id -> employeeRepo.findById(id).get()).collect(Collectors.toList()));
        schedule.setActivities(scheduleDTO.getActivities());
        return schedule;
    }
    private ScheduleDTO getScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setPetIds(schedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList()));
        return scheduleDTO;
    }
}

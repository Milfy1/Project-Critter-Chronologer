package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule,Long> {

    List<Schedule> getAllByPetsContains(Pet pet);
    List<Schedule> getAllByEmployeesContains(Employee employee);
//    List<Schedule> getAllByCustomersContains(Customer customer);

    List<Schedule> getAllByPetsIn(List<Pet> pets);
}

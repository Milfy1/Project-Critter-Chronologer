package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private PetRepo petRepo;



    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
    Customer customer = CustomerDTOtoCustomer(customerDTO);
//    List<Pet> pets = petRepo.findAllByCustomerId(customer.getId());
//    customer.setPets(pets);
    customerRepo.save(customer);
    return CustomertoCustomerDTO(customer);
    }

    private Customer CustomerDTOtoCustomer(CustomerDTO customerDTO) {
        List<Long> petIds = customerDTO.getPetIds();
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        if (petIds != null && !petIds.isEmpty()) {
            customer.setPets(petIds.stream().map(id -> petRepo.findById(id).get()).collect(Collectors.toList()));
        }else{
            customer.setPets(new ArrayList<>());
        }
        return customer;
    }
    private CustomerDTO CustomertoCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setPetIds(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        return customerDTO;
    }

    public List<CustomerDTO> getAll() {
      return customerRepo.findAll().stream().map(this::CustomertoCustomerDTO).collect(Collectors.toList());
    }

    public CustomerDTO getOwnerByPet(long petId) {
        Pet pet = petRepo.getOne(petId);
       Customer customer = pet.getCustomer();
       return CustomertoCustomerDTO(customer);
    }

}

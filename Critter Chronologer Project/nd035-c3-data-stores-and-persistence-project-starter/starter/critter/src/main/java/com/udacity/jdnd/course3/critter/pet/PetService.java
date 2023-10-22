package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepo;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {

    private final PetRepo petRepo;
    private final CustomerRepo customerRepo;




    public PetService(PetRepo petRepo, CustomerRepo customerRepo, CustomerService customerService) {
        this.petRepo = petRepo;
        this.customerRepo = customerRepo;
    }
//the 2 bellow functions are for mapping from and to DTO
    private Pet getPetMapper(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setNotes(petDTO.getNotes());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setType(petDTO.getType());
        pet.setCustomer(customerRepo.getOne(petDTO.getOwnerId()));
        return pet;
    }
    private PetDTO getPetDTOMapper(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setNotes(pet.getNotes());
        petDTO.setName(pet.getName());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setType(pet.getType());
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }


    public PetDTO savePet(PetDTO petDTO){
      Pet pet = getPetMapper(petDTO);
      System.out.println(pet);
      Customer customer = customerRepo.getOne(petDTO.getOwnerId());
      pet.setCustomer(customer);
      pet = petRepo.save(pet);
      customer.addPet(pet);
      customerRepo.save(customer);
       return getPetDTOMapper(pet);
    }

    public PetDTO getPet(long petId) {
        Pet pet = petRepo.getOne(petId);
        return getPetDTOMapper(pet);
    }

    public List<PetDTO> getAll() {
       List<PetDTO> pets = petRepo.findAll().stream().map(pet -> getPetDTOMapper(pet)).collect(Collectors.toList());
        return pets;
    }

    public List<PetDTO> getPetsByOwner(long ownerId) {
        List<PetDTO> pets = petRepo.findAllByCustomerId(ownerId).stream().map(pet -> getPetDTOMapper(pet)).collect(Collectors.toList());
       return pets;
    }
}

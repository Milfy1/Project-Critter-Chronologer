package com.udacity.jdnd.course3.critter.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepo extends JpaRepository<Pet,Long> {

    //no ownerID in pet only in petDTO
//    List<Pet> findAllByOwnerId();

    List<Pet> findAllByCustomerId(long ownerId);
}

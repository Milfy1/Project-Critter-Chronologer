package com.udacity.jdnd.course3.critter.pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private PetType type;

    private String name;

    @ManyToOne(targetEntity = Customer.class)
    private Customer customer;

    private LocalDate birthDate;

    private String notes;

}

package com.udacity.jdnd.course3.critter.user;


import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ElementCollection
    private Set<EmployeeSkill> skills;
    @ElementCollection
    private Set<DayOfWeek> daysAvailable;
}

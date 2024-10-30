package tn.esprit.spring.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Instructor;

import java.util.List;


public interface IInstructorRepository extends JpaRepository<Instructor, Long> {
    List<Instructor> findByLastNameContaining(String lastName);


}

package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class InstructorServicesImpl implements IInstructorServices{

    private IInstructorRepository instructorRepository;
    private ICourseRepository courseRepository;
    @Override
    public Instructor addInstructor(Instructor instructor) {
        Instructor savedInstructor = instructorRepository.save(instructor);
        return savedInstructor;
    }

    @Override
    public List<Instructor> retrieveAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        Instructor updatedInstructor = instructorRepository.save(instructor);

        return updatedInstructor;
    }
    @Override
    public Instructor retrieveInstructor(Long numInstructor) {
        return instructorRepository.findById(numInstructor).orElse(null);
    }

    @Override
    public Instructor addInstructorAndAssignToCourse(Instructor instructor, Long numCourse) {
        Course course = courseRepository.findById(numCourse).orElse(null);
        Set<Course> courseSet = new HashSet<>();
        courseSet.add(course);
        instructor.setCourses(courseSet);

        Instructor savedInstructor = instructorRepository.save(instructor);
        return savedInstructor;
    }
    public List<Instructor> retrieveInstructorsWithPagination(int page, int size) {
        Page<Instructor> pagedInstructors = instructorRepository.findAll(PageRequest.of(page, size));
        return pagedInstructors.getContent();
    }
    public List<Instructor> searchInstructorsByLastName(String lastName) {
        return instructorRepository.findByLastNameContaining(lastName);
    }


}

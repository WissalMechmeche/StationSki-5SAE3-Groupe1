package tn.esprit.spring.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDTO {
    Long numCourse;
    int level;
    String typeCourse;
    String support;
    Float price;
    int timeSlot;
    String description;
    String location;
}

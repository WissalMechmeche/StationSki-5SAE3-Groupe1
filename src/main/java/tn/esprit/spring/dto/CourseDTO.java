package tn.esprit.spring.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

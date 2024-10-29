package tn.esprit.spring.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDTO {
    Long numCourse;

    @NotNull
    @Min(1)
    @Max(10)
    int level;

    @NotNull
    String typeCourse;

    @NotNull
    String support;

    @NotNull
    @Min(0)
    Float price;

    @Min(1)
    int timeSlot;

    @NotBlank
    String description;

    @NotBlank
    String location;
}

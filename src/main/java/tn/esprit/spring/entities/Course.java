package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
@Builder
public class Course implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long numCourse;
	int level;
	@Enumerated(EnumType.STRING)
	TypeCourse typeCourse;
	@Enumerated(EnumType.STRING)
	Support support;
	Float price;
	int timeSlot;

	// New attributs
	String description;
	String location;

	@JsonIgnore
	@OneToMany(mappedBy= "course")
	Set<Registration> registrations;

}

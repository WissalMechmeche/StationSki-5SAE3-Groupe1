package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.*;

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

	@NotNull
	@Min(1)
	@Max(10)
	int level;

	@Enumerated(EnumType.STRING)
	TypeCourse typeCourse;

	@Enumerated(EnumType.STRING)
	Support support;

	@NotNull
	@Min(0)
	Float price;

	@Min(1)
	int timeSlot;


	@NotBlank
	String description;

	@NotBlank
	String location;


	@JsonIgnore
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	private Set<Registration> registrations;




}

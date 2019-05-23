package pl.tim.medicalclinic.patient;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.tim.medicalclinic.visit.Visit;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PATIENT")
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String lastName;
    @Email
    String mail;
    String pesel;

    String street;
    String town;
    String code;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    public List<Visit> visits;

}

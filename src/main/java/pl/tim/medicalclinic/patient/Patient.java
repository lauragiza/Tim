package pl.tim.medicalclinic.patient;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.tim.medicalclinic.visit.Visit;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PATIENT")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    String name;
    @NonNull
    String surname;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    public List<Visit> visits;

}

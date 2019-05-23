package pl.tim.medicalclinic.doctor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.tim.medicalclinic.vacation.Vacation;
import pl.tim.medicalclinic.visit.Visit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Setter
@Entity
@Table(name = "DOCTOR")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;

    private String specialization;

    private String phone;

    private String startWorkingTime;

    private String endWorkingTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    public List<Visit> visits;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    public List<Vacation> vacations;

}

package pl.tim.medicalclinic.doctor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.tim.medicalclinic.vacation.Vacation;
import pl.tim.medicalclinic.visit.Visit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Data
@Entity
@Table(name = "DOCTOR")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
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

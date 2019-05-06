package pl.tim.medicalclinic.office;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "OFFICE")
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int number;
    String name;
}

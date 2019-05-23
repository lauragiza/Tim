package pl.tim.medicalclinic.office;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OFFICE")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int number;
    String name;

}

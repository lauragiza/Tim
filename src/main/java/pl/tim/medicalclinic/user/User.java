package pl.tim.medicalclinic.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;

    private String surname;

    private String password;

    @Email
    private String email;


    public User(String surname, String password) {
        this.surname = surname;
        this.password = password;
    }
}

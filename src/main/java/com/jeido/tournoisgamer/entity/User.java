package com.jeido.tournoisgamer.entity;

import com.jeido.tournoisgamer.utils.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    @NotBlank(message = "Le champ ne doit pas être vide !")
    @Column(name = "name")
    private String name;
    @NotBlank(message = "Le champ ne doit pas être vide !")
    @Column(name = "passworde")
    private String password;
    @Column(name = "image")
    private String imgPath;
    @NotBlank(message = "Le champ ne doit pas être vide !")
    @Pattern(regexp = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$", message = "Format de l'email invalide !")
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;
    @ManyToMany
    private List<Tournament> subscribesTournament;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Result> attendedTournaments;
    @Column(name = "roles")
    private Role role;


    public double getRatio() {
        int wins = getWin();
        int defeats = getDefeat();
        return (defeats == 0) ? wins : (double) wins / defeats;
    }


    public int getWin() {
        return 0;
    }


    public int getDefeat() {
        return 0;
    }
}


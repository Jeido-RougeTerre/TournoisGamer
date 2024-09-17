package com.jeido.tournoisgamer.entity;

import com.jeido.tournoisgamer.utils.Role;
import jakarta.persistence.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    // --- ID ---
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    // --- VARIABLES ---
    @NotBlank(message = "This field can't be empty !")
    @Size(min=3, max=15)
    private String name;
    @NotBlank(message = "This field can't be empty !")
    @Size(min=8)
    private String password;
    @Column(name = "image")
    @NotBlank(message = "This field can't be empty !")
    private String imgPath;
    @Email
    private String email;

    // --- LISTS ---
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;
    @ManyToMany
    @JoinTable(name = "player_tournament",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "tournament_id"))
    private List<Tournament> subscribedTournament;
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

    public Object getUsername() {
        return null;
    }
}


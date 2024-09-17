package com.jeido.tournoisgamer.entity;

import com.jeido.tournoisgamer.utils.Format;
import com.jeido.tournoisgamer.utils.TournamentStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="tournaments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tournament {
    // --- ID ---
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tournament_id")
    private UUID id;

    // --- VARIABLES ---
    @NotBlank(message = "This field can't be empty !")
    private String name;
    @NotBlank(message = "This field can't be empty !")
    private LocalDateTime date;
    @NotBlank(message = "This field can't be empty !")
    private String game;
    @NotBlank(message = "This field can't be empty !")
    private int playerLimit;
    @NotBlank(message = "This field can't be empty !")
    private String rules;

    // --- ENUM ---
    private Format format;
    private TournamentStatus status;

    // --- LISTS ---
    @ManyToMany(mappedBy = "subscribedTournament")
    private List<User> inCompetitionPlayers;

    @OneToMany(mappedBy = "tournament")
    private List<Match> results;

    @ManyToMany(mappedBy = "subscribedTournament")
    private List<User> players;
}

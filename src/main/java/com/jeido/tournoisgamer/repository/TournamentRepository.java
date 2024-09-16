package com.jeido.tournoisgamer.repository;

import com.jeido.tournoisgamer.entity.Tournament;
import com.jeido.tournoisgamer.utils.TournamentStatus;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TournamentRepository extends JpaRepository<Tournament, UUID> {
    List<Tournament> findByName(String name);
    List<Tournament> findByStatus(TournamentStatus status);
    List<Tournament> findByDate(@NotBlank(message = "Le champ ne doit pas être vide !") Date date);
    List<Tournament> findByDateBefore(Date date);
    List<Tournament> findByDateAfter(Date date);
    List<Tournament> findByDateAndName(Date date, String name);

}

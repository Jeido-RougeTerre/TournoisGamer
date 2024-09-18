package com.jeido.tournoisgamer.service;
import ch.qos.logback.core.status.Status;
import com.jeido.tournoisgamer.entity.Tournament;
import com.jeido.tournoisgamer.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    public List<Tournament> findAllTournaments() {
        return tournamentRepository.findAll();
    }

    public List<Tournament> findTournamentsByName(String name) {
        return tournamentRepository.findByName(name);
    }

    public List<Tournament> findTournamentsByStatus(Status status) {
        return tournamentRepository.findByName(String.valueOf(status));
    }

    public List<Tournament> findTournamentsByDate(LocalDateTime date) {
        return tournamentRepository.findByDate(date);
    }

    public List<Tournament> findTournamentsByDateAndByName(LocalDateTime date, String name) {
        return tournamentRepository.findByDateAndName(date, name);
    }

    public Optional<Tournament> findTournamentById(UUID id) {
        return tournamentRepository.findById(id);
    }

    public List<Tournament> findTournamentBeforeDate(LocalDateTime date) {
        return tournamentRepository.findByDateBefore(date);
    }

    public List<Tournament> findTournamentAfterDate(LocalDateTime date) {
        return tournamentRepository.findByDateAfter(date);
    }

    public Tournament create(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    public Tournament update(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    public void delete(Tournament tournament) {
        tournamentRepository.delete(tournament);
    }
}
package com.jeido.tournoisgamer.controller;

import com.jeido.tournoisgamer.entity.Tournament;
import com.jeido.tournoisgamer.service.TournamentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("/tournaments")
    public String getAllTournaments(@RequestParam(value = "date", required = false)Date date, @RequestParam(value = "name", required = false)String name, Model model) {
        List<Tournament> tournaments;
        if (date != null && name != null) {
            tournaments = tournamentService.findTournamentsByDateAndByName(date, name);
        } else if (date != null) {
            tournaments = tournamentService.findTournamentsByDate(date);
        } else if (name != null) {
            tournaments = tournamentService.findTournamentsByName(name);
        } else {
            tournaments = tournamentService.findAllTournaments();
        }
        model.addAttribute("tournaments",tournaments);
        return "tournaments/listTournaments";
    }

    @GetMapping("/tournaments/{id}")
    public String getTournamentDetails(@PathVariable("id") UUID id, Model model) {
        Tournament tournament = tournamentService.findTournamentById(id).orElse(null);
        //error 400
        model.addAttribute("tournament",tournament);
        return "tournaments/detailTournaments";
    }

    @PostMapping("/tournaments/add")
    public String addTournament(@Valid @ModelAttribute("tournament")Tournament tournament, BindingResult bindingResult, Model model) {
     return " ";
    }

    @PostMapping("/tournaments/subscription/{id}")
    public String subscribeToTournament(@PathVariable("id") UUID id, Model model) {
        return " ";
    }

}

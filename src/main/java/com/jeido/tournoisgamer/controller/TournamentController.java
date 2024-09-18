package com.jeido.tournoisgamer.controller;

import com.jeido.tournoisgamer.entity.Tournament;
import com.jeido.tournoisgamer.entity.User;
import com.jeido.tournoisgamer.service.AuthService;
import com.jeido.tournoisgamer.service.TournamentService;
import com.jeido.tournoisgamer.service.UserService;
import com.jeido.tournoisgamer.utils.Format;
import com.jeido.tournoisgamer.utils.Role;
import com.jeido.tournoisgamer.utils.TournamentStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class TournamentController {


    private final TournamentService tournamentService;

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public TournamentController(TournamentService tournamentService, UserService userService, AuthService authService) {
        this.tournamentService = tournamentService;
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/tournaments")
    public String getAllTournaments(@RequestParam(value = "date", required = false) LocalDateTime date, @RequestParam(value = "name", required = false) String name, Model model) {
        if (authService.isLogged()) {

            User user = authService.getUser();
            if (user != null && user.getRole() == Role.ADMIN) {
                model.addAttribute("isAdmin", true);
            }
        } else {
            model.addAttribute("isAdmin", false);
        }

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
        model.addAttribute("tournaments", tournaments);
        return "tournaments/listTournaments";
    }

    @GetMapping("/tournaments/{id}")
    public String getTournamentDetails(@PathVariable("id") UUID id, Model model) {
        Tournament tournament = tournamentService.findTournamentById(id).orElse(null);
        //error 400
        model.addAttribute("tournament", tournament);
        return "tournaments/detailTournaments";
    }

    @GetMapping("/tournaments/add")
    public String addTournament(Model model) {
        model.addAttribute("tournament", Tournament.builder().game("none").format(Format.TOURNAMENT).date(LocalDateTime.now()).playerLimit(2).build());
        model.addAttribute("options", Format.values());
        return "tournaments/addTournament";
    }

    @PostMapping("/tournaments/add")
    public String addPostTournament(@Valid @ModelAttribute("tournament") Tournament tournament, BindingResult bindingResult, Model model) {
        if(!authService.isLogged()) return "redirect:/login";
        if(!userService.isAdmin(authService.getUser().getId())) return "redirect:/tournaments";

        if (bindingResult.hasErrors()) {
            return "redirect:/tournaments/add";
        }

        tournament.setStatus(TournamentStatus.NOT_STARTED);

        return "/tournaments/addTournament";
    }

    @PostMapping("/tournaments/update")
    public String updateTournament(@Valid @ModelAttribute("tournament")Tournament tournament, BindingResult bindingResult, Model model) {
        if(!authService.isLogged()) return "redirect:/login";
        if(!userService.isAdmin(authService.getUser().getId())) return "redirect:/tournaments";

        if (bindingResult.hasErrors()) {
            return "redirect:/tournaments/update";
        }

        switch (tournament.getStatus()) {
            case NOT_STARTED -> {}
            case STARTED -> {
                tournament.init();
            }
            case TBA -> {
                tournament.setDate(null);
            }
            case COMPLETED -> {
                tournament.calcWinner();
            }
            case CANCELLED -> {

            }
        }

        return "/tournaments/addTournament";
    }

    @PostMapping("/tournaments/subscription/{id}")
    public String subscribeToTournament(@PathVariable("id") UUID id, Model model) {
        if (authService.isLogged()) {
            User user = authService.getUser();

            if (user == null) {
                model.addAttribute("erreur", "Vous n'êtes pas connecté");
                return "redirect:/login";
            }


            Tournament tournament = tournamentService.findTournamentById(id).orElse(null);

            if (tournament == null) {
                model.addAttribute("erreur", "Tournoi introuvable");
                return "redirect:/tournaments";
            }

            if (tournament.getInCompetitionPlayers().size() >= tournament.getPlayerLimit()) {
                model.addAttribute("erreur", "Tournoi Plein");
                return "redirect:/tournaments/" + id;
            }

            switch (tournament.getStatus()) {
                case TBA:
                    model.addAttribute("erreur", "Ce Tournoi n'est pas encore éligible aux inscriptions");
                    break;
                case CANCELLED:
                    model.addAttribute("erreur", "Tournoi annulé");
                    break;
                case COMPLETED:
                    model.addAttribute("erreur", "Tournoi Terminé");
                    break;
                case STARTED:
                    model.addAttribute("erreur", "Tournoi Déjà commencé");
                    break;
                case NOT_STARTED:
                    tournament.getInCompetitionPlayers().add(user);
                    tournament.getPlayers().add(user);
                    tournamentService.update(tournament);
                    user.getSubscribedTournament().add(tournament);
                    userService.update(user);
                    break;
                default:
                    model.addAttribute("erreur", "incorrect Status");
                    break;
            }

            return "redirect:/tournaments/" + id;

        } else {
            model.addAttribute("erreur", "Vous n'êtes pas connecté");
            return "redirect:/user/registration-form";
        }
    }
}
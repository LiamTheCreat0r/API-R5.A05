package com.r5._5.controller;

import com.r5._5.model.Joueur;
import com.r5._5.model.Participation;
import com.r5._5.model.Rencontre;
import com.r5._5.repository.JoueurRepository;
import com.r5._5.repository.ParticipationRepository;
import com.r5._5.repository.RencontreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participations")
public class ParticipationController {

    private final ParticipationRepository participationRepository;
    private final JoueurRepository joueurRepository;
    private final RencontreRepository rencontreRepository;

    public ParticipationController(ParticipationRepository participationRepository,
            JoueurRepository joueurRepository, RencontreRepository rencontreRepository) {
        this.participationRepository = participationRepository;
        this.joueurRepository = joueurRepository;
        this.rencontreRepository = rencontreRepository;
    }

    // Replace the sent (partial) Joueur with the managed entity, or null if
    // missing/unknown
    private Joueur resolveJoueur(Joueur sent) {
        if (sent == null || sent.getJoueurId() == null) {
            return null;
        }
        return joueurRepository.findById(sent.getJoueurId()).orElse(null);
    }

    private Rencontre resolveRencontre(Rencontre sent) {
        if (sent == null || sent.getRencontreId() == null) {
            return null;
        }
        return rencontreRepository.findById(sent.getRencontreId()).orElse(null);
    }

    @GetMapping
    public List<Participation> getAll() {
        return participationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participation> getById(@PathVariable Integer id) {
        return participationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/joueur/{joueurId}")
    public List<Participation> getByJoueur(@PathVariable Integer joueurId) {
        return participationRepository.findByJoueur_JoueurId(joueurId);
    }

    @GetMapping("/rencontre/{rencontreId}")
    public List<Participation> getByRencontre(@PathVariable Integer rencontreId) {
        return participationRepository.findByRencontre_RencontreId(rencontreId);
    }

    @PostMapping
    public ResponseEntity<Participation> create(@RequestBody Participation participation) {
        Joueur joueur = resolveJoueur(participation.getJoueur());
        if (joueur == null) {
            return ResponseEntity.badRequest().build();
        }

        Rencontre rencontre = null;
        if (participation.getRencontre() != null) {
            rencontre = resolveRencontre(participation.getRencontre());
            if (rencontre == null) {
                return ResponseEntity.badRequest().build();
            }
        }

        participation.setJoueur(joueur);
        participation.setRencontre(rencontre);
        participation.setParticipationId(null); // force an INSERT
        Participation saved = participationRepository.save(participation);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participation> update(@PathVariable Integer id, @RequestBody Participation body) {
        return participationRepository.findById(id)
                .map(existing -> {
                    Joueur joueur = resolveJoueur(body.getJoueur());
                    if (joueur == null) {
                        return ResponseEntity.badRequest().<Participation>build();
                    }

                    Rencontre rencontre = null;
                    if (body.getRencontre() != null) {
                        rencontre = resolveRencontre(body.getRencontre());
                        if (rencontre == null) {
                            return ResponseEntity.badRequest().<Participation>build();
                        }
                    }

                    existing.setJoueur(joueur);
                    existing.setRencontre(rencontre);
                    existing.setTitulaireOuRemplacant(body.getTitulaireOuRemplacant());
                    existing.setPoste(body.getPoste());
                    existing.setNotePerformance(body.getNotePerformance());
                    return ResponseEntity.ok(participationRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Participation> patch(@PathVariable Integer id, @RequestBody Participation body) {
        return participationRepository.findById(id)
                .map(existing -> {
                    if (body.getJoueur() != null) {
                        Joueur joueur = resolveJoueur(body.getJoueur());
                        if (joueur == null) {
                            return ResponseEntity.badRequest().<Participation>build();
                        }
                        existing.setJoueur(joueur);
                    }
                    if (body.getRencontre() != null) {
                        Rencontre rencontre = resolveRencontre(body.getRencontre());
                        if (rencontre == null) {
                            return ResponseEntity.badRequest().<Participation>build();
                        }
                        existing.setRencontre(rencontre);
                    }
                    if (body.getTitulaireOuRemplacant() != null) {
                        existing.setTitulaireOuRemplacant(body.getTitulaireOuRemplacant());
                    }
                    if (body.getPoste() != null) {
                        existing.setPoste(body.getPoste());
                    }
                    if (body.getNotePerformance() != null) {
                        existing.setNotePerformance(body.getNotePerformance());
                    }
                    return ResponseEntity.ok(participationRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/performance")
    public ResponseEntity<Participation> deletePerformance(@PathVariable Integer id) {
        return participationRepository.findById(id)
                .map(existing -> {
                    existing.setNotePerformance(null);
                    return ResponseEntity.ok(participationRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
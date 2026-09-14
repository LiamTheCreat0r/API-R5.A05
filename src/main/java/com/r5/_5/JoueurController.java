package com.r5._5;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/joueurs")
public class JoueurController {

    private final JoueurRepository joueurRepository;

    public JoueurController(JoueurRepository joueurRepository) {
        this.joueurRepository = joueurRepository;
    }

    @GetMapping
    public List<Joueur> getAll() {
        return joueurRepository.findAll();
    }

    @GetMapping("/{id}")
    public Joueur getOne(@PathVariable Integer id) {
        return joueurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Joueur not found: " + id));
    }

    @PostMapping
    public Joueur create(@RequestBody Joueur joueur) {
        return joueurRepository.save(joueur);
    }

    @PutMapping("/{id}")
    public Joueur update(@PathVariable Integer id, @RequestBody Joueur updated) {
        Joueur joueur = joueurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Joueur not found: " + id));
        joueur.setNumeroLicence(updated.getNumeroLicence());
        joueur.setNom(updated.getNom());
        joueur.setPrenom(updated.getPrenom());
        joueur.setDateNaissance(updated.getDateNaissance());
        joueur.setTaille(updated.getTaille());
        joueur.setPoids(updated.getPoids());
        joueur.setStatut(updated.getStatut());
        return joueurRepository.save(joueur);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        joueurRepository.deleteById(id);
    }
}
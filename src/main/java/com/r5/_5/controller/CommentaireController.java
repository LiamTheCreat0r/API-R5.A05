package com.r5._5.controller;

import com.r5._5.model.Commentaire;
import com.r5._5.repository.CommentaireRepository;
import com.r5._5.repository.JoueurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commentaires")
public class CommentaireController {

    private final CommentaireRepository commentaireRepository;
    private final JoueurRepository joueurRepository;

    public CommentaireController(CommentaireRepository commentaireRepository, JoueurRepository joueurRepository) {
        this.commentaireRepository = commentaireRepository;
        this.joueurRepository = joueurRepository;
    }

    @GetMapping
    public List<Commentaire> getAll() {
        return commentaireRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commentaire> getById(@PathVariable Integer id) {
        return commentaireRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/joueur/{joueurId}")
    public List<Commentaire> getByJoueur(@PathVariable Integer joueurId) {
        return commentaireRepository.findByJoueurId(joueurId);
    }

    @PostMapping
    public ResponseEntity<Commentaire> create(@RequestBody Commentaire commentaire) {
        if (!joueurRepository.existsById(commentaire.getJoueurId())) {
            return ResponseEntity.badRequest().build();
        }
        commentaire.setCommentaireId(null);
        commentaire.setDate(java.time.LocalDate.now());
        Commentaire saved = commentaireRepository.save(commentaire);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commentaire> update(@PathVariable Integer id, @RequestBody Commentaire body) {
        return commentaireRepository.findById(id)
                .map(existing -> {
                    existing.setContenu(body.getContenu());
                    existing.setDate(body.getDate());
                    return ResponseEntity.ok(commentaireRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!commentaireRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        commentaireRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
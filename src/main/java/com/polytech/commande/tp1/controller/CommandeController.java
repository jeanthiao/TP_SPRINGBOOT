package com.polytech.commande.tp1.controller;

import com.polytech.commande.tp1.entities.Commande;
import com.polytech.commande.tp1.repositories.CommandeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commandes")
@Tag(name = "Commande", description = "Operations sur les commandes")
public class CommandeController {

    private final CommandeRepository commandeRepository;

    public CommandeController(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @PostMapping
    @Operation(summary = "Creer une nouvelle commande")
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
        commande.setDateCommande(LocalDateTime.now());
        Commande saved = commandeRepository.save(commande);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    @Operation(summary = "Recuperer toutes les commandes")
    public ResponseEntity<List<Commande>> getAllCommandes() {
        return ResponseEntity.ok(commandeRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer une commande par ID")
    public ResponseEntity<Commande> getCommande(@PathVariable Long id) {
        Optional<Commande> commande = commandeRepository.findById(id);
        return commande.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre a jour une commande existante")
    public ResponseEntity<Commande> updateCommande(@PathVariable Long id, @RequestBody Commande updatedCommande) {
        return commandeRepository.findById(id).map(commande -> {
            commande.setClient(updatedCommande.getClient());
            commande.setLignes(updatedCommande.getLignes());
            commandeRepository.save(commande);
            return ResponseEntity.ok(commande);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une commande")
    public ResponseEntity<Object> deleteCommande(@PathVariable Long id) {
        return commandeRepository.findById(id).map(commande -> {
            commandeRepository.delete(commande);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
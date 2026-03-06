package com.polytech.commande.tp1.controller;

import com.polytech.commande.tp1.entities.LigneCommande;
import com.polytech.commande.tp1.repositories.LigneCommandeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lignes-commande")
@Tag(name = "LigneCommande", description = "Operations sur les lignes de commande")
public class LigneCommandeController {

    private final LigneCommandeRepository ligneCommandeRepository;

    public LigneCommandeController(LigneCommandeRepository ligneCommandeRepository) {
        this.ligneCommandeRepository = ligneCommandeRepository;
    }

    @PostMapping
    @Operation(summary = "Creer une nouvelle ligne de commande")
    public ResponseEntity<LigneCommande> createLigne(@RequestBody LigneCommande ligne) {
        LigneCommande saved = ligneCommandeRepository.save(ligne);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    @Operation(summary = "Recuperer toutes les lignes de commande")
    public ResponseEntity<List<LigneCommande>> getAllLignes() {
        return ResponseEntity.ok(ligneCommandeRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer une ligne de commande par ID")
    public ResponseEntity<LigneCommande> getLigne(@PathVariable Long id) {
        Optional<LigneCommande> ligne = ligneCommandeRepository.findById(id);
        return ligne.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre a jour une ligne de commande")
    public ResponseEntity<LigneCommande> updateLigne(@PathVariable Long id, @RequestBody LigneCommande updatedLigne) {
        return ligneCommandeRepository.findById(id).map(ligne -> {
            ligne.setProduit(updatedLigne.getProduit());
            ligne.setQuantite(updatedLigne.getQuantite());
            ligne.setPrixUnitaire(updatedLigne.getPrixUnitaire());
            ligneCommandeRepository.save(ligne);
            return ResponseEntity.ok(ligne);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une ligne de commande")
    public ResponseEntity<Object> deleteLigne(@PathVariable Long id) {
        return ligneCommandeRepository.findById(id).map(ligne -> {
            ligneCommandeRepository.delete(ligne);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
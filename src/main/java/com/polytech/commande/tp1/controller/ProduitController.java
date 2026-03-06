package com.polytech.commande.tp1.controller;

import com.polytech.commande.tp1.entities.Produit;
import com.polytech.commande.tp1.repositories.ProduitRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produits")
@Tag(name = "Produit", description = "Operations sur les produits")
public class ProduitController {

    private final ProduitRepository produitRepository;

    public ProduitController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @PostMapping
    @Operation(summary = "Creer un nouveau produit")
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        Produit saved = produitRepository.save(produit);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    @Operation(summary = "Recuperer tous les produits")
    public ResponseEntity<List<Produit>> getAllProduits() {
        return ResponseEntity.ok(produitRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer un produit par ID")
    public ResponseEntity<Produit> getProduit(@PathVariable Long id) {
        Optional<Produit> produit = produitRepository.findById(id);
        return produit.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre a jour un produit existant")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody Produit updatedProduit) {
        return produitRepository.findById(id).map(produit -> {
            produit.setNom(updatedProduit.getNom());
            produit.setPrix(updatedProduit.getPrix());
            produit.setStock(updatedProduit.getStock());
            produitRepository.save(produit);
            return ResponseEntity.ok(produit);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un produit")
    public ResponseEntity<Object> deleteProduit(@PathVariable Long id) {
        return produitRepository.findById(id).map(produit -> {
            produitRepository.delete(produit);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
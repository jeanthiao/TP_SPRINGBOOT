package com.polytech.commande.tp1.repositories;

import com.polytech.commande.tp1.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Optional<Produit> findById(Long id);
}

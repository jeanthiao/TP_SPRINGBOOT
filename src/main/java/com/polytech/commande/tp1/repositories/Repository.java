package com.polytech.commande.tp1.repositories;

import com.polytech.commande.tp1.entities.Commande;
import com.polytech.commande.tp1.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Repository extends JpaRepository<Commande, Long> {
    Optional<Commande> findById(Long id);
}

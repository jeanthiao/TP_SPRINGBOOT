package com.polytech.commande.tp1.repositories;

import com.polytech.commande.tp1.entities.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
}
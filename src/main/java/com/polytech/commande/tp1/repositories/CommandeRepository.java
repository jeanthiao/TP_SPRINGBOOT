package com.polytech.commande.tp1.repositories;

import com.polytech.commande.tp1.entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
}

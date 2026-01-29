package com.polytech.commande.tp1.repositories;

import com.polytech.commande.tp1.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}

package com.polytech.commande.tp1.init;

import com.polytech.commande.tp1.entities.*;
import com.polytech.commande.tp1.repositories.ClientRepository;
import com.polytech.commande.tp1.repositories.CommandeRepository;
import com.polytech.commande.tp1.repositories.ProduitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("dev") // ✅ Actif uniquement en DEV
public class DataInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;
    private final CommandeRepository commandeRepository;

    public DataInitializer(ClientRepository clientRepository,
                           ProduitRepository produitRepository,
                           CommandeRepository commandeRepository) {
        this.clientRepository = clientRepository;
        this.produitRepository = produitRepository;
        this.commandeRepository = commandeRepository;
    }

    @Override
    public void run(String... args) {

        // 1️⃣ Clients
        Client c1 = new Client(null, "Jean", "jean@mail.com");
        Client c2 = new Client(null, "Awa", "awa@mail.com");
        clientRepository.saveAll(List.of(c1, c2));

        // 2️⃣ Produits
        Produit p1 = new Produit(null, "Ordinateur", BigDecimal.valueOf(750000), 10);
        Produit p2 = new Produit(null, "Souris", BigDecimal.valueOf(5000), 50);
        produitRepository.saveAll(List.of(p1, p2));

        // 3️⃣ Commande
        Commande commande = new Commande();
        commande.setClient(c1);

        LigneCommande lc1 = new LigneCommande();
        lc1.setProduit(p1);
        lc1.setQuantite(1);
        lc1.setPrixUnitaire(p1.getPrix());
        lc1.setCommande(commande);

        LigneCommande lc2 = new LigneCommande();
        lc2.setProduit(p2);
        lc2.setQuantite(2);
        lc2.setPrixUnitaire(p2.getPrix());
        lc2.setCommande(commande);

        commande.setLignes(List.of(lc1, lc2));

        commandeRepository.save(commande);

        System.out.println("✅ Données DEV initialisées avec succès");
    }
}

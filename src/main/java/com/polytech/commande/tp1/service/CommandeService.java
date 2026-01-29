package com.polytech.commande.tp1.service;

import com.polytech.commande.tp1.entities.Commande;
import com.polytech.commande.tp1.entities.LigneCommande;
import com.polytech.commande.tp1.entities.Produit;
import com.polytech.commande.tp1.repositories.ProduitRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public class CommandeService {
    private final JpaRepository<Commande, Long> repository;
    private final ProduitRepository produitRepository;
    public CommandeService(JpaRepository<Commande, Long> repository, ProduitRepository produitRepository) {
        this.repository = repository;
        this.produitRepository = produitRepository;
    }
    public List<Commande> findAll() {
        return repository.findAll();
    }
    public void verifierCommande(LigneCommande lignesCommande) {
        Produit produit=lignesCommande.getProduit();
        int quantite=lignesCommande.getQuantite();
        if(produit.getStock()<quantite){
            throw new RuntimeException("Stock insuffisant !");
        }

    }
    public BigDecimal calculTotalCommande(LigneCommande[] lignesCommande) {

        BigDecimal total = BigDecimal.ZERO;

        for (LigneCommande l : lignesCommande) {
            BigDecimal prixLigne = l.getPrixUnitaire()
                    .multiply(BigDecimal.valueOf(l.getQuantite()));
            total = total.add(prixLigne);
        }

        return total;
    }
    public void miseAJourStock(Commande commande) {

        for (LigneCommande ligne : commande.getLignes()) {

            // 1️⃣ Vérifier la commande (règle métier déjà implémentée)
            verifierCommande(ligne);

            // 2️⃣ Mettre à jour le stock du produit
            Produit produit = ligne.getProduit();
            produit.setStock(produit.getStock() - ligne.getQuantite());

            // 3️⃣ Sauvegarder le produit avec le nouveau stock
            produitRepository.save(produit);
        }
    }

}

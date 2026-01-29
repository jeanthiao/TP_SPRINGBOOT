package com.polytech.commande.tp1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LigneCommande {
    @Id
    @GeneratedValue
    private  Long id;
    //produit:Produit
    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;
    private Integer quantite;
    private BigDecimal prixUnitaire;
    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

}

package com.polytech.commande.tp1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Commande {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime dateCommande;
    private enum status {CREATED, VALIDATED,CANCELLED} ;
    @ManyToOne
    @JoinColumn(name = "client_id")
    Client client;
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<LigneCommande> lignes;
}

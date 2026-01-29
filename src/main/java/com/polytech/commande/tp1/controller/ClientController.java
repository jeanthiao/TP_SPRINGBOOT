package com.polytech.commande.tp1.controller;

import com.polytech.commande.tp1.entities.Client;
import com.polytech.commande.tp1.repositories.ClientRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@Tag(name = "Client", description = "Opérations sur les clients")
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // ✅ CREATE
    @PostMapping
    @Operation(summary = "Créer un nouveau client")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client saved = clientRepository.save(client);
        return ResponseEntity.status(201).body(saved); // 201 Created
    }

    // ✅ READ ALL
    @GetMapping
    @Operation(summary = "Récupérer tous les clients")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientRepository.findAll()); // 200 OK
    }

    // ✅ READ ONE
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un client par ID")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.map(ResponseEntity::ok) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un client existant")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        return clientRepository.findById(id).map(client -> {
            client.setNom(updatedClient.getNom());
            client.setEmail(updatedClient.getEmail());
            clientRepository.save(client);
            return ResponseEntity.ok(client); // 200 OK
        }).orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un client")
    public ResponseEntity<Object> deleteClient(@PathVariable Long id) {
        return clientRepository.findById(id).map(client -> {
            clientRepository.delete(client);
            return ResponseEntity.noContent().build(); // 204 No Content
        }).orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }
}



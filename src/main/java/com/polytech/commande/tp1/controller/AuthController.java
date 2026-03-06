package com.polytech.commande.tp1.controller;

import com.polytech.commande.tp1.dto.AuthRequest;
import com.polytech.commande.tp1.dto.AuthResponse;
import com.polytech.commande.tp1.entities.Utilisateur;
import com.polytech.commande.tp1.repositories.UtilisateurRepository;
import com.polytech.commande.tp1.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentification", description = "Login et Register")
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UtilisateurRepository utilisateurRepository,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    //  REGISTER
    @PostMapping("/register")
    @Operation(summary = "Créer un compte utilisateur")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (utilisateurRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username déjà utilisé");
        }
        Utilisateur user = new Utilisateur();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Utilisateur.Role.USER); // rôle par défaut
        utilisateurRepository.save(user);
        return ResponseEntity.status(201).body("Compte créé avec succès");
    }

    //  LOGIN
    @PostMapping("/login")
    @Operation(summary = "Se connecter et obtenir un token JWT")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        return utilisateurRepository.findByUsername(request.getUsername())
                .filter(u -> passwordEncoder.matches(request.getPassword(), u.getPassword()))
                .map(u -> {
                    String token = jwtUtil.generateToken(u.getUsername(), u.getRole().name());
                    return ResponseEntity.ok(new AuthResponse(token, u.getRole().name()));
                })
                .orElse(ResponseEntity.status(401).build());
    }
}
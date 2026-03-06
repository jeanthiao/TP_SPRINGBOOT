# TP_SPRINGBOOT
## API REST Spring Boot — avec Authentification JWT

---

## Description

Ce projet est une application Spring Boot exposant une API REST permettant de gérer des ressources métier (produits, clients, commandes, lignes de commande).

Elle utilise :
- **Spring Data JPA** + **MySQL** pour la persistance
- **Spring Security** + **JWT** pour l'authentification par token
- **Profils Spring** (`dev`, `test`, `prod`) pour les environnements
- **Swagger / OpenAPI** pour la documentation interactive

---

## Lancement de l'application

### 1. Prérequis

- Java 21 ou plus
- Maven
- MySQL
- Git

### 2. Clonage du dépôt

```bash
git clone https://github.com/jeanthiao/TP_SPRINGBOOT.git
cd TP1
```

### 3. Configuration des variables d'environnement

```bash
export DB_URL=jdbc:mysql://localhost:3306/gestion_commande
export DB_USERNAME=jcet_commande
export DB_PASSWORD=commande1
export SPRING_PROFILES_ACTIVE=dev
```

### 4. Lancement

```bash
mvn spring-boot:run
```

---

## Authentification JWT

L'API est sécurisée par des tokens JWT. Voici comment les utiliser :

### 1. Créer un compte

```http
POST /auth/register
Content-Type: application/json

{
  "username": "jcet",
  "password": "1234"
}
```

### 2. Se connecter et récupérer le token

```http
POST /auth/login
Content-Type: application/json

{
  "username": "jcet",
  "password": "1234"
}
```

Réponse :
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "role": "USER"
}
```

### 3. Utiliser le token dans les requêtes

Ajoute ce header à chaque requête protégée :

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## Droits d'accès

| Route | Accès |
|---|---|
| `POST /auth/register` | Public |
| `POST /auth/login` | Public |
| `GET /swagger-ui/**` | Public |
| `GET /clients`, `GET /commandes` | USER ou ADMIN |
| `POST`, `PUT` sur toutes les routes | USER ou ADMIN |
| `DELETE` sur toutes les routes | ADMIN uniquement |

---

## Documentation Swagger

Une fois l'application lancée, accède à la documentation interactive :

http://localhost:8080/swagger-ui/index.html

Pour tester les endpoints protégés dans Swagger :
1. Connecte-toi via `POST /auth/login`
2. Copie le token reçu
3. Clique sur le bouton **Authorize** en haut de la page
4. Entre : `Bearer <ton_token>`
5. Clique **Authorize** puis teste librement les endpoints

---

## Structure du projet

```
src/main/java/com/polytech/commande/tp1/
├── controller/        # Endpoints REST (Client, Commande, Produit, LigneCommande, Auth)
├── entities/          # Entités JPA (Client, Commande, Produit, LigneCommande, Utilisateur)
├── repositories/      # Interfaces Spring Data JPA
├── service/           # Logique métier
├── security/          # JWT (JwtUtil, JwtAuthFilter, SecurityConfig)
├── dto/               # Objets de transfert (AuthRequest, AuthResponse)
└── init/              # Initialisation des données (profil dev)
```

---

## Profils Spring

| Profil | Base de données | DDL | Usage |
|---|---|---|---|
| `dev` | localhost:3306/gestion_commande | `create` | Développement local |
| `test` | localhost:3306/gestion_commande_test | `create-drop` | Tests automatisés |
| `prod` | prod-server:3306/gestion_commande | `validate` | Production |

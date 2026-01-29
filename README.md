# TP_SPRINGBOOT

# 📦 API REST Spring Boot

## 🧾 Description
Ce projet est une application **Spring Boot** exposant une **API REST** permettant de gérer des ressources métier (produits, clients, commandes, etc.).  
Elle utilise **Spring Data JPA**, une base de données **MySQL**, des **profils Spring (dev, test, prod)** et la documentation **Swagger (OpenAPI)**.

---

Lancement de l’application
 1️⃣ Prérequis
- Java 17 ou plus
- Maven
- MySQL
- Git

---
 2️⃣ Clonage du dépôt
```bash
git clone https://github.com/jeanthiao/tp1.git
cd TP1


export DB_URL=jdbc:mysql://localhost:3306/gestion_commande
export DB_USERNAME=jcet_commande
export DB_PASSWORD=commande1
export SPRING_PROFILES_ACTIVE=dev

Accès à Swagger
Une fois l’application lancée :
Interface Swagger UI
👉 http://localhost:8080/swagger-ui.html


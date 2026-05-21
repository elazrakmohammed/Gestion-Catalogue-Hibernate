# 📦 Application de Gestion de Catalogue Produits

**Projet de fin de module - Ingénierie Logicielle & Persistance des données** *Filière : Big Data et Systèmes d'Information (BDSI)*

---

## 📝 Description du Projet
Cette application de bureau (Desktop) a été conçue pour répondre aux besoins de gestion de la structure « PME Distribution ». Elle permet d'administrer de manière centralisée un catalogue de produits via une architecture logicielle moderne en **N-Couches (MVC Étendu)**. 

L'objectif principal est de démontrer la maîtrise du pont entre le monde orienté objet (Java) et le monde relationnel (MySQL) grâce au mapping objet-relationnel (ORM).

## ✨ Fonctionnalités Principales
* **Opérations CRUD Complètes :** Création, lecture, mise à jour et suppression sécurisée des produits.
* **Persistance Automatisée :** Gestion des transactions et des sessions de base de données sans requêtes SQL brutes.
* **Interface Dynamique (UX/UI) :** Synchronisation en temps réel entre le formulaire de saisie et la table de données (`TableView`).
* **Recherche Prédictive :** Filtrage instantané en mémoire (via `FilteredList`) optimisant les appels au serveur.
* **Sécurisation :** Boîtes de dialogue de validation pour prévenir les suppressions accidentelles.

## 🛠️ Stack Technique
* **Langage :** Java SE 8 (JDK 1.8)
* **Framework IHM :** JavaFX (Intégré nativement) / FXML
* **Framework ORM :** Hibernate Core 5.6
* **Base de Données :** MySQL 8
* **Gestionnaire de dépendances :** Maven
* **IDE recommandé :** NetBeans 8.2

## 🚀 Installation & Exécution

### 1. Prérequis
* Avoir installé le **JDK 8**.
* Avoir installé un serveur MySQL (XAMPP, WAMP, ou MySQL Server autonome).

### 2. Configuration de la Base de Données
1. Ouvrez votre outil d'administration MySQL (ex: phpMyAdmin).
2. Exécutez le script SQL fourni dans le projet : `script.sql`.
   *(Ce script crée la base `gestion_produits` avec l'encodage `utf8mb4` et insère des données de test).*
3. Si votre mot de passe "root" MySQL n'est pas vide, modifiez le fichier `src/main/resources/hibernate.cfg.xml` à la ligne `hibernate.connection.password`.

### 3. Lancer l'application
1. Clonez ce dépôt sur votre machine locale :
   ```bash
   git clone [https://github.com/elazrakmohammed/gestion-catalogue-bdsi.git](https://github.com/VOTRE_PSEUDO/gestion-catalogue-bdsi.git)
2. Ouvrez le projet avec NetBeans 8.2.
3. Effectuez un Clean and Build pour que Maven télécharge les dépendances.
4. Exécutez le fichier Main.java.
   
## 📁 Architecture du Code
Le projet respecte une architecture en couches strictes :

* model/ : Entités POJO (Produit.java) avec annotations JPA (@Entity, @Table).
* service/ : Logique métier et gestion des transactions Hibernate (ProduitService.java).
* controller/ : Gestion des événements UI et liaison Vue-Modèle (ProduitController.java).
* resources/ : Fichiers de configuration (hibernate.cfg.xml), vues (produit.fxml) et styles (style.css).

Réalisé par Mohammed.

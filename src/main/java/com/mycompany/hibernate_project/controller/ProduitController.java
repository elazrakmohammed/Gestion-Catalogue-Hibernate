package com.mycompany.hibernate_project.controller;

import com.mycompany.hibernate_project.model.Produit;
import com.mycompany.hibernate_project.service.ProduitService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProduitController {

    // --- LIAISON AVEC LE FICHIER FXML (Les ID) ---
    @FXML private TextField txtCode;
    @FXML private TextField txtLibelle;
    @FXML private ComboBox<String> cbType;
    @FXML private TextField txtQuantite;
    @FXML private CheckBox chkDispo;
    @FXML private TextField txtRecherche;

    @FXML private TableView<Produit> tableProduits;
    @FXML private TableColumn<Produit, Long> colId;
    @FXML private TableColumn<Produit, String> colCode;
    @FXML private TableColumn<Produit, String> colLibelle;
    @FXML private TableColumn<Produit, String> colType;
    @FXML private TableColumn<Produit, Integer> colQuantite;
    @FXML private TableColumn<Produit, Boolean> colDispo;

    // --- NOTRE CHEF D'ORCHESTRE ---
    private final ProduitService service = new ProduitService();
    
    // Liste "observable" pour que le tableau se mette à jour tout seul
    private final ObservableList<Produit> masterData = FXCollections.observableArrayList();
    private Produit produitSelectionne; // Garde en mémoire le produit cliqué

    // --- INITIALISATION (Se lance tout seul à l'ouverture) ---
    @FXML
    public void initialize() {
        // 1. Remplir la liste déroulante des types
        cbType.setItems(FXCollections.observableArrayList("Épicerie", "Produits Frais", "Boissons", "Surgelés", "Laitages"));

        // 2. Dire à chaque colonne quelle variable de la classe Produit elle doit afficher
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("quantiteStock"));
        colDispo.setCellValueFactory(new PropertyValueFactory<>("disponibilite"));

        // 3. Charger les produits depuis la base
        chargerDonnees();

        // 4. Écouter les clics sur le tableau pour préremplir le formulaire
        tableProduits.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                produitSelectionne = newSelection;
                txtCode.setText(produitSelectionne.getCode());
                txtLibelle.setText(produitSelectionne.getLibelle());
                cbType.setValue(produitSelectionne.getType());
                txtQuantite.setText(String.valueOf(produitSelectionne.getQuantiteStock()));
                chkDispo.setSelected(produitSelectionne.isDisponibilite());
            }
        });

        // 5. BONUS : Barre de recherche en temps réel
        FilteredList<Produit> filteredData = new FilteredList<>(masterData, p -> true);
        txtRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(produit -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                // Cherche dans le libellé OU dans le type
                return produit.getLibelle().toLowerCase().contains(lowerCaseFilter) 
                    || produit.getType().toLowerCase().contains(lowerCaseFilter);
            });
        });
        tableProduits.setItems(filteredData); // On lie le tableau aux données filtrées
    }

    // --- CHARGER LES DONNÉES ---
    private void chargerDonnees() {
        masterData.clear();
        masterData.addAll(service.getAllProduits());
    }

    // --- BOUTON AJOUTER ---
    @FXML
    private void handleAjouter() {
        try {
            Produit p = new Produit(
                txtCode.getText(), txtLibelle.getText(), cbType.getValue(),
                Integer.parseInt(txtQuantite.getText()), chkDispo.isSelected()
            );
            service.ajouterProduit(p);
            chargerDonnees();
            handleActualiser(); // On vide les cases après l'ajout
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "La quantité doit être un nombre.");
        }
    }

    // --- BOUTON MODIFIER ---
    @FXML
    private void handleModifier() {
        if (produitSelectionne != null) {
            try {
                produitSelectionne.setCode(txtCode.getText());
                produitSelectionne.setLibelle(txtLibelle.getText());
                produitSelectionne.setType(cbType.getValue());
                produitSelectionne.setQuantiteStock(Integer.parseInt(txtQuantite.getText()));
                produitSelectionne.setDisponibilite(chkDispo.isSelected());

                service.modifierProduit(produitSelectionne);
                chargerDonnees();
                handleActualiser();
            } catch (NumberFormatException e) {
                afficherAlerte("Erreur", "La quantité doit être un nombre.");
            }
        }
    }

    // --- BOUTON SUPPRIMER ---
@FXML
private void handleSupprimer() {
    if (produitSelectionne != null) {
        // 1. Créer la boîte de dialogue de confirmation
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText("Suppression du produit : " + produitSelectionne.getLibelle());
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce produit ? Cette action est irréversible.");

        // 2. Afficher la boîte et attendre le clic de l'utilisateur
        java.util.Optional<ButtonType> result = confirmation.showAndWait();
        
        // 3. Si l'utilisateur clique sur OK, on supprime vraiment
        if (result.isPresent() && result.get() == ButtonType.OK) {
            service.supprimerProduit(produitSelectionne.getId());
            chargerDonnees();
            handleActualiser();
        }
    } else {
        afficherAlerte("Sélection requise", "Veuillez sélectionner un produit dans le tableau avant de cliquer sur Supprimer.");
    }
}

    // --- BOUTON VIDER / ACTUALISER ---
    @FXML
    private void handleActualiser() {
        produitSelectionne = null;
        txtCode.clear();
        txtLibelle.clear();
        cbType.setValue(null);
        txtQuantite.clear();
        chkDispo.setSelected(false);
        tableProduits.getSelectionModel().clearSelection();
    }

    // --- BOÎTE DE DIALOGUE D'ERREUR ---
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
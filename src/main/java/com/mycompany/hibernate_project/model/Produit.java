package com.mycompany.hibernate_project.model;

import javax.persistence.*;

@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "quantite_stock", nullable = false)
    private int quantiteStock;

    @Column(name = "disponibilite", nullable = false)
    private boolean disponibilite;

    // Constructeur vide obligatoire pour Hibernate
    public Produit() {
    }

    // Constructeur avec paramètres (sans l'ID qui est auto-incrémenté)
    public Produit(String code, String libelle, String type, int quantiteStock, boolean disponibilite) {
        this.code = code;
        this.libelle = libelle;
        this.type = type;
        this.quantiteStock = quantiteStock;
        this.disponibilite = disponibilite;
    }

    // --- GETTERS ET SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getQuantiteStock() { return quantiteStock; }
    public void setQuantiteStock(int quantiteStock) { this.quantiteStock = quantiteStock; }

    public boolean isDisponibilite() { return disponibilite; }
    public void setDisponibilite(boolean disponibilite) { this.disponibilite = disponibilite; }
}
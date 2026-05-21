package com.mycompany.hibernate_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        java.net.URL url = Main.class.getResource("/produit.fxml");
        if (url == null) {
            System.err.println("🚨 ERREUR FATALE : Impossible de trouver '/produit.fxml'.");
            return; 
        }

        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load());
        
        // On applique ton nouveau style CSS moderne
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        
        stage.setTitle("Gestion Catalogue - PME Distribution");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author bens
 */
public class FXMLMuttLab extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        stage.setTitle("MuttLab App");
        
        Parent parent;
        parent = FXMLLoader.load(getClass().getResource("fxml_muttlab.fxml"));
        
        Scene scene = new Scene(parent);
        
        stage.setScene(scene);

        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
}

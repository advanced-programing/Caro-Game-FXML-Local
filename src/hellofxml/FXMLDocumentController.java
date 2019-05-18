/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class FXMLDocumentController {
    //@FXML -> lien ket thanh phan trong view va controller
    //su dung css de tao format cho giao dien
    //1 fxml ưng voi 1 controller
    
    @FXML
    private Label message;
    @FXML
    private Button chooseGameType;
    @FXML 
    private Button chooseGameSize;
    @FXML 
    private Button start;
    @FXML 
    private Button quit;
    
    @FXML
    private void handleGameSize(ActionEvent event) throws IOException{
        Hellofxml.setGameSizeMenu();
    }
    
    @FXML
    void handleGameType(ActionEvent event) throws IOException{
        Hellofxml.setGameTypeMenu();
    }
    
    @FXML
    void startGame(ActionEvent event) throws IOException{
        if(FXMLGameTypeController.type==1)
            Hellofxml.setPlayerInforBox();
        else
            Hellofxml.setGameBoardMenu();
    }
    
    @FXML
    void quitGame(ActionEvent event) {
        Platform.exit();
    }
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

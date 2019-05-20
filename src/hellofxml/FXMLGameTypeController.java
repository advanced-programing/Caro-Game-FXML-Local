/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class FXMLGameTypeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Hellofxml application; 
    public static int type=1;
    
    @FXML
    private Button home;
    
    @FXML
    private Button playerTypeButton;
    
    @FXML
    private Button computerTypeButton;
    @FXML
    private Button bt_remote;
    
    @FXML
    private void playerTypeAction(){
        type = 1;
    }
    
    
    @FXML
    private void computerTypeAction() throws IOException{
        type = 2;
        application.setGameLevelMenu();
    }
    
    @FXML
    private void goHome() throws IOException{
        application.setMainMenu();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setApp(Hellofxml application) {
        this.application = application; 
    }
    @FXML
    private void playRemote(ActionEvent event) {
        System.out.println("CCL " + "User choose play with remote clients");
        application.userChoseRemote(true);
    }
    
}

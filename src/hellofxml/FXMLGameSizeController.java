/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class FXMLGameSizeController implements Initializable {
    private Hellofxml application; 
    public void setApp(Hellofxml application) {
        this.application = application; 
    }
    public static int size=5;
    /**
     * Initializes the controller class.
     */
    @FXML
    private Button home;
    
    @FXML
    private void goHome() throws IOException{
        application.setMainMenu();
    }
    
    @FXML
    private void size1(){
        size=5;
        System.out.println("size = " + size);
    }
    
    @FXML
    private void size2(){
        size=10;
        System.out.println("size = " + size);
    }
    
    @FXML
    private void size3(){
        size=15;
        System.out.println("size = " + size);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

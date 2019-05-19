/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class NoticeBoxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Image image = new Image(getClass().getResourceAsStream("/image/information.png"));
    Media sound = new Media(this.getClass().getResource("/sound/win.mp3").toExternalForm());
    
    @FXML
    private Button okButton;
    
    @FXML
    private Label noticeLabel;
    
    @FXML
    private Label player1Score;
    
    @FXML
    private Label player2Score;
    
    @FXML
    private void handleOkButton(ActionEvent event){
        Hellofxml.noticeBox.close();
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        noticeLabel.setGraphic(new ImageView(image));
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        if(null != FXMLGameBoardController.getCurrentState())
            switch (FXMLGameBoardController.getCurrentState()) {
            case X_WIN:
                noticeLabel.setText("The winner of this match is "+FXMLGameBoardController.getUsername1());
                break;
            case O_WIN:
                noticeLabel.setText("The winner of this match is "+FXMLGameBoardController.getUsername2());
                break;
            case DRAW:
                noticeLabel.setText("This's a draw match");
                break;
            default:
                break;
        }
        
        player1Score.setText(FXMLGameBoardController.getUsername1()+" : "+FXMLGameBoardController.getScore1());
        player2Score.setText(FXMLGameBoardController.getUsername2() +" : "+FXMLGameBoardController.getScore2());
    }    
    
}

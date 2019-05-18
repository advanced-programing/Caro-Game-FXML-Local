/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//cai javafx_scene builder 2.0
//netbean: tool -> option -> java -> javafx -> chen link da tai scene builder
//hoc: oracle javafx
//id de format: properti
//fx id code: code
//cài netbean 11 -> java mavin -> co san css
//gui thong tin: khi ket noi gui toa do (dang choi), khi nao ket thuc (input stream reader)
//su dung buffer de luu du lieu khi toc do 2 ben khong deu
//khong su dung cong >1024
//2 nguoi choi tren cung 1 may ->cung local host
//tao 2 thread rieng phia client/server
package hellofxml;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class Hellofxml extends Application {
    public static Stage myStage;
    public static Stage confirmBox;
    public static Stage noticeBox;
    
    @Override
    public void start(Stage stage) throws Exception {
        //confirmBox = new Stage();
        myStage = stage;
        setMainMenu();
        
        myStage.show();
    }

    public static void setMainMenu() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Hellofxml.class.getResource("FXMLDocument.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        myStage.setScene(scene);
        myStage.setTitle("Main menu");
    }
    
    public static void setGameSizeMenu() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Hellofxml.class.getResource("FXMLGameSize.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        myStage.setScene(scene);
        myStage.setTitle("Game size menu");
    }
 
    public static void setGameTypeMenu() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Hellofxml.class.getResource("FXMLGameType.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        myStage.setScene(scene);
        myStage.setTitle("Game type menu");
    }
    
    public static void setGameLevelMenu() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Hellofxml.class.getResource("ChooseLevelBox.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        myStage.setScene(scene);
        myStage.setTitle("Game level menu");
    }
    
    public static void setGameBoardMenu() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Hellofxml.class.getResource("FXMLGameBoard.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        myStage.setScene(scene);
        myStage.setTitle("Game board menu");
    }
    
    public static void setPlayerInforBox() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Hellofxml.class.getResource("PlayerInforBox.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        myStage.setScene(scene);
        myStage.setTitle("User information box");
    }
    
    public static void addConfirmBox() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Hellofxml.class.getResource("ConfirmBox.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        confirmBox = new Stage();
        confirmBox.initModality(Modality.APPLICATION_MODAL);
        confirmBox.setScene(scene);
        confirmBox.setTitle("Confirm Box");
        confirmBox.showAndWait();
    }
    
    public static void addNoticeBox() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Hellofxml.class.getResource("NoticeBox.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        noticeBox = new Stage();
        noticeBox.initModality(Modality.APPLICATION_MODAL);
        noticeBox.setScene(scene);
        noticeBox.setTitle("Notice Box");
        noticeBox.showAndWait();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
 
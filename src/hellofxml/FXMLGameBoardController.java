/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofxml;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class FXMLGameBoardController implements Initializable {
    private Hellofxml application; 
    public void setApp(Hellofxml application) {
        this.application = application; 
    }
    /**
     * Initializes the controller class.
     */
    private static int BOARD_WIDTH = 450;
    private static int BOARD_HEIGHT = 450;
    private static int CELL_SIZE = 25;
    private static int PAD = 8;
    private int BOARD_SIZE;
    private static int MAX_SCORE = 10;
    private static int MIN_SCORE = -10;
    private SEED playingMap[][];
    private boolean PLAYABLE = true;
    private static int score1;
    private static int score2;
    private int gameType;
    private int gameLevel;
    private Position bestPos = new Position();
    private int mainPointStep = 5;

    private enum SEED {
        EMPTY, X, O;
    }

    private SEED currentPlayer;

    private SEED computerSeed = SEED.O;
    private SEED playerSeed = SEED.X;

    private GraphicsContext gc;
    private Canvas board;

    private static GameState currentState = GameState.PLAYING;

    private static String userName1;
    private static String userName2;
    private Image XLabel = new Image("/image/X.PNG");
    private Image OLabel = new Image("/image/O.PNG");

    Media sound = new Media(this.getClass().getResource("/sound/click.mp3").toExternalForm());

    @FXML
    private HBox boardPane;

    //@FXML
    //private Label notice;
    @FXML
    private Button newMatch;

    @FXML
    private Label player1Name;

    @FXML
    private Label score;
    @FXML
    private Label player2Name;

    @FXML
    private Button newGame;

    @FXML
    private Button quit;

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;

    @FXML
    private void handle2Player(MouseEvent event) throws IOException {
        int r = (int) ((event.getX() - PAD) / (CELL_SIZE + 4));
        int c = (int) ((event.getY() - PAD) / (CELL_SIZE + 4));

        if (gameType == 1) {
            playAt(r, c);
        } else {
            //System.out.println("play with computer");
            if (PLAYABLE) {
                playAt(r, c);
                if (PLAYABLE) {
                    Position P = new Position();
                    P = findBestPosition(computerSeed, gameLevel);
                    //System.out.println("Scores = "+P.getPoint());
                    //System.out.println("r = "+P.getRow()+"  c = "+P.getColumn());
                    playAt(P.getRow(), P.getColumn());
                }
            }
        }
    }

    @FXML
    private void handleNewGame() {
        player2Name.setTextFill(Color.WHITE);
        player1Name.setTextFill(Color.RED);
        PLAYABLE = true;
        currentPlayer = SEED.X;
        score1 = 0;
        score2 = 0;
        score.setText(score1 + " : " + score2);
        resetBoard();
        drawBoard(BOARD_SIZE, gc);
        //notice.setText("Player "+userName1+" go...");
    }

    @FXML
    private void handleNewMatch() {
        player2Name.setTextFill(Color.WHITE);
        player1Name.setTextFill(Color.WHITE);
        if (gameType == 1) {
            if (currentState == GameState.O_WIN) {
                currentPlayer = SEED.O;
                player2Name.setTextFill(Color.SLATEBLUE);
            } else {
                currentPlayer = SEED.X;
                player1Name.setTextFill(Color.RED);
            }
        } else {
            currentPlayer = SEED.X;
            player1Name.setTextFill(Color.RED);
        }

        resetBoard();
        drawBoard(BOARD_SIZE, gc);
        currentState = GameState.PLAYING;
        PLAYABLE = true;
    }

    @FXML
    private void quitGame(ActionEvent event) throws IOException {
        Hellofxml.addConfirmBox();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        gameType = FXMLGameTypeController.type;
        gameLevel = ChooseLevelBoxController.level;
        player2Name.setTextFill(Color.WHITE);
        player1Name.setTextFill(Color.RED);

        if (gameType == 1) {
            userName1 = PlayerInforBoxController.name1;
            userName2 = PlayerInforBoxController.name2;
        } else {
            userName1 = "You";
            userName2 = "Computer";
        }

        player1Name.setText("" + userName1);
        player2Name.setText("" + userName2);
        player1Label.setGraphic(new ImageView(XLabel));
        player2Label.setGraphic(new ImageView(OLabel));
        score1 = 0;
        score2 = 0;
        score.setText(score1 + " : " + score2);
        currentPlayer = SEED.X;

        System.out.println("gameType = " + gameType + "  gameLevel " + gameLevel);

        BOARD_SIZE = FXMLGameSizeController.size;
        //notice.setText("Player "+userName1+" go...");
        board = new Canvas(BOARD_WIDTH, BOARD_HEIGHT);
        gc = board.getGraphicsContext2D();
        boardPane.getChildren().add(board);
        drawBoard(BOARD_SIZE, gc);
        player2Name.setTextFill(Color.WHITE);
        player1Name.setTextFill(Color.RED);

        System.out.println("draw board");
        resetBoard();
    }

    public boolean checkDraw() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (playingMap[r][c] == SEED.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkWin(int row, int column) {
        SEED crPlayer = playingMap[row][column];
        if (crPlayer == SEED.EMPTY) {
            return false;
        }
        int count = 1;
        int i = row, j = column;
        //checking by row
        j = column - 1;
        while (j >= 0 && playingMap[i][j] == crPlayer) {
            count++;
            j--;
        }
        j = column + 1;
        while (j < BOARD_SIZE && playingMap[i][j] == crPlayer) {
            count++;
            j++;
        }

        if (count >= 5) {
            return true;
        } else {
            count = 1;
        }
        //checking by column
        i = row - 1;
        j = column;
        while (i >= 0 && playingMap[i][j] == crPlayer) {
            count++;
            i--;
        }

        i = row + 1;
        while (i < BOARD_SIZE && playingMap[i][j] == crPlayer) {
            count++;
            i++;
        }

        if (count >= 5) {
            return true;
        } else {
            count = 1;
        }
        //checking by first diagonal
        i = row - 1;
        j = column - 1;
        while (i >= 0 && j >= 0 && playingMap[i][j] == crPlayer) {
            count++;
            i--;
            j--;
        }

        i = row + 1;
        j = column + 1;
        while (i < BOARD_SIZE && j < BOARD_SIZE && playingMap[i][j] == crPlayer) {
            count++;
            i++;
            j++;
        }
        if (count >= 5) {
            return true;
        } else {
            count = 1;
        }
        //checking by second diagonal
        i = row + 1;
        j = column - 1;
        while (i < BOARD_SIZE && j >= 0 && playingMap[i][j] == crPlayer) {
            count++;
            i++;
            j--;
        }

        i = row - 1;
        j = column + 1;
        while (i >= 0 && j < BOARD_SIZE && playingMap[i][j] == crPlayer) {
            count++;
            i--;
            j++;
        }

        if (count >= 5) {
            return true;
        } else {
            return false;
        }
    }

    //choi mot nuoc
    private boolean playAt(int r, int c) throws IOException {
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        if (r < BOARD_SIZE && c < BOARD_SIZE && PLAYABLE == true) {
            if (playingMap[r][c] == SEED.EMPTY) {
                playingMap[r][c] = currentPlayer;
                if (currentPlayer == SEED.X) {
                    player2Name.setTextFill(Color.SLATEBLUE);
                    player1Name.setTextFill(Color.WHITE);
                    drawCross(r, c);

                    if (checkWin(r, c)) {
                        //notice.setText("Player "+userName1+" wins, gameover!");
                        score1++;
                        currentState = GameState.X_WIN;
                        PLAYABLE = false;
                        player2Name.setTextFill(Color.WHITE);
                        Hellofxml.addNoticeBox();
                        score.setText(score1 + " : " + score2);
                        return true;
                        //Hellofxml.addConfirmBox();
                    } else if (checkDraw()) {
                        // notice.setText("It's a draw, gameover!");
                        currentState = GameState.DRAW;
                        PLAYABLE = false;
                        player2Name.setTextFill(Color.WHITE);
                        Hellofxml.addNoticeBox();
                        //Hellofxml.addConfirmBox();
                    } else {
                        currentState = GameState.PLAYING;
                        currentPlayer = SEED.O;
                        PLAYABLE = true;
                        //notice.setText("Player "+userName2+" go...");
                    }
                } else {
                    player1Name.setTextFill(Color.RED);
                    player2Name.setTextFill(Color.WHITE);
                    drawCircle(r, c);

                    if (checkWin(r, c)) {
                        //notice.setText("Player "+userName2+" wins, gameover!");
                        score2++;
                        currentState = GameState.O_WIN;
                        PLAYABLE = false;
                        player1Name.setTextFill(Color.WHITE);
                        Hellofxml.addNoticeBox();
                        score.setText(score1 + " : " + score2);
                        return true;
                        //Hellofxml.addConfirmBox();
                    } else if (checkDraw()) {
                        // notice.setText("It's a draw, gameover!");
                        currentState = GameState.DRAW;
                        PLAYABLE = false;
                        player1Name.setTextFill(Color.WHITE);
                        Hellofxml.addNoticeBox();
                        //Hellofxml.addConfirmBox();
                    } else {
                        currentState = GameState.PLAYING;
                        currentPlayer = SEED.X;
                        PLAYABLE = true;
                        //notice.setText("Player "+userName1+" go...");
                    }
                }
            }
        }
        return false;
    }

    private Position findBestPosition(SEED turn, int level) {
        Position bestPos = new Position();

        List<Position> validPos = new Vector<>();
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (playingMap[r][c] == SEED.EMPTY) {
                    Position pos = new Position(r, c);
                    int point = getMainPointAt(turn, r, c, playingMap);
                    if (point >= 4 * mainPointStep) {
                        point = 6 * mainPointStep;
                    }
                    pos.setPoint(point);
                    validPos.add(pos);
                }
            }
        }

        if (!validPos.isEmpty()) {
            Comparator compareProc = new Comparator<Position>() {
                @Override
                public int compare(Position o1, Position o2) {
                    if (o1.getPoint() == o2.getPoint()) {
                        return 0;
                    } else if (o1.getPoint() < o2.getPoint()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            };

            switch (level) {
                case 1: {//chon mot vi tri random
                    int n = validPos.size();
                    Random rand = new Random();
                    bestPos = validPos.get(rand.nextInt(n));
                    break;
                }
                case 2: { //chon vi tri co diem thang cao nhat
                    validPos.sort(compareProc);
                    bestPos = validPos.get(0);
                    break;
                }
                case 3: { //xem xet giua diem thang va diem thua
                    for (int i = 0; i < validPos.size(); i++) {
                        Position p = validPos.get(i);
                        int oppPoint = getOpponentPointAt(turn, validPos.get(i).getRow(), validPos.get(i).getColumn());
                        if (oppPoint >= 3) {
                            validPos.get(i).setPoint(oppPoint + 1);
                        }
                    }
                    validPos.sort(compareProc);
                    bestPos = validPos.get(0);
                    break;
                }
            }
        }
        return bestPos;
    }

    private int getMainPointAt(SEED turn, int row, int column, SEED playingMap[][]) {
        int count = 0;  //count the number of positions next to (row, column)
        int i = row, j = column;
        int r_count = 0;
        //checking by row
        j = column - 1;
        while (j >= 0 && playingMap[i][j] == turn) {
            r_count = r_count + mainPointStep;
            j--;
        }
        if (j > 0) {
            r_count++;
        }

        j = column + 1;
        while (j < BOARD_SIZE && playingMap[i][j] == turn) {
            r_count = r_count + mainPointStep;
            j++;
        }
        if (j < BOARD_SIZE - 1) {
            r_count++;
        }

        int c_count = 0;
        //checking by column
        i = row - 1;
        j = column;
        while (i >= 0 && playingMap[i][j] == turn) {
            c_count = c_count + mainPointStep;
            i--;
        }
        if (i > 0) {
            c_count++;
        }

        i = row + 1;
        while (i < BOARD_SIZE && playingMap[i][j] == turn) {
            c_count = c_count + mainPointStep;
            i++;
        }
        if (i < BOARD_SIZE - 1) {
            c_count++;
        }
        //checking by first diagonal
        int fd_count = 0;
        i = row - 1;
        j = column - 1;
        while (i >= 0 && j >= 0 && playingMap[i][j] == turn) {
            fd_count = fd_count + mainPointStep;
            i--;
            j--;
        }
        if (i > 0 && j > 0) {
            fd_count++;
        }

        i = row + 1;
        j = column + 1;
        while (i < BOARD_SIZE && j < BOARD_SIZE && playingMap[i][j] == turn) {
            fd_count = fd_count + mainPointStep;
            i++;
            j++;
        }
        if (i < BOARD_SIZE - 1 && j < BOARD_SIZE - 1) {
            fd_count++;
        }
        //checking by second diagonal
        int sd_count = 0;
        i = row + 1;
        j = column - 1;
        while (i < BOARD_SIZE && j >= 0 && playingMap[i][j] == turn) {
            sd_count = sd_count + mainPointStep;
            i++;
            j--;
        }

        if (i < BOARD_SIZE - 1 && j > 0) {
            sd_count++;
        }
        i = row - 1;
        j = column + 1;
        while (i >= 0 && j < BOARD_SIZE && playingMap[i][j] == turn) {
            sd_count = sd_count + mainPointStep;
            i--;
            j++;
        }
        if (i > 0 && j < BOARD_SIZE - 1) {
            sd_count++;
        }

        return Math.max(Math.max(r_count, c_count), Math.max(fd_count, sd_count));

    }

    private int getOpponentPointAt(SEED turn, int row, int column) {
        int count = 0;  //count the number of positions next to (row, column)
        int i = row, j = column;
        int r_count = 0;
        //checking by row
        j = column - 1;
        while (j >= 0 && playingMap[i][j] != SEED.EMPTY && playingMap[i][j] != turn) {
            r_count = r_count + mainPointStep;
            j--;
        }
        if (j > 0) {
            r_count++;
        }
        j = column + 1;
        while (j < BOARD_SIZE && playingMap[i][j] != SEED.EMPTY && playingMap[i][j] != turn) {
            r_count = r_count + mainPointStep;
            j++;
        }
        if (j < BOARD_SIZE - 1) {
            r_count++;
        }

        int c_count = 0;
        //checking by column
        i = row - 1;
        j = column;
        while (i >= 0 && playingMap[i][j] != SEED.EMPTY && playingMap[i][j] != turn) {
            c_count = c_count + mainPointStep;
            i--;
        }
        if (i > 0) {
            c_count++;
        }

        i = row + 1;
        while (i < BOARD_SIZE && playingMap[i][j] != SEED.EMPTY && playingMap[i][j] != turn) {
            c_count = c_count + mainPointStep;
            i++;
        }
        if (i < BOARD_SIZE - 1) {
            c_count++;
        }

        //checking by first diagonal
        int fd_count = 0;
        i = row - 1;
        j = column - 1;
        while (i >= 0 && j >= 0 && playingMap[i][j] != SEED.EMPTY && playingMap[i][j] != turn) {
            fd_count = fd_count + mainPointStep;
            i--;
            j--;
        }
        if (i > 0 && j > 0) {
            fd_count++;
        }

        i = row + 1;
        j = column + 1;
        while (i < BOARD_SIZE && j < BOARD_SIZE && playingMap[i][j] != SEED.EMPTY && playingMap[i][j] != turn) {
            fd_count = fd_count + mainPointStep;
            i++;
            j++;
        }
        if (i < BOARD_SIZE - 1 && j < BOARD_SIZE - 1) {
            fd_count++;
        }

        //checking by second diagonal
        int sd_count = 0;
        i = row + 1;
        j = column - 1;
        while (i < BOARD_SIZE && j >= 0 && playingMap[i][j] != SEED.EMPTY && playingMap[i][j] != turn) {
            sd_count = sd_count + mainPointStep;
            i++;
            j--;
        }
        if (i < BOARD_SIZE - 1 && j > 0) {
            sd_count++;
        }

        i = row - 1;
        j = column + 1;
        while (i >= 0 && j < BOARD_SIZE && playingMap[i][j] != SEED.EMPTY && playingMap[i][j] != turn) {
            sd_count = sd_count + mainPointStep;
            i--;
            j++;
        }
        if (i > 0 && j < BOARD_SIZE - 1) {
            sd_count++;
        }

        return Math.max(Math.max(r_count, c_count), Math.max(fd_count, sd_count));
    }

    /*private Position findBestPosition(){
        Position bestPos = new Position();
        int bestVal = Integer.MIN_VALUE;
        
        for(int r=0; r<BOARD_SIZE; r++){
            for(int c=0; c<BOARD_SIZE; c++){
                if(playingMap[r][c]==SEED.EMPTY){
                    playingMap[r][c] = computerSeed;
                    int moveVal = minimax(0, playingMap, playerSeed);
                    playingMap[r][c] = SEED.EMPTY;
                    if(moveVal > bestVal){
                        bestVal = moveVal;
                        bestPos.setRow(r);
                        bestPos.setColumn(c);
                    }
                }
            }
        }
        return bestPos;
    } */

 /* private int miniMax(int depth, SEED turn, int alpha, int beta, SEED map[][]){
        int bestRow=-1;
        int bestCol=-1;
        int score;
        if(depth>=5||isGameOver(map)){
            //System.out.println("depth = "+depth+" over = "+isGameOver(map));
            bestPos.setColumn(bestCol);
            bestPos.setRow(bestRow);
            score = evaluate(depth, map);
            return score;
        }
        else{
        for(int r=0; r<BOARD_SIZE; r++){
            for(int c=0; c<BOARD_SIZE; c++){
                if(map[r][c]==SEED.EMPTY){
                    map[r][c] = turn;
                    if(turn == computerSeed){
                        score = miniMax(depth + 1, playerSeed, alpha, beta, map);
                        if (score > alpha) {
                            alpha = score;
                            bestRow = r;
                            bestCol = c;
                        }
                    }
                    else{
                        score = miniMax(depth + 1, computerSeed, alpha, beta, map);
                        if (score < beta) {
                            beta = score;
                            bestRow = r;
                            bestCol = c;
                        }
                    }
                    map[r][c] = SEED.EMPTY;
                    
                }
                //System.out.println("r , c: "+r+" "+c+" score:"+score);
                if(alpha>beta)
                    break;
                
            }
        }
        bestPos.setColumn(bestCol);
        bestPos.setRow(bestRow);
        return (turn==computerSeed?alpha:beta);
    } 
    }*/
 /*private List<Position> findValidPos(){
        List<Position> validPos = new ArrayList<>();
        
        if(PLAYABLE == false)
            return validPos;
        
        for(int r=0; r<BOARD_SIZE; r++){
            for(int c=0; c<BOARD_SIZE; c++){
                if(playingMap[r][c]==SEED.EMPTY){
                    Position pos = new Position(r, c);
                    validPos.add(pos);
                }
            }
        }
        return validPos;
    }
    
    private int miniMax2(int depth, SEED player){
        List<Position> validPos = findValidPos();
        //bestPos= new Position();
        int maxDepth = depth;
        int bestScore = (player == computerSeed)? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        
        if(validPos.isEmpty()||depth==0){
            bestScore = evaluate(maxDepth - depth);
        }
        else{
            for(Position move : validPos){
                System.out.println("computer move at : "+move.getRow()+" , "+move.getColumn());
                playingMap[move.getRow()][move.getColumn()] = player;
                if(player == computerSeed){
                    currentScore = miniMax(depth-1, playerSeed);
                    if(currentScore > bestScore){
                        bestScore = currentScore;
                        bestPos.setRow(move.getRow());
                        bestPos.setColumn(move.getColumn());
                    }
                }
                else{
                    currentScore = miniMax(depth-1, computerSeed);
                    if(currentScore < bestScore){
                        bestScore = currentScore;
                        bestPos.setRow(move.getRow());
                        bestPos.setColumn(move.getColumn());
                    }
                }
                playingMap[move.getRow()][move.getColumn()] = SEED.EMPTY;
            }
        }
        return bestScore;
    } */
    private boolean isGameOver() {
        //printMap(map);
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (checkWin(r, c)) {
                    //System.out.println("win at r, c: "+r+" "+c);
                    return true;
                }
            }
        }
        if (checkDraw()) {
            // System.out.println("Draw");
            return true;
        }
        return false;
    }

    public void printMap(SEED map[][]) {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                int t;
                if (map[r][c] == SEED.EMPTY) {
                    t = 0;
                } else if (map[r][c] == SEED.X) {
                    t = 1;
                } else {
                    t = 2;
                }
                System.out.print(" " + t);
            }
            System.out.println("");
        }
    }

    public void resetBoard() {
        if (playingMap != null) {
            playingMap = null;
        }
        playingMap = new SEED[BOARD_SIZE][BOARD_SIZE];
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                playingMap[r][c] = SEED.EMPTY;
            }
        }
    }

    private void drawCross(int row, int col) {
        int inner_pad = 3;
        int border_width = 4;
        gc.clearRect(PAD + row * (CELL_SIZE + border_width), PAD + col * (CELL_SIZE + border_width), CELL_SIZE, CELL_SIZE);

        gc.fillRect(PAD + row * (CELL_SIZE + border_width), PAD + col * (CELL_SIZE + border_width), CELL_SIZE, CELL_SIZE);
        gc.setStroke(Color.RED);
        gc.setLineWidth(3.0);
        gc.strokeLine(PAD + row * (CELL_SIZE + border_width) + inner_pad, PAD + col * (CELL_SIZE + border_width) + inner_pad, PAD + row * (CELL_SIZE + border_width) + CELL_SIZE - inner_pad, PAD + col * (CELL_SIZE + border_width) + CELL_SIZE - inner_pad);
        gc.strokeLine(PAD + row * (CELL_SIZE + border_width) + inner_pad, PAD + col * (CELL_SIZE + border_width) + CELL_SIZE - inner_pad, PAD + row * (CELL_SIZE + border_width) + CELL_SIZE - inner_pad, PAD + col * (CELL_SIZE + border_width) + inner_pad);
    }

    private void drawCircle(int row, int col) {
        int inner_pad = 3;
        int border_width = 4;
        gc.clearRect(PAD + row * (CELL_SIZE + border_width), PAD + col * (CELL_SIZE + border_width), CELL_SIZE, CELL_SIZE);

        gc.fillRect(PAD + row * (CELL_SIZE + border_width), PAD + col * (CELL_SIZE + border_width), CELL_SIZE, CELL_SIZE);
        gc.setStroke(Color.SLATEBLUE);
        gc.setLineWidth(3.0);
        gc.strokeOval(PAD + row * (CELL_SIZE + border_width) + inner_pad, PAD + col * (CELL_SIZE + border_width) + inner_pad, CELL_SIZE - 2 * inner_pad, CELL_SIZE - 2 * inner_pad);
    }

    private void drawBoard(int n, GraphicsContext gc) {
        gc.clearRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        gc.setFill(Color.WHITE);
        //int pad = 4;
        double x = 0, y = 0;
        //double w = 25, h = 25;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                gc.fillRect(PAD + i * (CELL_SIZE + 4), PAD + j * (CELL_SIZE + 4), CELL_SIZE, CELL_SIZE);
            }
        }
        gc.setLineWidth(3.0);
        gc.setStroke(Color.CADETBLUE);
        gc.strokeRect(2, 2, PAD + n * (CELL_SIZE + 4), PAD + n * (CELL_SIZE + 4));
    }

    public static int getScore1() {
        return score1;
    }

    public static int getScore2() {
        return score2;
    }

    public static GameState getCurrentState() {
        return currentState;
    }

    public static String getUsername1() {
        return userName1;
    }

    public static String getUsername2() {
        return userName2;
    }
}

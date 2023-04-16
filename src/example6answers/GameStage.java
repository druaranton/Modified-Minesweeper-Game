package example6answers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameStage {
	private Scene scene;
	private Stage stage;
	/*Group layout/container is a component which applies no special
	layout to its children. All child components (nodes) are positioned at 0,0*/
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private Element flag;
	private GridPane map;
	private int[][] gameBoard;
	private ArrayList<Element> landCells;

	private boolean flagClicked=false;

	public final static int MAX_CELLS = 81; //it will be 81 cells
	public final static int MAP_NUM_ROWS = 9; //9 rows
	public final static int MAP_NUM_COLS = 9; //9 columns
	public final static int MAP_WIDTH = 400;
	public final static int MAP_HEIGHT = 400;
	public final static int CELL_WIDTH = 40;
	public final static int CELL_HEIGHT = 40;
	public final static int FLAG_WIDTH = 70;
	public final static int FLAG_HEIGHT = 70;
	public final static boolean IS_GAME_DONE = false;
	public final static int WINDOW_WIDTH = 420;
	public final static int WINDOW_HEIGHT = 500;
	public final static int NUM_BOMBS = 20; //number of bombs

	public final Image bg = new Image("images/background.jpg",500,500,false,false);
	public final static Image icon = new Image("images/icon.png");

	public GameStage() {
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.WHITE);
		this.scene.setFill(Color.BLACK);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.flag = new Element(Element.FLAG_TYPE,this);
		this.map = new GridPane();
		this.landCells = new ArrayList<Element>();
		this.gameBoard = new int[GameStage.MAP_NUM_ROWS][GameStage.MAP_NUM_COLS];
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;
		//draw the background to the canvas at location x=0, y=70
		this.gc.drawImage( this.bg, 0, 70);

		this.initGameBoard();	//initialize game board with 1s and 0s
		this.createMap();

		//set stage elements here
		this.root.getChildren().add(canvas);
		this.root.getChildren().add(map);
		this.root.getChildren().add(this.flag.getImageView());
		this.stage.setTitle("Modified Minesweeper Game");
		this.stage.setScene(this.scene);
		this.stage.getIcons().add(icon); //adds the icon
		this.stage.setResizable(false); //the window cannot be resized
		this.stage.show();
	}

	private void initGameBoard(){
		Random r = new Random();
		int counter = 0; //countwe for the number of bombs
//		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
//			for(int j=0;j<GameStage.MAP_NUM_COLS;j++){
//				this.gameBoard[i][j] = r.nextInt(2);			//randomize 0 or 1; 0-no bomb, 1-bomb
//			}
//		}
		while(counter != NUM_BOMBS) { //if the number of bombs is not yet 20
			int x = r.nextInt(9); //randomize the row
			int y = r.nextInt(9); //randomize the column
			if(this.gameBoard[x][y] != 1) { //if the place has no bomb yet
				this.gameBoard[x][y] = 1; //place a bomb
				counter++; //increment the counter
			}

		}
		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
			for(int j=0;j<GameStage.MAP_NUM_COLS;j++){
				if(this.gameBoard[i][j] != 1) { //if the board has doesnt have a bomb
					this.gameBoard[i][j] = 0; //input 0 or safe
				}
			}
		}


		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
			System.out.println(Arrays.toString(this.gameBoard[i]));//print final board content
		}
	}

	private void createMap(){
		//create 9 land cells
		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
			for(int j=0;j<GameStage.MAP_NUM_COLS;j++){

				// Instantiate land elements
				Element land = new Element(Element.LAND_TYPE,this);
				land.initRowCol(i,j);

				//add each land to the array list landCells
				this.landCells.add(land);
			}
		}

		this.setGridPaneProperties();
		this.setGridPaneContents();
	}

	//method to set size and location of the grid pane
	private void setGridPaneProperties(){
		this.map.setPrefSize(GameStage.MAP_WIDTH, GameStage.MAP_HEIGHT);
		//set the map to x and y location; add border color to see the size of the gridpane/map
//	    this.map.setStyle("-fx-border-color: red ;");
		this.map.setLayoutX(GameStage.WINDOW_WIDTH*0.025);
	    this.map.setLayoutY(GameStage.WINDOW_WIDTH*0.2);
	    this.map.setVgap(5);
	    this.map.setHgap(5);
	}

	//method to add row and column constraints of the grid pane
	private void setGridPaneContents(){

		 //loop that will set the constraints of the elements in the grid pane
	     int counter=0;
	     for(int row=0;row<GameStage.MAP_NUM_ROWS;row++){
	    	 for(int col=0;col<GameStage.MAP_NUM_COLS;col++){
	    		 // map each land's constraints
	    		 GridPane.setConstraints(landCells.get(counter).getImageView(),col,row);
	    		 counter++;
	    	 }
	     }

	   //loop to add each land element to the gridpane/map
	     for(Element landCell: landCells){
	    	 this.map.getChildren().add(landCell.getImageView());
	     }
	}

	boolean isBomb(Element element){
		int i = element.getRow();
		int j = element.getCol();

		//if the row col cell value is equal to 1, cell has bomb
		if(this.gameBoard[i][j] == 1){
			System.out.println(">>>>>>>>>Bomb!");
			return true;
		}

		System.out.println(">>>>>>>>>SAFE!");
		return false;
	}

	public boolean isFlagClicked() {
		return this.flagClicked;
	}

	public void setFlagClicked(boolean value) {
		this.flagClicked = value;
	}

	Stage getStage() {
		return this.stage;
	}

	int numBomb(Element element) {
		//System.out.println(row);
		//System.out.println(col);
		int row = element.getRow();
		int col = element.getCol();
		int bomb = 0; //will store the number of neighbor bombs
		if(row == 0) { //if it is the first row
			if(col == 0) { //if it is first row and first col
				for(int i=row; i != row+2; i++) {
					for(int j=col; j != col+2; j++) {
						if(this.gameBoard[i][j] == 1) {
							bomb++;
						}
					}
				}
			} else{
				if(col == MAP_NUM_COLS - 1) { //if it is first row and col is the last col
					for(int i=row; i != row+2; i++) {
						for(int j=col-1; j != col+1; j++) {
							if(this.gameBoard[i][j] == 1) {
								bomb++;
							}
						}
					}
				} else { //if is first row and the col is nether the first nor last col
					for(int i=row; i != row+2; i++) {
						for(int j=col-1; j != col+2; j++) {
							if(this.gameBoard[i][j] == 1) {
								bomb++;
							}
						}
					}
				}
			}
		} else { //if it is not the first row
			if(row == MAP_NUM_ROWS-1) { //if it id the last row
				if(col == 0) { //if it is the last row and the first col
					for(int i=row-1; i != row+1; i++) {
						for(int j=col; j != col+2; j++) {
							if(this.gameBoard[i][j] == 1) {
								bomb++;
							}
						}
					}
				} else {
					if(col == MAP_NUM_COLS - 1) { //if it is the last row and the last col
						for(int i=row-1; i != row+1; i++) {
							for(int j=col-1; j != col+1; j++) {
								if(this.gameBoard[i][j] == 1) {
									bomb++;
								}
							}
						}
					} else { //if it is the last row and neither the first nor last col
						for(int i=row-1; i != row+1; i++) {
							for(int j=col-1; j != col+2; j++) {
								if(this.gameBoard[i][j] == 1) {
									bomb++;
								}
							}
						}
					}
				}
			} else { //row is not the first nor the last row
				if(col == 0) { //if it is not the first row nor last row and it is the first col
					for(int i=row-1; i != row+2; i++) {
						for(int j=col; j != col+2; j++) {
							if(this.gameBoard[i][j] == 1) {
								bomb++;
							}
						}
					}
				} else {
					if(col == MAP_NUM_COLS - 1) { //if it is not the first nor last row and it is the last col
						for(int i=row-1; i != row+2; i++) {
							for(int j=col-1; j != col+1; j++) {
								if(this.gameBoard[i][j] == 1) {
									bomb++;
								}
							}
						}
					} else {
						for(int i=(row)-1; i != row+2; i++) { //if it is neither the first nor laat row and it is also neither the first or last col
							for(int j=(col)-1; j != col+2; j++) {
								if(this.gameBoard[i][j] == 1) {
									bomb++;
								}
							}
						}
					}
				}
			}
		}
		return bomb; //return the number of neighboring boms
	}


//	void flashGameOver(){
//		PauseTransition transition = new PauseTransition(Duration.seconds(1));
//		transition.play();
//
//		transition.setOnFinished(new EventHandler<ActionEvent>() {
//
//			public void handle(ActionEvent arg0) {
//				GameOverStage gameover = new GameOverStage();
//				stage.setScene(gameover.getScene());
//			}
//		});
//	}
//
//	void flashGameWinner(){
//		PauseTransition transition = new PauseTransition(Duration.seconds(1));
//		transition.play();
//
//		transition.setOnFinished(new EventHandler<ActionEvent>() {
//
//			public void handle(ActionEvent arg0) {
//				GameWinnerStage gamewinner = new GameWinnerStage();
//				stage.setScene(gamewinner.getScene());
//			}
//		});
//	}
//
	void setGameOver(int num){
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				GameOverStage gameover = new GameOverStage(num);
				stage.setScene(gameover.getScene());
			}
		});
	}

}


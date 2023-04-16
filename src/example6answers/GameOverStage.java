package example6answers;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameOverStage {
	private StackPane pane;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;
	public final Image backg = new Image("images/background.jpg",500,500,false,false);

	GameOverStage(int num){
		this.pane = new StackPane();
		this.scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.setProperties(num);


	}

	private void setProperties(int num){
		this.gc.fillRect(0,0,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		Font theFont = Font.font("Cooper Black",FontWeight.BOLD,30);//set font type, style and size
		this.gc.setFont(theFont);
		this.gc.drawImage(backg, 0, 0); //sets the background

		if(num == 0) { //if the player lost
			this.gc.setFill(Color.RED);										//set font color of text
			this.gc.fillText("Game Over! You Lose!", GameStage.WINDOW_WIDTH*0.12, GameStage.WINDOW_HEIGHT*0.3);						//add a hello world to location x=60,y=50
		} else { //if the player won
			this.gc.setFill(Color.LIGHTGOLDENRODYELLOW);										//set font color of text
			this.gc.fillText("Congratulations! You win!", GameStage.WINDOW_WIDTH*0.02, GameStage.WINDOW_HEIGHT*0.3);
		}

		Button exitbtn = new Button("Exit Game");
		this.addEventHandler(exitbtn);
		exitbtn.setTextFill(Color.DARKBLUE); //textcolor on the button
		exitbtn.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null))); //color of the button


		pane.getChildren().add(this.canvas);
		pane.getChildren().add(exitbtn);

	}

	private void addEventHandler(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				System.exit(0);
			}
		});

	}

	Scene getScene(){
		return this.scene;
	}
}

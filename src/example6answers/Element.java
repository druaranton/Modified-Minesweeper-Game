package example6answers;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Element {
	private String type;
	protected Image img;
	protected ImageView imgView;
	protected GameStage gameStage;
	protected int row, col;
	static int flag_count = 0; //counter for the flags
	static int opened_count = 0; //counter for the opened cells

	public final static Image FLAG_IMAGE = new Image("images/flag.gif",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image BOMB_IMAGE = new Image("images/bomb.gif",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image LAND_IMAGE = new Image("images/land.gif",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image ONE_IMAGE = new Image("images/one.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image TWO_IMAGE = new Image("images/two.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image THREE_IMAGE = new Image("images/three.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image FOUR_IMAGE = new Image("images/four.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image FIVE_IMAGE = new Image("images/five.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image SIX_IMAGE = new Image("images/six.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image SEVEN_IMAGE = new Image("images/seven.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image EIGHT_IMAGE = new Image("images/eight.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static int IMAGE_SIZE = 70;

	public final static String FLAG_TYPE = "flag";
	public final static String BOMB_TYPE = "bomb";
	public final static String LAND_TYPE = "land";
	public final static String LAND_FLAG_TYPE = "landToFlag";

	public Element(String type, GameStage gameStage) {
		this.type = type;
		this.gameStage = gameStage;

		// load image depending on the type
		switch(this.type) {
			case Element.FLAG_TYPE: this.img = Element.FLAG_IMAGE; break;
			case Element.LAND_TYPE: this.img = Element.LAND_IMAGE; break;
			case Element.BOMB_TYPE: this.img = Element.BOMB_IMAGE; break;
		}

		this.setImageView();
		this.setMouseHandler();
	}

	protected void loadImage(String filename,int width, int height){
		try{
			this.img = new Image(filename,width,height,false,false);
		} catch(Exception e){}
	}


	String getType(){
		return this.type;
	}

	int getRow() {
		return this.row;
	}

	int getCol() {
		return this.col;
	}


	protected ImageView getImageView(){
		return this.imgView;
	}

	void setType(String type){
		this.type = type;
	}

	void initRowCol(int i, int j) {
		this.row = i;
		this.col = j;
	}

	private void setImageView() {
		// initialize the image view of this element
		this.imgView = new ImageView();
		this.imgView.setImage(this.img);
		this.imgView.setLayoutX(0);
		this.imgView.setLayoutY(0);
		this.imgView.setPreserveRatio(true);

		if(this.type.equals(Element.FLAG_TYPE)) {
			this.imgView.setFitWidth(GameStage.FLAG_WIDTH);
			this.imgView.setFitHeight(GameStage.FLAG_HEIGHT);
		}else {
			this.imgView.setFitWidth(GameStage.CELL_WIDTH);
			this.imgView.setFitHeight(GameStage.CELL_HEIGHT);
		}
	}

	private void setMouseHandler(){
		Element element = this;
		this.imgView.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
                switch(element.getType()) {
	                case Element.FLAG_TYPE: 		// if flag, set flagClicked to true
								                	System.out.println("FLAG clicked!");
								    	            gameStage.setFlagClicked(true);
								    	            break;
	    			case Element.LAND_TYPE:
			    									System.out.println("LAND clicked!");
								    				if(!gameStage.isFlagClicked()) {
								    					if(!gameStage.isBomb(element)) {	// if not a bomb
								    						int neighbor = gameStage.numBomb(element); //gets the number of bomb neighbors
								    						switch(neighbor) {
								    							case 1: //if it only has 1 neighboring bomb
								    								changeImage(element, Element.ONE_IMAGE); //change the image
								    								break;
								    							case 2: //if it has 2 neighboring bombs
								    								changeImage(element, Element.TWO_IMAGE); //change the image
								    								break;
								    							case 3: //if it has 2 neighboring bombs
								    								changeImage(element, Element.THREE_IMAGE); //change the image
								    								break;
								    							case 4: //if it has 2 neighboring bombs
								    								changeImage(element, Element.FOUR_IMAGE); //change the image
								    								break;
								    							case 5: //if it has 2 neighboring bombs
								    								changeImage(element, Element.FIVE_IMAGE); //change the image
								    								break;
								    							case 6: //if it has 2 neighboring bombs
								    								changeImage(element, Element.SIX_IMAGE); //change the image
								    								break;
								    							case 7: //if it has 2 neighboring bombs
								    								changeImage(element, Element.SEVEN_IMAGE); //change the image
								    								break;
								    							case 8: //if it has 2 neighboring bombs
								    								changeImage(element, Element.EIGHT_IMAGE); //change the image
								    								break;
								    							case 0: //if it has no neighboring bombs
								    								clearImage(element); //clears the image
								    								break;

								    						}

								    						System.out.println("NUMBER OF NEIGHBORING BOMBS: " + neighbor);
								    						opened_count++; //increments the counter for the opened cells
								    						if(opened_count == (GameStage.MAX_CELLS - GameStage.NUM_BOMBS)) { //if all the cells without bombs has been opened
								    							gameStage.setGameOver(1); //winner
								    						}

								    					} else {
								    						changeImage(element,Element.BOMB_IMAGE); // if bomb, change image to bomb
								    						gameStage.setGameOver(0); //loser
								    					}
								    	            } else {
								    	            	changeImage(element,Element.FLAG_IMAGE);	// if flag was clicked before hand, change image to flag
								    	            	element.setType(LAND_FLAG_TYPE);			// change type to landToFlag
								    	            	if(gameStage.isBomb(element)) { //if the flag was placed under the bomb
								    	            		flag_count++; //increment the flag count
								    	            		if(flag_count == GameStage.NUM_BOMBS) { //if all the bombs has flags in it
								    	            			gameStage.setGameOver(1); //winner
								    	            		}
								    	            	}
								    	            	gameStage.setFlagClicked(false);	    	// reset flagClicked to false
								    	            }
								    				break;
	    			case Element.LAND_FLAG_TYPE:
								    				changeImage(element,Element.LAND_IMAGE);		// if flag is clicked, change image back to land
							    	            	element.setType(LAND_TYPE);
							    	            	break;
                }
			}	//end of handle()
		});
	}

	private void clearImage(Element element) {
		imgView.setImage(null);
	}

	private void changeImage(Element element, Image image) {
		this.imgView.setImage(image);

	}
}

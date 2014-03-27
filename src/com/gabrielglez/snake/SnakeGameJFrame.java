package com.gabrielglez.snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SnakeGameJFrame extends JFrame{
	
	private static final int MIN_Y = 0;
	private static final int MAX_Y = 50; //22

	private static final int MIN_X = 0;
	private static final int MAX_X = 50; //22
	
	private int y;
	private int x;
	
	private JButton[][] buttons;
	private JPanel playJPanel;
	
	private Body headSnake;
	private JButton easyJButton ;

	private JButton mediumJButton;
	private JButton hardJButton;
	
	private JLabel pointJLabel;
	
	private JPanel buttonsJPanel;
	ArrayList<Body> snakeBody ;
	
	private int fruitY;
	private int fruitX;
	
	private boolean snakeIsUp ;
	private boolean snakeIsDown;
	
	private boolean snakeIsLeft;
	private boolean snakeIsRight;
	
	private boolean snakeEatFruit;
	private boolean levelSelected;
	
	private boolean keyUpUsed = false;
	private boolean keyDownUsed = false;

	private boolean keyLeftUsed = false;
	private boolean keyRightUsed = false;
	
	private int points;
	private Long snakeSpeed;

	private ActionListener buttonsActionListener;	
	
	public void initGame(){
		
		initFrame();
		initComponents();
		
		repaintGrayTable();
		paintSnake();
		
	}
	
	
	public SnakeGameJFrame(){
		
		initGame();
		
		do{
			
			try{

				if( levelSelected == true ){
					
					
					if( isSnakeIsDied() ){

						JOptionPane.showMessageDialog(this, "You L O S E !!!! hahahahahhaa!!");
						initGame();

					}
				
					exchangeSnakeBodyValues();
					
					
					if( snakeIsUp == true ){
						y--;
						up();
					}
					
					if( snakeIsDown  == true ){
						y++;
						down();
					}
					
					if( snakeIsRight == true ) {
						x++;
						right();
					}
					
					if( snakeIsLeft == true ){
						x--;
						left();
					}
					
					
					repaintGrayTable();
					paintSnake();
					
				}
				else{
					pointJLabel.setText("Select a level!");
				}
				
				Thread.sleep(snakeSpeed);
				
			}catch(Exception ex){
				
				System.out.println("Problem !!! " + ex.toString());
			
			}
			
		}while ( true );
		
	}
	

	private void initComponents() {
		
		y = 10;
		x = 10;
		
		snakeIsUp = false;
		snakeIsDown = false;
		
		snakeIsLeft = false;
		snakeIsRight = false;
		
		snakeEatFruit = false;
		levelSelected = false;
		
		points = 0;
		snakeSpeed = 250L;
		
		snakeBody = new ArrayList<Body>();
		buttons = new JButton[MAX_Y][MAX_X];
		
		playJPanel = new JPanel(new GridLayout(MAX_Y,MAX_X , 1, 1));
		playJPanel.setFocusable(true);
		
		playJPanel.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
					
				if( e.getKeyCode() == KeyEvent.VK_DOWN){
					
					if( keyDownUsed == false){
					
						snakeIsDown  = true;
						snakeIsUp    = false;
						snakeIsRight = false;
						snakeIsLeft  = false;
						
						keyDownUsed  = true;
						keyUpUsed    = true;
						keyRightUsed = false;
						keyLeftUsed  = false;
						
					}
				}
				
				if( e.getKeyCode() == KeyEvent.VK_UP){
					
					if ( keyUpUsed == false){
						
						snakeIsDown  = false;
						snakeIsUp    = true;
						snakeIsRight = false;
						snakeIsLeft  = false;
						
						keyUpUsed    = true;
						keyDownUsed  = true;
						keyRightUsed = false;
						keyLeftUsed  = false;
						
					}
				}
				
				if( e.getKeyCode() == KeyEvent.VK_LEFT){
					
					if( keyLeftUsed == false){
					
						snakeIsDown  = false;
						snakeIsUp    = false;
						snakeIsRight = false;
						snakeIsLeft  = true;
						
						keyLeftUsed  = true;
						keyRightUsed = true;
						keyUpUsed    = false;
						keyDownUsed  = false;
						
						
					}
				}
				
				if( e.getKeyCode() == KeyEvent.VK_RIGHT){
					
					if ( keyRightUsed == false){
						
						snakeIsDown  = false;
						snakeIsUp    = false;
						snakeIsRight = true;
						snakeIsLeft  = false;
						
						keyLeftUsed  = true;
						keyRightUsed = true;
						keyUpUsed    = false;
						keyDownUsed  = false;
						
						
					}
				}
			}
		});
		
		
		buttonsActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				levelSelected = true;
				
				if ( e.getSource() == easyJButton ){
					snakeSpeed = 800L;
					disableAndSetFococusableFalseButtons();
				}
				
				else if ( e.getSource() == mediumJButton ){
					snakeSpeed = 350L;
					disableAndSetFococusableFalseButtons();
				}
				
				else if ( e.getSource() == hardJButton ){
					snakeSpeed = 140L;
					disableAndSetFococusableFalseButtons();
				}
				
				pointJLabel.setText("0");
			}
			
		};

		
		easyJButton  = new JButton("EASY");
		easyJButton.addActionListener(buttonsActionListener);
		
		mediumJButton= new JButton("MEDIUM");
		mediumJButton.addActionListener(buttonsActionListener);
		
		hardJButton = new JButton("HARD");
		hardJButton.addActionListener(buttonsActionListener);

		pointJLabel = new JLabel("0");
		pointJLabel.setFocusable(false);
		
		buttonsJPanel = new JPanel( new FlowLayout( FlowLayout.CENTER));
		buttonsJPanel.removeAll();
		buttonsJPanel.add(easyJButton);
		buttonsJPanel.add(mediumJButton);
		buttonsJPanel.add(hardJButton);
		buttonsJPanel.add(pointJLabel);
		
		getContentPane().removeAll();
		getContentPane().add(playJPanel , BorderLayout.CENTER);
		getContentPane().add(buttonsJPanel , BorderLayout.SOUTH);
		
		startTableGame();
		loadFruits();
		
		setVisible(true);
		
	}
	
	
	public void disableAndSetFococusableFalseButtons(){
		
		easyJButton.setEnabled(false);
		easyJButton.setFocusable(false);
		
		mediumJButton.setEnabled(false);
		mediumJButton.setFocusable(false);
		
		hardJButton.setEnabled(false);
		hardJButton.setFocusable(false);
		
	}
	
	
	public void enableButtons(){
		
		easyJButton.setEnabled(true);
		mediumJButton.setEnabled(true);
		hardJButton.setEnabled(true);
	}
	
	
	public boolean isSnakeIsDied(){
		
			int yHead = snakeBody.get(0).getPositionY();
			int xHead = snakeBody.get(0).getPositionX();
			
			for ( int i = 1 ; i < snakeBody.size() ; i++ ){
				
				if( snakeBody.get(i).getPositionY() == yHead && 
					snakeBody.get(i).getPositionX() == xHead    )
					return true;
			}
			
		return false;
		
	}
	
	
	public void loadFruits(){
		
		boolean positionFruitOK = true;
		
		do{
			
			int Y = (int) (Math.random() * 21);
			int X = (int) (Math.random() * 21);
	
			if( isLocationFruitInSnakeBody(Y, X) == false ){
				buttons[Y][X].setBackground(Color.YELLOW);
				positionFruitOK = false;
			}
		
		}while(positionFruitOK == true);
		
		
	}
	
	
	public boolean isLocationFruitInSnakeBody(int y, int x){
		
			for ( int i = 1 ; i < snakeBody.size() ; i++ ){
				
				if( snakeBody.get(i).getPositionY() == y && 
					snakeBody.get(i).getPositionX() == x    )
					return true;
			}
		
		return false;
		
	}
	

	
	public void startTableGame(){

		fillButtonsInGridLayout();

		//Set snakeHead
		Body body = new Body();
		body.setPositionY(10);
		body.setPositionX(10);
		body.setColor(Color.GREEN);
		
		snakeBody.add(body);
		
		headSnake = snakeBody.get(0);
		
		
		//end snakeHead
		
		
		buttons[10][10].setBackground(headSnake.getColor());
		
	}
	

	private void initFrame() {
		
		setTitle("Juego de la Serpiente por Gabriel González");
		setSize(600,600);
		//setResizable(false);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	
	public boolean isHeadSnakeEatFruit(){
		
		int y = snakeBody.get(0).getPositionY();
		int x = snakeBody.get(0).getPositionX();
		
		if( buttons[y][x].getBackground() == Color.YELLOW  ){
			fruitY = y;
			fruitX = x;
			points = points + 10;
			pointJLabel.setText(String.valueOf(points));
			loadFruits();
			return true;
		}
		else{
			return false;
		}

	}
	

	
	public void paintSnake(){
		
			System.out.println("Tamaño snake " + snakeBody.size() );

			int y ,x ;
			
			for (int i = 0 ; i < snakeBody.size() ; i++){
				
				y = snakeBody.get(i).getPositionY();
				x = snakeBody.get(i).getPositionX();
				
				System.out.println("CUERPO " + i +" VALOR Y : " + y + " VALOR X : " + x);
				
				buttons[y][x].setBackground(snakeBody.get(i).getColor());
			
			}

	}
	
	
	public void exchangeSnakeBodyValues(){

		  for ( int i =  snakeBody.size() -1   ; i > 0   ;  i-- ){

		        int actualY = snakeBody.get(i - 1).getPositionY();
		        int actualX = snakeBody.get(i - 1).getPositionX();

		        snakeBody.get( i ).setPositionY(actualY);
		        snakeBody.get( i ).setPositionX(actualX);

		   }
		  
	}
	

	public boolean checkItsSnakeBodyIsInFruitYX(int fruitY , int fruitX){
		
		for ( int i = 0 ; i < snakeBody.size() ; i++ ){
			
			if( snakeBody.get(i).getPositionY() == fruitY && 
				snakeBody.get(i).getPositionX() == fruitX    )
				
				return true;
		}
		
		return false;
	}
	
	
	public void down(){
		
		if( y >= MIN_Y && y <= MAX_Y  ){
		
					snakeBody.get(0).setPositionY(y);
					checkIfHeadEatFruit_checkIsSnakeBodyIsInFruit_AddBodyToSnake();
					
			}else{

				JOptionPane.showMessageDialog(this, "Crash with bottom wall !!!!");
				initGame();
			}
		
	}
		
	
	
	
	public void up(){
		
		if( y >= MIN_Y && y <= MAX_Y ){
		
			snakeBody.get(0).setPositionY(y);
			checkIfHeadEatFruit_checkIsSnakeBodyIsInFruit_AddBodyToSnake();
			
		}else{
	
			JOptionPane.showMessageDialog(this, "Crash with top wall !!!!");
			initGame();
		}
	}
	
	
	public void left(){
		
		if( x >= MIN_X && x <= MAX_X ){
			
			snakeBody.get(0).setPositionX(x);
			checkIfHeadEatFruit_checkIsSnakeBodyIsInFruit_AddBodyToSnake();
		
		}else{
	
			JOptionPane.showMessageDialog(this, "Crash with left wall !!!!");
			initGame();
		}
	}
	
	
	public void right(){
		
		if( x > MIN_X && x < MAX_X ){
			
			snakeBody.get(0).setPositionX(x);
			checkIfHeadEatFruit_checkIsSnakeBodyIsInFruit_AddBodyToSnake();
			
		}else{
			
			JOptionPane.showMessageDialog(this, "Crash with right wall !!!!");
			initGame();
		}
	}
	
	
	
	public void checkIfHeadEatFruit_checkIsSnakeBodyIsInFruit_AddBodyToSnake(){
		
		if( isHeadSnakeEatFruit() ){
			snakeEatFruit = true;
		}
		
		if(snakeEatFruit == true ){
		
				if ( checkItsSnakeBodyIsInFruitYX( fruitY , fruitX ) == false ) {
				
				Body body = new Body();
				body.setPositionY(fruitY);
				body.setPositionX(fruitX);
				body.setColor(Color.RED);
				
				snakeBody.add( body );
				snakeEatFruit = false;
				
			}
		}
		
	}
	
	public void fillButtonsInGridLayout(){
		
		for(int i = 0 ; i < MAX_Y ; i++){
				for( int j = 0 ; j < MAX_X ; j++){
					
					JButton button = new JButton();
					button.setBackground(Color.GRAY);
					button.setFocusable(false);
					button.setToolTipText(String.valueOf(i) + "-" + String.valueOf(j));
					buttons[i][j] = button;

					playJPanel.add(button);
				
				}
		}	
	}
	
	
	public void repaintGrayTable(){
		
		for(int i = 0 ; i < MAX_Y ; i++){
			for( int j = 0 ; j < MAX_X ; j++){
				
				if( buttons[i][j].getBackground() != Color.YELLOW )
					buttons[i][j].setBackground(Color.GRAY);;
			}
		}	
	}

}

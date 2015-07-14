package com.my.project.client.gui;

import java.io.Serializable;

import javax.swing.JOptionPane;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.my.project.client.Board;
import com.my.project.client.Color;
import com.my.project.client.Player;
import com.my.project.client.Square;
import com.my.project.client.service.ClientImplementation;
import com.my.project.message.ToClient;

public class Interface extends Composite implements Serializable{


	ClientImplementation clientImplementation;
	public Board board;
	Player originPlayer;
	Player otherPlayer;
	
	
	Label player1Label;
	Label player2Label;
	Label player1Score=new Label("02");
	Label player2Score=new Label("02"); 
	Label turnLable;
	VerticalPanel boardVerticalPanel=new VerticalPanel();
	VerticalPanel scoreVerticalPanel=new VerticalPanel();
	HorizontalPanel horizontalPanel=new HorizontalPanel();
	VerticalPanel verticalPanel=new VerticalPanel();
	
	MenuBar menuBar=new MenuBar();
	

	/**
	 * @wbp.parser.constructor
	 */
	public Interface(){}
	public Interface(ClientImplementation clientImplementation, Player originPlayer, Player otherPlayer) {
		
		this.clientImplementation=clientImplementation;
		this.originPlayer=originPlayer;
		this.originPlayer.setClientImplementation(clientImplementation);
		this.otherPlayer=otherPlayer;
		this.otherPlayer.setClientImplementation(clientImplementation);
		
		this.board=new Board(this.originPlayer,this.otherPlayer);
		this.boardVerticalPanel.add(board);
		
		Image image;
		
		player1Label=new Label(originPlayer.getName());
		this.player1Label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.player1Label.setStyleName("player");
		this.scoreVerticalPanel.add(player1Label);
		image=new Image(Square.Image1);
		image.setSize("45px", "45px");
		image.setStyleName("centerCellImage");
		this.scoreVerticalPanel.add(image);
		this.player1Score.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.player1Score.setStyleName("score");
		this.scoreVerticalPanel.add(player1Score);
		
		player2Label=new Label(otherPlayer.getName());
		this.player2Label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.player2Label.setStyleName("player");
		this.scoreVerticalPanel.add(player2Label);
		image=new Image(Square.Image2);
		image.setSize("45px", "45px");
		image.setStyleName("centerCellImage");
		this.scoreVerticalPanel.add(image);
		this.player2Score.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.player2Score.setStyleName("score");
		this.scoreVerticalPanel.add(player2Score);
		this.scoreVerticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.scoreVerticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		this.turnLable=new Label(originPlayer.getName()+" turn ("+originPlayer.getColor()+")");
		this.turnLable.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.turnLable.setStyleName("turn");
		this.scoreVerticalPanel.add(this.turnLable);
		
		
		this.scoreVerticalPanel.setWidth("175px");
		this.scoreVerticalPanel.setStyleName("scoreVerticalPanel");
		
		this.horizontalPanel.add(boardVerticalPanel);
		this.horizontalPanel.add(scoreVerticalPanel);
		
		this.menuBar.setStyleName("menuBar",true);
		menuBar.addItem("NewGame", new NewGameCMD());
		MenuBar option=new MenuBar();
		option.addItem(" Change board color ", new ChangeBoardColorCMD());
		menuBar.addItem("Option",option);
		
		MenuBar Help=new MenuBar();
		Help.addItem(" knowing the rules of the game ", new HelpCMD());
		menuBar.addItem("Help",Help);
		
		this.verticalPanel.add(menuBar);
		this.verticalPanel.add(horizontalPanel);
		
		initWidget(verticalPanel);
		
	}
	class NewGameCMD implements Command {

		
		
		@Override
		public void execute() {
			showDialog();
		}
		private void showDialog() {
			Button okButton = new Button("OK");
			final RadioButton againstComputer=new RadioButton("gameType");
			final RadioButton towPlayer=new RadioButton("gameType");
			
			final TextBox nameField = new TextBox();
			
			nameField.setText("Player1");
			nameField.setFocus(true);
			nameField.selectAll();
			final DialogBox dialogBox = new DialogBox();
			dialogBox.setText("New Game");
			dialogBox.setAnimationEnabled(true);
			
			VerticalPanel dialogVPanel = new VerticalPanel();
			HorizontalPanel radioHorizontalPanel=new HorizontalPanel();
			//String style="'margin-top:150px;margin-bottom:15px;margin-right:5px;margin-left:5px;'";
			dialogVPanel.add(new HTML("<b >Enter your name:</b>"));
			dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
			dialogVPanel.add(nameField);
			dialogVPanel.add(new HTML("<b>choose gmae type:</b>"));
			radioHorizontalPanel.add(againstComputer);
			radioHorizontalPanel.add(new HTML("Against Computer"));
			dialogVPanel.add(radioHorizontalPanel);
			radioHorizontalPanel=new HorizontalPanel();
			radioHorizontalPanel.add(towPlayer);
			radioHorizontalPanel.add(new HTML("Tow Player"));
			dialogVPanel.add(radioHorizontalPanel);
			dialogVPanel.add(okButton);
			dialogBox.setWidget(dialogVPanel);
			
			// Add a handler to close the DialogBox
			okButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					dialogBox.hide();
					if(againstComputer.getValue())
					   originPlayer.startGame(nameField.getText(),1);
					if(towPlayer.getValue())
						originPlayer.startGame(nameField.getText(),0);
								
				}
			});
			
			dialogBox.center();
		}
		
	}
	

class HelpCMD implements Command {
	@Override
		public void execute() {
			showDialog();
		}
		private void showDialog() {
			
		Button okButton = new Button("OK");
		
		final TextBox nameField = new TextBox();
		
		nameField.setText("Player1");
		nameField.setFocus(true);
		nameField.selectAll();
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("New Game");
		dialogBox.setAnimationEnabled(true);
		
		VerticalPanel dialogVPanel = new VerticalPanel();
		HorizontalPanel radioHorizontalPanel=new HorizontalPanel();
		dialogVPanel.add(new HTML("<b >About Smart Reversi :</b>"));							
		dialogVPanel.add(new HTML("<b4>Smart Reversi is a computer version of the classic strategy game Othello,:</b4>"));
		dialogVPanel.add(new HTML("<b4>that involves quick score changes and long-range thinking:</b4>"));
		dialogVPanel.add(new HTML("<b4>Thank you for choosing Smart Reversi! :</b4>"));
		dialogVPanel.add(new HTML("<b4>We sincerely hope you enjoy our game Related Topics :</b4>"));
		dialogVPanel.add(new HTML("<b>Reversi Game Rules:</b>"));
		dialogVPanel.add(new HTML("<b2>The Goal of the Game:</b2>"));		
		dialogVPanel.add(new HTML("<b4>The winner is the player who has more discs of his colour than his opponent at the end of the game:</b4>"));
		dialogVPanel.add(new HTML("<b4>This will happen when neither of the two players has a legal move. :</b4>"));
		dialogVPanel.add(new HTML("<b2>Starting Position:</b2>"));
		dialogVPanel.add(new HTML("<b4>At the beginning of the game, four discs (two of one player and two of another) are placed in:</b4>"));
		dialogVPanel.add(new HTML("<b4> the centre 2x2 square of the board. :</b4>"));
		dialogVPanel.add(new HTML("<b>Making a Move:</b>"));
		dialogVPanel.add(new HTML("<b4>At his turn, a player must place a disc of his colour on one of the empty squares of the board, adjacent to an opponent's disc.:</b4>"));
		dialogVPanel.add(new HTML("<b4>In addition, by placing his disc, he must flank one or several of his opponent's discs between the disc played and another disc :</b4>"));
		dialogVPanel.add(new HTML("<b4>of his own colour already on the board.:</b4>"));
		dialogVPanel.add(new HTML("<b4>He then flips to his colour all the discs which were flanked. The discs are neither removed from the board nor :</b4>"));
		dialogVPanel.add(new HTML("<b4>moved from one square to another:</b4>"));
		dialogVPanel.add(new HTML("<b4>If, at your turn, you may not make a move to flip at least one opponent's disc according to these rules,:</b4>"));
		dialogVPanel.add(new HTML("<b4> you must pass your turn and it's once again your opponent's turn to play. But if a move is possible, you must play it. :</b4>"));
		dialogVPanel.add(new HTML("<b>End of the Game:</b>"));
		dialogVPanel.add(new HTML("<b4>The game is over when neither of the two players has a legal move.:</b4>"));
		dialogVPanel.add(new HTML("<b4>Generally, this happens when all 64 squares are occupied. However, it is possible that some empty squares will remain where :</b4>"));
		dialogVPanel.add(new HTML("<b4>neither player may move:</b4>"));
		dialogVPanel.add(new HTML("<b4>In this case, we count discs to determine the final score. :</b4>"));
		dialogVPanel.add(new HTML("<b>To Start a New Game:</b>"));
		dialogVPanel.add(new HTML("<b4>Select New Game:</b4>"));
		dialogVPanel.add(new HTML("<b>To Make a Move:</b>"));
		dialogVPanel.add(new HTML("<b4>Just click the left mouse button on the game board cell where you want to move, :</b4>"));
		dialogVPanel.add(new HTML("<b4>or use the arrow keys to place the mouse pointer in appropriate position and :</b4>"));
		dialogVPanel.add(new HTML("<b4>then press Space bar or Enter key :</b4>"));
		dialogVPanel.add(new HTML("<b>How to Change color board:</b>"));
		dialogVPanel.add(new HTML("<b4>Select menu Options and then choose color type:</b4>"));
		dialogVPanel.add(new HTML("<b4>:</b4>"));
		
		
		
		
		dialogVPanel.add(okButton);
		ScrollPanel scrollPanel =new ScrollPanel(dialogVPanel);
		scrollPanel.scrollToBottom();
		dialogBox.setWidget(scrollPanel);
		
		okButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
							
								
				dialogBox.hide();										
			}
		});
		
		dialogBox.center();
		
		}
		
	}

	class ChangeBoardColorCMD implements Command {
		
		@Override
		public void execute() {
			showDialog();
		}
		private void showDialog() {
			Button okButton = new Button("OK");
			final RadioButton blue=new RadioButton("color");
			final RadioButton green=new RadioButton("color");
			final RadioButton red=new RadioButton("color");
			
			final DialogBox dialogBox = new DialogBox();
			dialogBox.setText("New Game");
			dialogBox.setAnimationEnabled(true);
			
			VerticalPanel dialogVPanel = new VerticalPanel();
			HorizontalPanel radioHorizontalPanel=new HorizontalPanel();
			dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
			dialogVPanel.add(new HTML("<b>choose gmae type:</b>"));
			radioHorizontalPanel.add(blue);
			radioHorizontalPanel.add(new HTML("Blue"));
			dialogVPanel.add(radioHorizontalPanel);
			radioHorizontalPanel=new HorizontalPanel();
			radioHorizontalPanel.add(green);
			radioHorizontalPanel.add(new HTML("Green"));
			dialogVPanel.add(radioHorizontalPanel);
			radioHorizontalPanel=new HorizontalPanel();
			radioHorizontalPanel.add(red);
			radioHorizontalPanel.add(new HTML("Red"));
			dialogVPanel.add(radioHorizontalPanel);
			dialogVPanel.add(okButton);
			dialogBox.setWidget(dialogVPanel);
			
			// Add a handler to close the DialogBox
			okButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					dialogBox.hide();
					if(blue.getValue())
					   Interface.this.board.setStyleName(Color.blue);
					if(green.getValue())
						Interface.this.board.setStyleName(Color.green);
					if(red.getValue())
						Interface.this.board.setStyleName(Color.red);
								
				}
			});
			
			dialogBox.center();
		}

		
	}
	
	public void upDateInterface(ToClient toClient) {
		this.board.currentPlayer=toClient.getCurrentPlayer();
		this.board.originPlayer=toClient.getOriginPlayer();
		this.board.otherPlayer=toClient.getOtherPlayer();
		this.originPlayer=toClient.getOriginPlayer();
		this.otherPlayer=toClient.getOtherPlayer();
		this.upDatePlayerScore();
		this.board.board=toClient.getBoard();
		this.board.upateBoard();
		
		this.turnLable.setText(toClient.getCurrentPlayer().getName()+" turn ("+toClient.getCurrentPlayer().getColor()+")");
		
	}
	private void upDatePlayerScore() {
		this.player1Label.setText(this.originPlayer.getName());
		this.player1Score.setText(Integer.toString(this.originPlayer.getScore()));
		this.player2Label.setText(this.otherPlayer.getName());
		this.player2Score.setText(Integer.toString(this.otherPlayer.getScore()));
	}
	public void startGame(String plaerName, int gameType) {
		this.board.originPlayer.setColor(Square.color1);
		this.board.originPlayer.setScore(2);
		this.board.originPlayer.setName(plaerName);
		this.board.originPlayer.setGameType(gameType);
		this.board.otherPlayer.setName(this.board.originPlayer.getName()+"1");
		this.board.otherPlayer.setColor(Square.color2);
		this.board.otherPlayer.setScore(2);
		this.board.otherPlayer.setGameType(this.board.originPlayer.getGameType());
		this.board.currentPlayer=this.board.originPlayer;
		this.board.originPlayer.setClientImplementation(clientImplementation);
		this.board.otherPlayer.setClientImplementation(clientImplementation);
		this.turnLable.setText(this.board.originPlayer.getName()+" turn ("+this.board.originPlayer.getColor()+")");
		upDatePlayerScore();
		
		this.board.initialBoard();
		
	}
	public void finishGame() {
		System.out.println("finishGame");
		Button okButton = new Button("OK");			
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Game Over");
		dialogBox.setAnimationEnabled(true);
		
	    VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		final HTML result=new HTML("");
		dialogVPanel.add(result);
		dialogVPanel.add(okButton);
		dialogBox.setWidget(dialogVPanel);
		
		if(originPlayer.getScore()>=otherPlayer.getScore())
			result.setHTML("<h1>You Are Win</h1>");
		else
			result.setHTML("<h1>You Are Lose</h1>");
		
		dialogBox.center();
		
		// Add a handler to close the DialogBox
		okButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				originPlayer.startGame(originPlayer.getName(),originPlayer.getGameType());
			}
		});
		
	}

}

package com.my.project.client;

import java.io.Serializable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;

public class Board extends Grid implements Serializable{
	String color=Color.red;
	public final static int squareNumber=8;
	public Square[][] board;
	public Player originPlayer;
	public Player otherPlayer;
	public Player currentPlayer;
	
	public Board(Player originPlayer, Player otherPlayer){
		super(squareNumber,squareNumber);
		
		this.originPlayer=originPlayer;
		this.otherPlayer=otherPlayer;
		this.currentPlayer=this.originPlayer;
		initialBoard();
	}
	
	public void initialBoard() {
		
		setBorderWidth(1);
		setStyleName(color);
		
		HTMLTable.CellFormatter formatter =getCellFormatter();		
		board=new Square[squareNumber][squareNumber];
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board[i].length;j++){
				board[i][j]=new Square();
				board[i][j].setFlag(false);
				
				formatter.setHeight(i, j, "60px");
				formatter.setWidth(i, j, "60px");
			}
		board[squareNumber/2][squareNumber/2].setColor(Square.color1);
		board[squareNumber/2 -1][squareNumber/2 -1].setColor(Square.color1);
		board[squareNumber/2][squareNumber/2].setDiscus(Square.Image1);
		board[squareNumber/2 -1][squareNumber/2 -1].setDiscus(Square.Image1);
		
		board[squareNumber/2][squareNumber/2 -1].setColor(Square.color2);
		board[squareNumber/2 -1][squareNumber/2].setColor(Square.color2);
		board[squareNumber/2][squareNumber/2 -1].setDiscus(Square.Image2);
		board[squareNumber/2 -1][squareNumber/2].setDiscus(Square.Image2);
		
		board[squareNumber/2][squareNumber/2].setFlag(true);
		board[squareNumber/2 -1][squareNumber/2 -1].setFlag(true);
		board[squareNumber/2 -1][squareNumber/2].setFlag(true);
		board[squareNumber/2 ][squareNumber/2 -1].setFlag(true);
		addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				Cell cell = ((Grid)event.getSource()).getCellForEvent(event);
				if(cell==null)
					return;
				currentPlayer.putDiscus(cell.getRowIndex(), cell.getCellIndex(),Board.this);
			}
		});

		upateBoard();
	}

	public void upateBoard() {
		Image image;
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board[i].length;j++){
				if(board[i][j].getDiscus()!=null){
					image=new Image(board[i][j].getDiscus());
					image.setSize("55px", "55px");
					image.setStyleName("centerCellImage");
					setWidget(i, j, image);
				}else{
					setWidget(i, j, new Image());
				}
				
			}
	}
	

}

package com.my.project.message;

import java.io.Serializable;

import com.my.project.client.Player;
import com.my.project.client.Square;

public class Message implements Serializable {
	private Square[][] board;
	private Player originPlayer;
	private Player otherPlayer;
	private Player currentPlayer;
	
	public Player getOriginPlayer() {
		return originPlayer;
	}

	public void setOriginPlayer(Player originPlayer) {
		this.originPlayer = originPlayer;
	}

	public Player getOtherPlayer() {
		return otherPlayer;
	}

	public void setOtherPlayer(Player otherPlayer) {
		this.otherPlayer = otherPlayer;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Square[][] getBoard() {
		return board;
	}

	public void setBoard(Square[][] board) {
		this.board = board;
	}
}

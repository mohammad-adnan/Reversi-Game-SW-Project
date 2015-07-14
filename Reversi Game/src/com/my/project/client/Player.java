package com.my.project.client;

import java.io.Serializable;

import com.my.project.client.gui.Interface;
import com.my.project.client.service.ClientImplementation;

public class Player implements Serializable{
	 private String name;
	 private int ID ;
	 private int score=2;
	 private String color;
	 private int gameType;
	 private ClientImplementation clientImplementation;
	 public void setClientImplementation(ClientImplementation clientImplementation) {
		this.clientImplementation = clientImplementation;
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public void putDiscus(int RowIndex, int culomnIndex, Board board){
		
		if(this.clientImplementation==null)
			System.out.println("clientImplementation null in putdiscus");
		else
		this.clientImplementation.validateActionAndGetNewBoard(RowIndex, culomnIndex, this.gameType);
		
	}

	public void startGame(String playerName, int gameType) {
		this.clientImplementation.startGame(playerName,gameType);
	}

	public void finishGame() {
		this.clientImplementation.finishGame();
	}
}

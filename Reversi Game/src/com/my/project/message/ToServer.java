package com.my.project.message;

import java.io.Serializable;

public class ToServer extends Message implements Serializable{
	private int rowNumber;
    private int culomnNumber;
    private int gameType;
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public int getCulomnNumber() {
		return culomnNumber;
	}
	public void setCulomnNumber(int culomnNumber) {
		this.culomnNumber = culomnNumber;
	}
	
	public int getGameType() {
		return gameType;
	}
	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	void f(){
		System.out.println("knlk");
	}
}

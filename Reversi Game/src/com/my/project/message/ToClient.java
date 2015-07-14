package com.my.project.message;

import java.io.Serializable;

public class ToClient extends Message implements Serializable{
	private boolean changed;
	private boolean gameFinished=false;
	
	public boolean isGameFinished() {
		return gameFinished;
	}

	public void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	void f(){
		System.out.println("knlk");
	}
}

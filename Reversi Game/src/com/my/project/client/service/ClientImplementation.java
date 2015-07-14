package com.my.project.client.service;

import java.io.Serializable;

import javax.security.auth.callback.CallbackHandler;

import com.gargoylesoftware.htmlunit.SgmlPage;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.my.project.client.Player;
import com.my.project.client.Square;
import com.my.project.client.gui.Interface;
import com.my.project.message.Message;
import com.my.project.message.ToClient;
import com.my.project.message.ToServer;

public class ClientImplementation implements ClientInterface,Serializable{

	private ReversiServiceAsync service;
	private Interface userinterface;
	
	public ClientImplementation(){
		this.service=GWT.create(ReversiService.class);
		
		initialGame();
		
	}
	
	private void initialGame() {
		Player originPlayer=new Player() ;
		
		Player otherPlayer=new Player();
		
		originPlayer.setColor(Square.color1);
		originPlayer.setScore(2);
		originPlayer.setName("player1");
		originPlayer.setGameType(1);
		
		otherPlayer.setName(originPlayer.getName()+"1");
		otherPlayer.setColor(Square.color2);
		otherPlayer.setScore(2);
		otherPlayer.setGameType(originPlayer.getGameType());
		
		this.userinterface=new Interface(this,originPlayer,otherPlayer);
	}

	public Interface getuserinterface(){
		return this.userinterface;
	}
	
	@Override
	public void validateActionAndGetNewBoard(int rowNumber, int culomnNumber,int gameType) {
		ToServer message=new ToServer();
		message.setBoard(this.userinterface.board.board);
		message.setCulomnNumber(culomnNumber);
		message.setRowNumber(rowNumber);
		message.setOriginPlayer(this.userinterface.board.originPlayer);
		message.setOtherPlayer(this.userinterface.board.otherPlayer);
		message.setCurrentPlayer(this.userinterface.board.currentPlayer);
		message.getOriginPlayer().setClientImplementation(null);
		message.getOtherPlayer().setClientImplementation(null);
		message.getCurrentPlayer().setClientImplementation(null);
		message.setGameType(gameType);
		this.service.validateActionAndGetNewBoard(message, new DefaultCallBack());
	}
	
	private class DefaultCallBack implements AsyncCallback<Message>{

		@Override
		public void onFailure(Throwable caught) {
			System.out.println("Error server doesn't response");
		}

		@Override
		public void onSuccess(Message message) {
			message.getOriginPlayer().setClientImplementation(ClientImplementation.this);
			message.getOtherPlayer().setClientImplementation(ClientImplementation.this);
			message.getCurrentPlayer().setClientImplementation(ClientImplementation.this);
			
			ToClient toClient=(ToClient) message;
			
			//if(toClient.isChanged()){
				userinterface.upDateInterface(toClient);
				
				//}
			
			if(toClient.isGameFinished())
				finishGame();
			
		}
		
	}

	public void startGame(String plaerName, int gameType) {
		this.userinterface.startGame( plaerName, gameType);
		
	}

	public void finishGame() {System.out.println("onsucc finidh game");
		this.userinterface.finishGame();
	}

}

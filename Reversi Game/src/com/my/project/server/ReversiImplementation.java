package com.my.project.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.my.project.client.Board;
import com.my.project.client.Square;
import com.my.project.client.service.ReversiService;
import com.my.project.message.Message;
import com.my.project.message.ToClient;
import com.my.project.message.ToServer;

public class ReversiImplementation extends RemoteServiceServlet implements
		ReversiService {

	enum Direction{UP,DWON,LEFT,RIGHT,UP_RIGHT,UP_LEFT,DOWN_RIGHT,DOWN_LEFT};
	Square[][] board;
	ToClient toClient=new ToClient();
	
	@Override
	public Message validateActionAndGetNewBoard(Message message) {
		ToServer toServer=(ToServer) message;
		this.board=toServer.getBoard();
		//current player play
		int numberOfchangedDiscus=validateAction(toServer.getRowNumber(),toServer.getCulomnNumber(),toServer.getCurrentPlayer().getColor(),false);
		
		//manage game
		
		if(numberOfchangedDiscus>0){
			calculateScore(numberOfchangedDiscus, toServer);
			
			//check if game finish
			checkIfGameFinish(toServer);
			
			
			if(toServer.getGameType()==1 && !toClient.isGameFinished()){//one player (against computer)
				int numberOfchangedDiscus1=0;
				for(int i=0;i<Board.squareNumber;i++){
					for(int j=0;j<Board.squareNumber;j++){
						numberOfchangedDiscus1=validateAction(i,j,toServer.getOtherPlayer().getColor(),false);
						if(numberOfchangedDiscus1>0)
							break;
					}
					if(numberOfchangedDiscus1>0)
						break;
					
				}
				
				calculateScore(numberOfchangedDiscus1, toServer);
				checkIfGameFinish(toServer);
			}
		}
		//end manage game
		toClient.setBoard(this.board);
		toClient.setCurrentPlayer(toServer.getCurrentPlayer());
		toClient.setOriginPlayer(toServer.getOriginPlayer());
		toClient.setOtherPlayer(toServer.getOtherPlayer());
		toClient.setChanged((numberOfchangedDiscus>0? true:false));
		message=toClient;
		return message;
	
	}
	private void checkIfGameFinish(ToServer toServer) {
		
//		if(toServer.getOriginPlayer().getScore()+toServer.getOtherPlayer().getScore()==Board.squareNumber*Board.squareNumber)
//			toClient.setGameFinished(true);
//		else
//			toClient.setGameFinished(false);
		
		int numberOfchangedDiscus1=0;
		for(int i=0;i<Board.squareNumber;i++){
			for(int j=0;j<Board.squareNumber;j++){
				numberOfchangedDiscus1=validateAction(i,j,toServer.getCurrentPlayer().getColor(),true);
				if(numberOfchangedDiscus1>0)
					break;
			}
			if(numberOfchangedDiscus1>0)
				break;
			
		}
		
		if(numberOfchangedDiscus1>0)
			toClient.setGameFinished(false);
		else
			toClient.setGameFinished(true);
	}
	void calculateScore(int numberOfchangedDiscus,ToServer toServer){
		//determine turn
		if(toServer.getCurrentPlayer()==toServer.getOriginPlayer()){
			//change score
			toServer.getOriginPlayer().setScore(toServer.getOriginPlayer().getScore()+numberOfchangedDiscus+1);
			toServer.getOtherPlayer().setScore(toServer.getOtherPlayer().getScore()-numberOfchangedDiscus);
			//end change score
			//determine turn
			toServer.setCurrentPlayer(toServer.getOtherPlayer());
			}
		else{
			//change score
			toServer.getOtherPlayer().setScore(toServer.getOtherPlayer().getScore()+numberOfchangedDiscus+1);
			toServer.getOriginPlayer().setScore(toServer.getOriginPlayer().getScore()-numberOfchangedDiscus);
			//end change score
			//determine turn
			toServer.setCurrentPlayer(toServer.getOriginPlayer());
			}
	}
	int validateAction(int rowNumber,int culomnNumber,String playerColor,boolean onlyCheck) {
		int discus=0;

		if(!board[rowNumber][culomnNumber].getFlag()){
			discus+=responseAction(Direction.UP,rowNumber,culomnNumber,playerColor,onlyCheck);
			discus+=responseAction(Direction.DWON,rowNumber,culomnNumber,playerColor,onlyCheck);
			discus+=responseAction(Direction.LEFT,rowNumber,culomnNumber,playerColor,onlyCheck);
			discus+=responseAction(Direction.RIGHT,rowNumber,culomnNumber,playerColor,onlyCheck);
			discus+=responseAction(Direction.UP_RIGHT,rowNumber,culomnNumber,playerColor,onlyCheck);
			discus+=responseAction(Direction.UP_LEFT,rowNumber,culomnNumber,playerColor,onlyCheck);
			discus+=responseAction(Direction.DOWN_RIGHT,rowNumber,culomnNumber,playerColor,onlyCheck);
			discus+=responseAction(Direction.DOWN_LEFT,rowNumber,culomnNumber,playerColor,onlyCheck);
		}
		
		return discus;
	}
	
	int responseAction(Direction dirction,int rowNumber,int culomnNumber,String playerColor,boolean onlyCheck){
		
		int xStep = 0;
		int yStep = 0;
		int stepsNumber = 0;
		
		switch (dirction) {
		case UP:
			xStep=0;
			yStep=-1;
			stepsNumber=rowNumber;
			
			break;
		case DWON:
			xStep=0;
			yStep=1;
			stepsNumber=Board.squareNumber-rowNumber-1;
			
			break;
		case LEFT:
			xStep=-1;
			yStep=0;
			stepsNumber=culomnNumber;
			
			break;
		case RIGHT:
			xStep=1;
			yStep=0;
			stepsNumber=Board.squareNumber-culomnNumber-1;
			
			break;
		case UP_LEFT:
			xStep=-1;
			yStep=-1;
			stepsNumber=Math.min(rowNumber, culomnNumber);
			
			break;
		case UP_RIGHT:
			xStep=1;
			yStep=-1;
			stepsNumber=Math.min(rowNumber, Board.squareNumber-culomnNumber-1);
			
			break;
		case DOWN_RIGHT:
			xStep=1;
			yStep=1;
			stepsNumber=Math.min(Board.squareNumber-rowNumber-1, Board.squareNumber-culomnNumber-1);
			
			break;
		case DOWN_LEFT:
			xStep=-1;
			yStep=1;
			stepsNumber=Math.min(Board.squareNumber-rowNumber-1, culomnNumber);
			
			break;
		default:
			System.out.println("Error input in enum");
			break;
		}
		int discus=0;
		for(int x=culomnNumber+xStep,y=rowNumber+yStep,counter =1;counter<=stepsNumber;counter++,x+=xStep,y+=yStep){
			if(!board[y][x].getFlag()){
				discus=0;
				break;
				}
			else
				if( board[y][x].getColor().equals(playerColor))
					break;
			else
				if(!board[y][x].getColor().equals(playerColor)){
					if(counter==stepsNumber){
						discus=0;
						break;
					}
					else
					{
						discus++;
					}
				}
		}
		
		if ((discus > 0) && !onlyCheck) {
			for (int x =culomnNumber , y = rowNumber, counter = 0; counter <= discus; counter++, x += xStep, y += yStep) {
				if (Square.color1.equals(playerColor)) {
					board[y][x].setColor(Square.color1);
					board[y][x].setDiscus(Square.Image1);
				} else {
					board[y][x].setColor(Square.color2);
					board[y][x].setDiscus(Square.Image2);
				}
				board[y][x].setFlag(true);
			}
		}
		return discus;
	}
}

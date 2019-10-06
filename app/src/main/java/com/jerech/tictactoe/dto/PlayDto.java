/**
 * @author jeremiaschaparro@gmail.com 
 *
 */
package com.jerech.tictactoe.dto;


public class PlayDto {

	private String message;
	
	private GameDto currentGame;
	
	

	public PlayDto(String message, GameDto currentGame) {
		super();
		this.message = message;
		this.currentGame = currentGame;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public GameDto getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(GameDto currentGame) {
		this.currentGame = currentGame;
	}
	
	
	
}

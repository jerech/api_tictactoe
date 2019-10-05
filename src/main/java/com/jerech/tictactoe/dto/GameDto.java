/**
 * @author jeremiaschaparro@gmail.com 
 *
 */
package com.jerech.tictactoe.dto;

import java.util.Date;


public class GameDto {

	private String id;
	
	private String createdUser;
	
	private String lastUser;
	
	private String winner;
	
	private String board[];
	
	private Date createdDate;
	
	private Date finishDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String[] getBoard() {
		return board;
	}

	public void setBoard(String[] board) {
		this.board = board;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
	
	
}

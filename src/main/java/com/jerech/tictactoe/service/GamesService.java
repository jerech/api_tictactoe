/**
 * @author jeremiaschaparro@gmail.com 
 *
 */
package com.jerech.tictactoe.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jerech.tictactoe.dto.GameDto;


public interface GamesService {
	 /**
     * Start a game
     *
     * @param userName
     * @return GameDto
     */
	GameDto start(String userName);
	
	
	/**
     * Get one Game 
     *
     * @param id
     * @return GameDto
     */
	GameDto get(String id);
	
	
	/**
     * Get list of Games for user
     *
     * @param userName
     * @return List<GameDto>
     */
	List<GameDto> getAll(String userName);
	
	
	/**
     * Get list of Games for user with pagination
     *
     * @param userName
     * @return Page<GameDto>
     */
	Page<GameDto> getPage(String userName, Integer page, Integer sizePage, String sort, boolean isAsc);
}

/**
 * @author jeremiaschaparro@gmail.com 
 *
 */
package com.jerech.tictactoe.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jerech.tictactoe.model.Game;

public interface GameRepository extends MongoRepository<Game, String>{
	
	List<Game> findByWinner(String winner);

}

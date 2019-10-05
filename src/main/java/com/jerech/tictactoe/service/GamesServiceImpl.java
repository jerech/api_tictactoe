/**
 * @author jeremiaschaparro@gmail.com 
 *
 */
package com.jerech.tictactoe.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jerech.tictactoe.dto.GameDto;
import com.jerech.tictactoe.exception.TicTacToeException;
import com.jerech.tictactoe.model.Game;
import com.jerech.tictactoe.repository.GameRepository;

@Component
public class GamesServiceImpl implements GamesService{
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
    private ModelMapper modelMapper;

	@Override
	public GameDto start(String userName) {

		Game game = new Game();
		game.set_id(new ObjectId());
		game.setCreatedUser(userName); 
		game.setCreatedDate(new Date());
		game.setBoard(createBoard());
		
		gameRepository.save(game);
	
		return convertToDto(game);
	}

	@Override
	public GameDto get(String id) throws TicTacToeException{
		
		GameDto gameDto;
		try {
			Optional<Game> optGame = gameRepository.findById(id);
			gameDto = convertToDto(optGame.get());
		} catch (Exception e) {
			throw new TicTacToeException("Game not found");
		}
	
		return gameDto;
	}

	@Override
	public List<GameDto> getAll(String userName) {
		List<Game> list = gameRepository.findByCreatedUser(userName);
		
		return list.stream()
		          .map(hello -> convertToDto(hello))
		          .collect(Collectors.toList());
	}

	@Override
	public Page<GameDto> getPage(String userName, Integer page, Integer sizePage, String sort, boolean isAsc) {
		Pageable pageable = PageRequest.of(page, sizePage, isAsc?Direction.ASC:Direction.DESC, StringUtils.isEmpty(sort)?"createDate":sort);

		Game game = new Game();
		game.setCreatedUser(userName);
		Example<Game> exampleGame = Example.of(game);
		
		Page<Game> pageGame = gameRepository.findAll(exampleGame, pageable);
		
		Page<GameDto> pageGameDto = pageGame.map(this::convertToDto);
		
		return pageGameDto;
	}
	
	private String[] createBoard() {
		String[] newBoard = new String[9];
		for(int i=0; i < 9; i++) {
			newBoard[i] = "-";
		}
		return newBoard;
	}
	
	private GameDto convertToDto(Game game) {
		GameDto gameDto = modelMapper.map(game, GameDto.class);
		gameDto.setId(game.get_id().toString());
	    return gameDto;
	}

}

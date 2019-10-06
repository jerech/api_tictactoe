/**
 * @author jeremiaschaparro@gmail.com 
 *
 */
package com.jerech.tictactoe.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
import com.jerech.tictactoe.dto.PlayDto;
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
		
		GameDto gameDto = convertToDto(getGame(id));
		return gameDto;
		
	}
	
	private Game getGame(String id) {
		Game game;
		try {
			Optional<Game> optGame = gameRepository.findById(id);
			game = optGame.get();
		} catch (Exception e) {
			throw new TicTacToeException("Game not found");
		}
	
		return game;
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
	
	@Override
	public PlayDto play(String idGame, Integer pos, String mark) {
		Game game = getGame(idGame);
		
		
		if(checkGameOver(game)) {
			throw new TicTacToeException("Game finished");
		}
		
		if(checkWinner(game.getBoard(), mark)) {
			throw new TicTacToeException(mark.equals(Game.USER_MARK) ? "Game finished. You Win!" : "Game finished. Computer Win!");
		}
		
		if(!validPosition(cellsEmpty(game.getBoard()), pos)) {
			throw new TicTacToeException("Selected position not valid");
		}
		
		PlayDto playDto = null;
		
		game.getBoard()[pos] = mark;
		game.setLastUser(mark.equals(Game.USER_MARK)?game.getCreatedUser():"Computer");
		if(checkWinner(game.getBoard(), mark)) {
			game.setWinner(mark.equals(Game.USER_MARK)?game.getCreatedUser():"Computer");
			game.setFinishDate(new Date());
			playDto = new PlayDto(mark.equals(Game.USER_MARK) ? "You Win!" : "Computer Win!", convertToDto(game));
		} else if(checkGameOver(game)) {
			game.setFinishDate(new Date());
			playDto = new PlayDto("Tied game!", convertToDto(game));
		}
		
		gameRepository.save(game);
		return playDto == null ? new PlayDto(mark.equals(Game.USER_MARK) ? "You turn!" : "Computer turn!", convertToDto(game)) : playDto;
	}
	
	@Override
	public PlayDto playComputer(String idGame, String[] board) {
		return play(idGame, generateComputerPosition(cellsEmpty(board)), Game.COMPUTER_MARK);
	}
	
	
	private String[] createBoard() {
		String[] newBoard = new String[9];
		for(int i=0; i < 9; i++) {
			newBoard[i] = "-";
		}
		return newBoard;
	}
	
	private int generateComputerPosition(List<Integer> cellsEmpty) {
		if (cellsEmpty.isEmpty()) {
			throw new TicTacToeException("Game finished");
		}
		Random r = new Random();
		int x = r.nextInt((cellsEmpty.size()-1 - 0) + 1);
		return cellsEmpty.get(x).intValue();
	}
	
	private boolean validPosition(List<Integer> cellsEmpty, Integer pos) {
		for(Integer cellEmpty: cellsEmpty) {
			if(pos.equals(cellEmpty)) {
				return true;
			}
		}
		return false;
	}
	
	private List<Integer> cellsEmpty(String[] board){
		List<Integer> cellsEmpty = new ArrayList<Integer>();
		for (int i = 0; i < board.length; i++) {	
			if("-".equals(board[i]))
				cellsEmpty.add(new Integer(i));
		}
		return cellsEmpty;
	}
	
	private boolean checkGameOver(Game game) {
		
		return cellsEmpty(game.getBoard()).size()==0 || game.getFinishDate() != null || !StringUtils.isEmpty(game.getWinner());
	}
	
	private boolean checkWinner(String[] board, String mark){
		String marks = "";
		for (int i = 0; i < 8; i++) {	
			switch (i) {
			case 0:
				marks = board[0]+board[3]+board[6];
				break;
			case 1:
				marks = board[2]+board[5]+board[8];
				break;
			case 2:
				marks = board[1]+board[4]+board[7];
				break;
			case 3:
				marks = board[0]+board[1]+board[2];
				break;
			case 4:
				marks = board[3]+board[4]+board[5];
				break;
			case 5:
				marks = board[6]+board[7]+board[8];
				break;
			case 6:
				marks = board[0]+board[4]+board[8];
				break;
			case 7:
				marks = board[2]+board[4]+board[6];
				break;
			default:
				break;
			}
			if(marks.equals(mark+mark+mark)) {
				return true;
			}
		}
		return false;
	}
	
	
	private GameDto convertToDto(Game game) {
		GameDto gameDto = modelMapper.map(game, GameDto.class);
		gameDto.setId(game.get_id().toString());
	    return gameDto;
	}

	
	
	

}

/**
 * @author jeremiaschaparro@gmail.com 
 *
 */
package com.jerech.tictactoe.controller;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jerech.tictactoe.dto.GameDto;
import com.jerech.tictactoe.service.GamesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Game Management")
@RestController
@Validated
public class GamesController {
	
	@Autowired
	private GamesService gamesService;

	@ApiOperation(value = "Start Game", response = GameDto.class)
	@RequestMapping(value = "/games/start", method = RequestMethod.POST)
	public ResponseEntity<GameDto> hello(@RequestParam() @NotEmpty(message="User Name not empty") String userName) {
		
		return new ResponseEntity<GameDto>(gamesService.start(userName), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get Game", response = GameDto.class)
	@RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
	public GameDto get(@PathVariable("id") String id) {
		

		return gamesService.get(id);
	}
	
	@ApiOperation(value = "Get All Games for User!", response = GameDto.class)
	@RequestMapping(value = "/games", method = RequestMethod.GET)
	public List<GameDto> getAll(@RequestParam("userName") @NotEmpty(message="User Name not empty") String userName) {
		
		return gamesService.getAll(userName);
	}
	
	@ApiOperation(value = "Get Page Games for User!", response = GameDto.class)
	@RequestMapping(value = "/games/page", method = RequestMethod.GET)
	public Page<GameDto> getPage(@RequestParam("page") @Min(value=0, message="Min value is 0") Integer page,
			@RequestParam("sort") @NotEmpty(message="Field not empty") String sort,
			@RequestParam("isAsc") boolean isAsc) {
		
		return gamesService.getPage(page, sort, isAsc);
	}
	
	
}

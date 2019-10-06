# Api Rest for TicTacToe game
Api Rest developed with Spring Boot that allows to start a tictactoe game, check game data and play with the computer.
## GITHUB
This is a [link to repo](https://github.com/jerech/api_tictactoe) 

## Database
Api rest implements MongoDB to save application data.

## Documentation
The documentation has been implemented with Swagger2. You can see it in  [this link](http://31.220.62.157/api_tictactoe/swagger-ui.html) 

## Health check Api Rest
You can check helth in link](http://31.220.62.157/api_tictactoe/actuator) 

### Installation
Deployment can be done with docker compose.

```sh
$ docker-compose up -d
```
### Run in develop mode Spring Boot
You must previously have access to mongo db and configure the variables in .env
```sh
$ mvn clean install
$ mvn spring-boot:run
```

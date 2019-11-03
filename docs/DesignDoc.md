# PROJECT Design Documentation


## Team Information
* Team name: Team D
* Team members
  * Brandon Chen
  * Giovanni Melchionne
  * Kris Schnupp
  * Johnny Puskar
  * Kyle Collins

## Executive Summary

Web Checkers is an online, multiplayer web app that allows users to play checkers with one another.
Users can sign in with a desired username and play against friends or strangers.

### Purpose
Provide a way for users to play checkers with their friends. The target user base would be all checkers lovers.

### Glossary and Acronyms

| Term | Definition |
|------|------------|
| MVP | Minimum Viable Product |


## Requirements
Allow users to Sign In with a username, without Sign Out functionality.<br>
Allow users to start a game with other online players who are not in a game.<br>
Allow users to play a game with their opponent.<br>
Allow users to record and watch replays of any game.<br>
Allow users to ask for help when they are stuck.<br>

### Definition of MVP
The MVP of the project is to have a basic checkers game with sign in and sign out functionality.

### MVP Features
The features of the MVP will be sign in with a username and sign out.<br>
Start a game with a player who is not in a game.<br>
Resign a game.<br>
Validate the moves.<br>
Submit and pass your turn.<br>

### Roadmap of Enhancements
Allow users to ask for help about available moves.<br>
Allow users to record the moves in the game and replay them.<br>

## Application Domain
![The WebCheckers Domain Model](domain-model-placeholder.png)

The main entities are the board, player, piece, moves, and square.<br>
Pieces belong on the board, and the board is made of squares.<br> 
A Player is represented by a piece and makes moves.<br>
A Piece can either be a regular piece or king piece.<br>
The different types of moves are capture, regular move, and king move.<br>


## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

As a web application, the user interacts with the system using a
browser.  The client-side of the UI is composed of HTML pages with
some minimal CSS for styling the page.  There is also some JavaScript
that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views.
Controllers are built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](web-interface-placeholder.jpg)

After the user connects, the user sees the home page with route "/". After, if he chooses to sign in, he gets sent to the "/signin", seeing the signin.ftl page. After he signs in, he is redirected to the homepage if he inputs a valid name, and if not, he stays on the "/signin" route. If he is signed in, he can choose to start a game which would load the "/game". In a game, the user 

### UI Tier

> _Provide a summary of the Server-side UI tier of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class structure or object diagrams) with some
> details such as critical attributes and methods._

> _You must also provide any dynamic models, such as statechart and
> sequence diagrams, as is relevant to a particular aspect of the design
> that you are describing.  For example, in WebCheckers you might create
> a sequence diagram of the `POST /validateMove` HTTP request processing
> or you might show a statechart diagram if the Game component uses a
> state machine to manage the game._

> _If a dynamic model, such as a statechart describes a feature that is
> not mostly in this tier and cuts across multiple tiers, you can
> consider placing the narrative description of that feature in a
> separate section for describing significant features. Place this after
> you describe the design of the three tiers._


### Application Tier
   Our application has the PlayerLobby and Game.  <br>
 PlayerLobby holds all the Players that are currently logged in and is changed by SignIn and SignOut. <br>
 Game holds the game of checkers 2 players are playing and other necessary information like what happened during a specific turn <br>



### Model Tier
Our Model Tier has Board, Move, Piece, Player, Position, Row, and Space<br>
Board holds the logic for containing a set of Rows and updating specific Spaces with Pieces <br>
Row holds 8 Spaces <br>
Space is a square that has a color, and the Black Tiles can have a Piece <br>
Piece has a Color (Red or White) and a type (Single or King) <br>
Position is the row and space coordinate of a place on the board <br>
Move is 2 positions that represent a starting and ending point of a piece during a move <br>

### Utilities Tier
Our Utilities includes Message, MoveValidator, and ValidationResult
ValidationResult are the enums that MoveValidator returns <br>
MoveValidator is something that takes model components and returns a value for the UI to use to determine what to do with the information given <br>
Message is used to make messages to send to the JavaScript and display on the screen <br>
### Design Improvements
What we Improved: we originally didn't have board hold a list of Rows and instead had it hold a double array of Spaces. <br>
we updated our Board to hold a list of rows that held a list of spaces to better fit Object-Oriented Design.<br>

What we could improve on: <br>
Our code for validating moves is very convoluted and complex and could be nicer and easier to follow.<br>
More simpler methods- specifically RevertTurn() and SubmitTurn() needs variables we have in game that game doesn't use for anything else<br>
We could have combined all of those variables into one class called Turn to simplify these methods<br>

Tedious Checks: some of our methods are very tedious and run a ton of lines of code to check if something is valid, or to get the proper information <br>
These could be simplified so when unit testing they aren't extremely hard to test <br> 

## Testing
The tests that we performed involved unit testing to see if our code would return the 
expected outcome given any input. Also, acceptance testing was done manually to see if 
the logic of the game was correct and to catch any bugs that could occur. 

### Acceptance Testing
Acceptance testing was done manually by someone who did not write the code to test the logic of the 
program. All of our user stories have passed the suite of acceptance criteria tests. We have nto yet tested 
any of the enhancement user stories. Some of the issues we found included random bugs in the game logic such 
as moving backwards when the piece was not a king and being able to end your turn even with an available capture.

### Unit Testing and Code Coverage
Our overall code... 
Some of our unit tests were purposely not tested as they required tedious checks. For exmaple, we 
did not check the case where the game would end if a user no longer had any places to move with his pieces. We thought that 
this was an extreme condition that was hard to test with a unit test, but instead tested it manually for the expected outcome.


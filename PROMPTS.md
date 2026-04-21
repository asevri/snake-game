## Prompt 1:
I'm building a Snake game in Java using Swing. Create a single file called SnakeGame.java. It should have a main method that opens a JFrame window that is 600 by 600 pixels and titled Snake. Inside the frame, add a JPanel subclass called GamePanel. Do not add any game logic yet. Just get the window to open correctly.

Result: file was created and ran as expected without any errors.

Fixes: No fixes needed.

Observation: Nothing unordinary

## Prompt 2:
Now extend SnakeGame.java. Keep it as one file. Add a dark background grid and draw a starting snake that is three segments long near the center of the board, facing right. Each cell should be a 30x30 pixel square. Draw the snake in green and the background in dark gray. Do not add movement yet.

Result: GamePanel class has been updated with the new grid and added green snake as expected, no errors so far.

Fixes: No fixes needed.

Observation: Does this mean the 0,0 is at the center of the grid and not bottom left corner? Can it be changed for easier handling or does it cause more code to handle it?

## Prompt 3:
Make the snake move automatically using a Swing timer that ticks every 150 milliseconds. Add arrow key controls so the player can steer, but don't allow the snake to reverse direction. For now, have the snake wrap around the edges instead of dying. Make sure the panel can receive keyboard input.

Result: GamePanel class has been updated to include movement as expected. No errors so far.

Fixes: No fixes needed.

Observation: It appears most of the changes are happening in the Game Panel alone

## Prompt 4:
Add a food pellet that spawns at a random empty cell. When the snake eats it, grow by one segment and spawn new food. Add collision detection: hitting a wall or the snake's own body should end the game, stop movement, and show a "Game Over" message with the final score. Display the current score in the top-left corner during play. When the game is over, let the player press R to reset everything and play again.

Result: food pellets were added and the scoring worked but after 7 pallets were eaten an 8th one spawned right behind the edge of the screan. 

Fixes: make sure pellets are spawned only within the visible grid.

Observation: Interesting that AI did not know to make the assumption that the player will always need an access to the pallets.

## Prompt 5:
A food pellet spawned outside visible game window. Fix this to make sure pellets are spawned only within the game window. Do not make any other changes.

Result: Fixed the issue of pallets spawning outside of the screen by adding grid limits.

Fixes: Fixed issue from prompt 4, no further issues

Observation: It had to update an IF statement with addition within bounds check to make sure the food does not spawn outside, so somehow for loops made it spawn one above or below. Further investigation needed.
import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    private static final int GOAL = 28;
    private int turnDelay;
    private int score;
    private Snake snake;
    private Apple apple;
    private boolean isGameStopped;

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
        snakeGame.initialize();
    }

    public void initialize(){
        setScreenSize(WIDTH, HEIGHT + 2);
        createGame();
    }

    public void onTurn(int x){
        snake.move(apple);
        if(!apple.isAlive){
            createNewApple();
            score += 5;
            setScore(score);
            turnDelay -= 5;
            setTurnTimer(turnDelay);
        }
        if(!snake.isAlive){
            gameOver();
        }
        if(snake.getLength() > GOAL){
            win();
        }
        drawScene();
    }

    public void onKeyPress(Key key){
        if(key.equals(Key.DOWN)){
            snake.setDirection(Direction.DOWN);
        }
        else if(key.equals(Key.UP)){
            snake.setDirection(Direction.UP);
        }
        else if(key.equals(Key.RIGHT)){
            snake.setDirection(Direction.RIGHT);
        }
        else if(key.equals(Key.LEFT)){
            snake.setDirection(Direction.LEFT);
        }
        else if(key == Key.SPACE && isGameStopped){
            createGame();
        }
    }

    private void createGame(){
        Snake snake = new Snake(WIDTH/2, HEIGHT/2);
        this.snake = snake;
        createNewApple();
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
        score = 0;
        setScore(score);
    }

    private void drawScene(){
        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                setCellValueEx(x, y, Color.LIGHTSTEELBLUE, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createNewApple(){
        int w = getRandomNumber(WIDTH);
        int h = getRandomNumber(HEIGHT);
        apple = new Apple(w, h);
        if(snake.checkCollision(apple)){
            createNewApple();
        }
    }

    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.GOLD, "GAME OVER", Color.AQUA, 34);
    }

    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.AQUA, "VICTORY", Color.GOLD, 34);
    }
}


import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake extends GameObject{
    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;


    private Direction direction = Direction.LEFT;

    public Snake(int x, int y) {
        super(x, y);
        GameObject go1 = new GameObject(x, y);
        GameObject go2 = new GameObject(x + 1, y);
        GameObject go3 = new GameObject(x + 2, y);
        snakeParts.add(go1);
        snakeParts.add(go2);
        snakeParts.add(go3);
    }

    public void draw(Game game){
        for(int i = 0; i < snakeParts.size(); i++){
            if(i == 0 && !isAlive){
                game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.RED, 75);
            }
            else if(i == 0){
                game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.CHOCOLATE, 75);
            }
            else if(!isAlive){
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
            }
            else{
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.CHOCOLATE, 75);
            }
        }
    }

    public void move(Apple apple){
        GameObject gameObject = createNewHead();
        if(gameObject.x < 0 || gameObject.x >= SnakeGame.WIDTH || gameObject.y < 0 || gameObject.y >= SnakeGame.HEIGHT){
            isAlive = false;
        }
        else {
            if(checkCollision(gameObject)){
                isAlive = false;
            }
            else {
                snakeParts.add(0, gameObject);
                if (gameObject.x == apple.x && gameObject.y == apple.y) {
                    apple.isAlive = false;
                } else {
                    removeTail();
                }
            }
        }
    }

    public GameObject createNewHead(){
        if(direction.equals(Direction.DOWN)){
            return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
        }
        else if(direction.equals(Direction.LEFT)){
            return new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
        }
        else if(direction.equals(Direction.RIGHT)){
            return new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
        }
        else {
            return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
        }
    }

    public void removeTail(){
        snakeParts.remove(snakeParts.size()-1);
    }

    public void setDirection(Direction direction) {
        if(direction.equals(Direction.UP) && this.direction.equals(Direction.DOWN)){
            return;
        }
        if(direction.equals(Direction.DOWN) && this.direction.equals(Direction.UP)){
            return;
        }
        if(direction.equals(Direction.LEFT) && this.direction.equals(Direction.RIGHT)){
            return;
        }
        if(direction.equals(Direction.RIGHT) && this.direction.equals(Direction.LEFT)){
            return;
        }
        if((this.direction.equals(Direction.UP) || this.direction.equals(Direction.DOWN)) && snakeParts.get(0).y == snakeParts.get(1).y){
            return;
        }
        if((this.direction.equals(Direction.RIGHT) || this.direction.equals(Direction.LEFT)) && snakeParts.get(0).x == snakeParts.get(1).x){
            return;
        }
        this.direction = direction;
    }

    public boolean checkCollision(GameObject obj){
        boolean isDead = false;
        for(GameObject o : snakeParts){
            if(o.x == obj.x && o.y == obj.y){
                isDead = true;
            }
        }
        return isDead;
    }

    public int getLength(){
        return snakeParts.size();
    }
}

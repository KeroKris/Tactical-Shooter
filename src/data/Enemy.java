package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.quickLoad;
import static helpers.Clock.delta;

import org.newdawn.slick.opengl.Texture;

public class Enemy extends Entity{

    private boolean first;
    private Tile startTile;
    private TileGrid grid;
    private Tile nextTile;
    private Tile myTile;
    private int maxHealth;


    private int directionIndex;

    public Enemy(Texture texture, Tile startTile, int width, int height, float speed, TileGrid grid, int health){
        this.texture = texture;
        this.startTile = startTile;
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.grid = grid;
        this.directionIndex = 0;
        this.myTile = grid.getTile(getCenterX(), getCenterY());
        this.nextTile = grid.getTile((int) (x) +1, (int)(y));
        this.health = health;
        this.maxHealth = health;
        this.first = true;
        this.alive = true;
    }

    public void update(){

        if(first){
            first = false;
        } else	{

            setMyTile(grid.getTile(getCenterX(), getCenterY()));

            switch(directionIndex){
                case 0:
                    if(pathContinues(directionIndex)){
                        x += delta() * speed;  //Move right

                    }else{
                        if(pathContinues(3)){ // go down
                            directionIndex = 3;
                            y += delta() * speed;
                        }else if(pathContinues(1)){ // go up
                            y -= delta() * speed;
                            directionIndex = 1;
                        }
                    }

                    break;
                case 1:
                    if(pathContinues(directionIndex)){
                        y -= delta() * speed;	//Move up

                    }else{
                        if(pathContinues(2)){ // go left
                            x -= delta() * speed;
                            directionIndex = 2;
                        }else if(pathContinues(0)){ // go right
                            x += delta() * speed;
                            directionIndex = 0;
                        }
                    }

                    break;
                case 2:
                    if(pathContinues(directionIndex)){
                        x -= delta() * speed;	//Move left

                    }else{
                        if(pathContinues(1)){ // go up
                            directionIndex = 1;
                            y -= delta() * speed;
                        }else if(pathContinues(3)){ // go down
                            y += delta() * speed;
                            directionIndex = 3;
                        }
                    }

                    break;
                case 3:
                    if(pathContinues(directionIndex)){
                        y += delta() * speed;	//Move down

                    }else{
                        if(pathContinues(0)){ // go right
                            x += delta() * speed;
                            directionIndex = 0;
                        }else if(pathContinues(2)){ // go left
                            x -= delta() * speed;
                            directionIndex = 2;
                        }
                    }
                    break;
            }
        }
    }

    private boolean pathContinues(int dI){
        boolean answer = true;

        switch(dI){
            case 0: // Moving right
                setNextTile(grid.getTile((int) ((x + width +  delta()*speed)), getCenterY()));
                break;
            case 1: // Moving up
                setNextTile(grid.getTile(getCenterX(), (int)((y- delta()*speed))));
                break;
            case 2: // Moving left
                setNextTile(grid.getTile((int) ((x - delta()*speed)), getCenterY()));
                break;
            case 3: // Moving down
                setNextTile(grid.getTile(getCenterX(), (int)((y+height +  delta()*speed))));
                break;
        }

        if(getNextTile().getType() == TileType.Pit){
            die();
        }
        if(!getNextTile().getType().walkable){
            answer = false;
        }
        return answer;
    }

    public void damage(int amount){
        health -= amount;
        if(health <=0) {
            if(isAlive()) die();
        }
    }

    public void draw(){

        drawQuadTex(texture, x, y, width *2, height);
        if(health < maxHealth){
            drawQuadTex(quickLoad("healthBar"), x, y  - 12, TILE_SIZE * health /maxHealth /4*3, 2);
        }
    }

    public Tile getStartTile() {
        return startTile;
    }

    public void setStartTile(Tile startTile) {
        this.startTile = startTile;
    }

    public TileGrid getTileGrid(){
        return grid;
    }

    public void setNextTile(Tile nextTile) {

        this.nextTile = nextTile;
    }

    public Tile getNextTile() {
        return nextTile;
    }


    public void setMyTile(Tile myTile){
        this.myTile = myTile;
    }
    public Tile getMyTile() {
        return myTile;
    }


    public int getCenterX(){
        centerX = (int) (x + (width/2));
        return centerX;
    }

    public int getCenterY(){
        centerY = (int) (y + (height/2));
        return centerY;
    }
}

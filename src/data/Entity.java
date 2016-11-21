package data;

//import static helpers.Clock.delta;
import static helpers.Artist.*;
import static data.TileGrid.*;

import helpers.Clock;
import org.newdawn.slick.opengl.Texture;

public abstract class Entity {

    //Drawing
    protected Texture texture;
    protected int width;
    protected int height;
    protected float angle;

    //Position
    protected float x;
    protected float y;




    protected float xVelocity;
    protected float yVelocity;
    protected float speed;

    protected int centerX;
    protected int centerY;



    //Health
    protected int health;
    protected boolean alive;
    protected float moveX;
    protected float moveY;




    //Movement



    protected float findDistance(Entity e){

        float xDistance = Math.abs(e.getX() - x);
        float yDistance = Math.abs(e.getY() - y);

        float realDistance = (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

        return realDistance;
    }


    protected float findCenterDistance(Entity e){

        float xDistance = Math.abs(e.getCenterX() - this.getCenterX());
        float yDistance = Math.abs(e.getCenterY() - this.getCenterY());

        float realDistance = (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
        return realDistance;
    }



    protected void calculateDirection(Entity t) {
        float totalAllowedMovement = 1.0f;

        float xDistanceFromTarget = Math.abs(t.getCenterX() - this.getCenterX() /*+ target.getWidth()*/);
        float yDistanceFromTarget = Math.abs(t.getCenterY() - this.getCenterY() /*+ target.getHeight()*/);
        float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;

        float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;

        xVelocity = xPercentOfMovement;
        yVelocity = totalAllowedMovement - xPercentOfMovement;




        if (t.getCenterX() < this.getCenterX())
            xVelocity *= -1;
        if (t.getCenterY() < this.getCenterY())
            yVelocity *= -1;

    }

    protected float calculateAngle(Entity target) {

        // tracking mouse for later
        // double angleTemp = Math.atan2((int)Math.floor((HEIGHT- Mouse.getY())
        // - y), (int)Math.floor(Mouse.getX()) - x);

        double angleTemp = Math.atan2(target.getCenterY() - getCenterY(), target.getCenterX() - getCenterX());



        return (float) Math.toDegrees(angleTemp);
    }




    protected void moveTowards(Entity goal){

        //Homing missiles ?
//		 calculateDirection(goal);



//
//			 if (Game.getGrid().getTile((int)moveX, (int)getCenterY()).getType().walkable &&
//					 Game.getGrid().getTile((int)moveX + width, (int) getCenterY()).getType().walkable){
//
//			 }
//			 if (Game.getGrid().getTile((int)getCenterX(), (int)moveY).getType().walkable &&
//					 Game.getGrid().getTile((int)getCenterX(), (int)(moveY) + height).getType().walkable
//					 ){
//
//			 }
//

//
//		if (checkCollision(moveX, moveY, width, height, goal.getX(), goal.getY(), goal.getWidth(),
//				goal.getHeight())) {
////			System.out.println("HIT!");
//		}

        x += xVelocity * speed * Clock.delta();
        y += yVelocity * speed * Clock.delta();

    }




    //===============Getters and Setters===================


    //Drawing

    public Texture getTexture() {
        return texture;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }




    //Position


    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }

    public float getMoveX() {
        return moveX;
    }

    public float getMoveY() {
        return moveY;
    }



    public int getCenterX(){
        centerX = (int) (x + (width/2));
        return centerX;
    }

    public int getCenterY(){
        centerY = (int) (y + (height/2));
        return centerY;
    }


    //Health

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    protected void die(){
        alive = false;
    }

    public void damage(int amount){
        health -= amount;
        if(health <=0) {
            if(isAlive()) die();
        }
    }

}

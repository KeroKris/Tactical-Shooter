package data;

import static helpers.Artist.*;
import static helpers.Clock.delta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

abstract class Tower extends Entity {

    float timeSinceLastShot, firingSpeed, shootAngle, moveAngle;
    int damage, range, id;
    protected Texture baseTexture, cannonTexture;
    @SuppressWarnings("unused")
    protected Tile startTile;
    ArrayList<Projectile> projectiles;
    CopyOnWriteArrayList<Enemy> enemies;
    protected Enemy target;
    boolean targeted;
    private MoveCheckPoint checkPoint;
    Queue<MoveCheckPoint> checkPointQueue;
    float projectileSpeed;
    int projectileSize;
    Texture[] textures;
    protected HitBox hitBox;
    public TowerType type;
    public Movement moveMode;
    private boolean placed;


    Tower(TowerType type, CopyOnWriteArrayList<Enemy> enemies, int id){
        this.textures = type.textures;
        this.width = type.width;
        this.height = type.height;
        this.id = id;
        this.x = Mouse.getX();
        this.y = Mouse.getY();

//		this.hitBox = new HitBox(this);
        this.type = type;

        this.damage = type.damage;
        this.range = type.range;
        this.firingSpeed = type.firingSpeed;
        this.speed = type.speed;
        this.projectileSpeed = type.projectileSpeed;
        this.projectileSize = type.projectileSize;

        this.targeted = false;
        this.setProjectiles(new ArrayList<>());
        this.enemies = enemies;
        this.target = acquireTarget();
        this.shootAngle = 0;
        this.checkPointQueue = new LinkedList<>();
        this.timeSinceLastShot = 0;
    }

    public void update() {

        if(target == null || !( target).isAlive() || !isInRange(target) || target.health < damage * 3/4){
            targeted = false;
        }


        if(!targeted){
            target = acquireTarget();
        }

        timeSinceLastShot += delta();

        if(target != null){
            if (timeSinceLastShot > firingSpeed){
                shoot(target);
            }

            shootAngle = calculateAngle(target);
        }


        //=============MOVING=======================


        if (!checkPointQueue.isEmpty()) {
            if (this.hasCheckPoint()) {
                moveAngle = calculateAngle(checkPointQueue.peek());
                if(!targeted){

                    shootAngle =  moveAngle;
                }
                calculateDirection(checkPointQueue.peek());
                move(checkPointQueue.peek());

            }
        }




    }



    public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
        enemies = newList;
    }

    public void addCheckPoint(MoveCheckPoint checkPoint) {
        checkPointQueue.add(checkPoint);

    }

    public void setCheckPoint(MoveCheckPoint checkPoint) {
        this.checkPoint = checkPoint;

    }

    public MoveCheckPoint getCheckPoint() {
        return checkPoint;
    }

    public Queue<MoveCheckPoint> getMoveCheckPoints() {
        return checkPointQueue;
    }

    protected void move(MoveCheckPoint checkPoint) {
        if (!checkPointReached(checkPoint)) {
            calculateAngle(checkPoint);
            moveTowards(checkPoint);
        } else
            checkPointQueue.poll();

    }

    protected boolean checkPointReached(MoveCheckPoint checkPoint) {

        if (findCenterDistance(checkPoint) < 5)
            return true;

        return false;
    }

    public boolean hasCheckPoint() {
        if ((checkPointQueue.peek() != null))
            return true;

        return false;
    }

    // ================SHOOTING===================================

    protected Enemy acquireTarget() {

        Enemy closest = null;
        float closestDistance = 10000;

        for (Enemy e : enemies) {
            if (isInRange(e) && e.isAlive() && findDistance(e) < closestDistance) {
                closestDistance = findDistance(e); // Don't think I want this.
                // ...Maybe
                closest = e;
            }
        }

        if (closest != null) {
            targeted = true;
        }
        return closest;
    }

    protected boolean isInRange(Entity e) {

        if (findDistance(e) < range) {
            return true;
        }
        return false;
    }

    // protected float findDistance(Entity e){
    //
    //
    // float xDistance = Math.abs(e.getX() - x);
    // float yDistance = Math.abs(e.getY() - y);
    //
    // float realDistance = (float) Math.sqrt(Math.pow(xDistance, 2) +
    // Math.pow(yDistance, 2));
    //
    // return realDistance;
    // }

    protected abstract void shoot(Entity target);

//	int projectileLength = projectileSize, projectileWidth = projectileSize / 4;
//	timeSinceLastShot = 0;
//		projectiles.add(new StandardBullet(quickLoad("bullet"), target, getCenterX() - projectileSize / 2,
//	getCenterY() - projectileSize / 2, projectileWidth, projectileLength, projectileSpeed, damage));


    public float[] getBarrelMouthPosition(){

        double[] testPos = {0,0};

        float tempAngle = shootAngle;
/*		if(shootAngle < 90 && shootAngle <180){
			tempAngle = shootAngle *-1;
			testPos[0] =  Math.cos((tempAngle - 90));
			testPos[1] =  Math.sin((tempAngle - 90));
			System.out.println(tempAngle -90 );
		} else if(shootAngle < 90){
			System.out.println(tempAngle);
		}

		else {
			tempAngle = shootAngle *-1;
			System.out.println(tempAngle  +270);
			testPos[0] = Math.cos((tempAngle + 90));
			testPos[1] = Math.sin((tempAngle + 90));
		}*/
        System.out.println(shootAngle);

        testPos[0] = Math.cos((tempAngle ));
        testPos[1] = Math.sin((tempAngle ));
//		System.out.println("x: "+testPos[0] +  ", y: " + testPos[1]);
        float[] pos = {(int)(getCenterX() + (testPos[0] * width/2)), (int)(getCenterY() - (testPos[1]  * height/2))};



        return pos;


    }




    public void draw(){

//		  for (int i = 0; i < textures.length; i++){
//		  drawQuadTex(textures[i], x, y, width, height);
//		  }
        drawQuadTex(textures[0], x, y, width, height);

        drawQuadTexRot(textures[1], x, y, width, height, shootAngle);




//		drawQuadTex(baseTexture, x, y, width, height);
//		drawQuadTexRot(cannonTexture, x, y, width, height, shootAngle);
    }





    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }






    public int getRange() {
        return range;
    }

    public Enemy getTarget() {
        return target;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }








    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setEnemies(CopyOnWriteArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public int movingMode(){
        return moveMode.moving();
    }

    public void setMoveMode(Movement moveMode) {
        this.moveMode = moveMode;
    }
}

package data;

import static data.ProjectileType.MACHINEGUN;
import static data.ProjectileType.SHOTGUN;
import static data.ProjectileType.STANDARD;
import static helpers.Artist.*;
import static helpers.Clock.delta;

import java.util.concurrent.CopyOnWriteArrayList;

class TowerSoldier extends Tower {

    private Weapon weapon;
    private double moveAngle;
    private static int selectedTowerID = -1;
    private boolean placed = false;

    TowerSoldier(TowerType type, Weapon weapon, CopyOnWriteArrayList<Enemy> enemies, int id) {

        super(type, enemies, id);

        moveMode = new WalkingMode();
        this.weapon = weapon;

        speed = moveMode.moving();
    }

    @Override
    public void update() {

        speed = moveMode.moving();

        if (target == null || !(target).isAlive() || !isInRange(target)) {
            targeted = false;
        }
        if (!targeted) {
            target = acquireTarget();
        }

        timeSinceLastShot += delta();

        if (target != null) {
            if (timeSinceLastShot > firingSpeed) shoot(target);
            shootAngle = calculateAngle(target);
        }

        // =============MOVING=======================
        if (!checkPointQueue.isEmpty()) {
            if (this.hasCheckPoint()) {
                if(!targeted){
                    shootAngle = calculateAngle(checkPointQueue.peek());
                    shootAngle = (float) moveAngle;
                }
                move(checkPointQueue.peek());
            }
        }
    }

    @Override
    protected void move(MoveCheckPoint checkPoint) {
        if (!checkPointReached(checkPoint)) {
            moveTowards(checkPoint);
        } else
            checkPointQueue.poll();
    }

    @Override
    protected void shoot(Entity target) {
        int projectileLength = projectileSize, projectileWidth = projectileSize / 4;
        timeSinceLastShot = 0;

        switch (weapon){
            case shotgun:
                shotgunFire(20);
                break;
            case rifle:
                projectiles.add(new StandardBullet(STANDARD, target, getCenterX() - projectileSize / 2,
                        getCenterY() - projectileSize / 2, projectileWidth, projectileLength, enemies));
                break;
            case machineGun:
                firingSpeed = 0.2f;
                projectiles.add(new StandardBullet(MACHINEGUN, target, getCenterX() - projectileSize / 2,
                        getCenterY() - projectileSize / 2, projectileWidth, projectileLength, enemies));
                break;
            default:
                break;
        }
    }

    private void shotgunFire(int numberOfBullets){
        if (numberOfBullets < 1){
            return;
        }
        projectiles.add(new StandardBullet(ProjectileType.SHOTGUN, target, getCenterX() - projectileSize / 2,
                getCenterY() - projectileSize / 2, projectileSize/4, projectileSize/4, enemies));
        shotgunFire(numberOfBullets -1);
        return;
    }

    @Override
    protected void calculateDirection(Entity t) {
        float totalAllowedMovement = 1.0f;

        float xDistanceFromTarget = Math.abs(t.getCenterX() - this.getCenterX() /*+ target.getWidth()*/);
        float yDistanceFromTarget = Math.abs(t.getCenterY() - this.getCenterY() /*+ target.getHeight()*/);
        float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;

        float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;

        xVelocity = xPercentOfMovement;
        yVelocity = totalAllowedMovement - xPercentOfMovement;

        moveX = getCenterX();
        moveY = getCenterY();

        if (t.getCenterX() < this.getCenterX())
            xVelocity *= -1;
        if (t.getCenterY() < this.getCenterY())
            yVelocity *= -1;

        //calculating shootAngle
        double angleTemp = Math.atan2(yVelocity, xVelocity);

        moveAngle =  Math.toDegrees(angleTemp);
    }

    public static void setSelectedTowerID(int selectedTowerID) {
        TowerSoldier.selectedTowerID = selectedTowerID;
    }

    public static int getSelectedTowerID() {
        return selectedTowerID;
    }

    @Override
    public void draw(){

//		  for (int i = 0; i < textures.length; i++){
//		  drawQuadTex(textures[i], x, y, width, height);
//		  }
//		drawQuadTex(quickLoad("floorTile"), Game.getGrid().getTile((int)getMoveX(),(int) getMoveY()).getX(), Game.getGrid().getTile((int)getMoveX(),(int) getMoveY()).getY(), width, height);


        if (selectedTowerID == id){

            drawQuadTex(textures[0], x, y, width, height);
        }
        drawQuadTexRot(textures[1], x, y, width, height, shootAngle);


//		drawQuadTex(quickLoad("pittile"), getBarrelMouthPosition()[0], getBarrelMouthPosition()[1], width, height);

//		drawQuadTex(baseTexture, x, y, width, height);
//		drawQuadTexRot(cannonTexture, x, y, width, height, shootAngle);
    }



    @Override
    protected void moveTowards(Entity goal){
        calculateDirection(goal);

        moveX += xVelocity * speed * delta();
        moveY += yVelocity * speed * delta();

        if(moveX > getCenterX()) moveX += width/2;
        if(moveX < getCenterX()) moveX -= width/2;

        if(moveY > getCenterY()) moveY += height/2;
        if(moveY < getCenterY()) moveY -= height/2;


        if(!checkCollisionWithTile(this, Game.getGrid().getTile((int)getMoveX(), (int)y))){
            x += xVelocity * speed * delta();
        }
        if(!checkCollisionWithTile(this, Game.getGrid().getTile((int)x, (int)getMoveY()))){
            y += yVelocity * speed * delta();
        }
    }
}

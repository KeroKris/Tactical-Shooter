package data;

import static helpers.Artist.checkCollision;
import static helpers.Artist.checkCollisionWithTile;
import static helpers.Artist.drawQuadTexRot;
import static helpers.Clock.delta;

public abstract class Projectile extends Entity {

    protected int damage;
    Entity target;

    public Projectile(ProjectileType type, Entity target, float x, float y, int width, int height) {
        this.texture = type.texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = type.speed;
        this.damage = type.damage;
        this.target = target;
        this.alive = true;

        this.xVelocity = 0;
        this.yVelocity = 0;
        calculateDirection(target);
        this.angle = calculateAngle(target);
    }

    public void update() {

        if (alive) {
            // Homing Missile
//			 calculateDirection(target);
            moveTowards(target);
            draw();

            if(checkCollisionWithTile(this, Game.getGrid().getTile((int)getMoveX(), (int)getMoveY()))){
                alive = false;
            }

            if (checkCollision(x, y, width, height, target.getX(), target.getY(), target.getWidth(),
                    target.getHeight())) {
                target.damage(damage);
                alive = false;
            }
        }
    }

    public void draw() {
        drawQuadTexRot(texture, x, y, width, height, angle + 90);
    }

    @Override
    protected void calculateDirection(Entity t) {

        float realAccuracy = 300;
        float missAngle = 1/realAccuracy * 100;

        float accuracy =  missAngle * (float) Math.random();
        accuracy -= missAngle/2;

        float totalAllowedMovement = 1.0f;

        float xDistanceFromTarget =  Math.abs((t.getCenterX() - this.getCenterX()));
        float yDistanceFromTarget =  Math.abs((t.getCenterY() - this.getCenterY()));
        float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;

        float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;

        xVelocity = xPercentOfMovement;
//		System.out.println(xPercentOfMovement);
        yVelocity = totalAllowedMovement - xPercentOfMovement;

/*		xVelocity += accuracy;
		yVelocity -= xVelocity - accuracy;*/


        if (t.getCenterX() < this.getCenterX()){
//			System.out.println("Target is to the left");
            xVelocity *= -1;
            yVelocity += accuracy;
        }  else{
            yVelocity += accuracy;
//			System.out.println("Target is to the right");
        }

        if (t.getCenterY() < this.getCenterY()){
//			System.out.println("target is above");
            yVelocity *= -1;
            xVelocity += accuracy;
        }  else{
            xVelocity += accuracy;
//			System.out.println("target is below");
        }

        if(yVelocity > xVelocity){
            xVelocity += accuracy;
//			System.out.println(xVelocity - yVelocity);
        }

//		xVelocity *= accuracy;
//		yVelocity *= accuracy;
    }
}

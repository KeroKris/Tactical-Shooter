package data;

import org.newdawn.slick.opengl.Texture;

import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Artist.checkCollision;
import static helpers.Artist.checkCollisionWithTile;

/**
 * Created by Kristoffer on 2016-09-03.
 */
public class StandardBullet extends Projectile{

    protected CopyOnWriteArrayList<Enemy> enemies;

    public StandardBullet(ProjectileType type, Entity target, float x, float y, int width, int height, CopyOnWriteArrayList enemies) {
        super(type, target, x, y, width, height);
        this.enemies = enemies;
        calculateDirection(target);
    }



    @Override
    public void update() {

        if (alive) {
            // Homing Missile
//            calculateDirection(target);
            moveTowards(target);

            draw();

            if(Game.getGrid().getTile((int)getX(), (int)getY()).getType().solid){
                alive = false;
            }

            enemies.stream().filter(e -> checkCollision(x, y, width, height, e.getX(), e.getY(), e.getWidth(),
                    e.getHeight())).forEach(e -> {
                e.damage(damage);
                alive = false;
            });



        }
    }

}

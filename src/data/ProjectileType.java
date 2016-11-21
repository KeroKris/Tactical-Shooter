package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.quickLoad;

import org.newdawn.slick.opengl.Texture;

public enum ProjectileType {

    STANDARD( quickLoad("bullet"), 300, 200),
    MACHINEGUN(quickLoad("bullet"), 50, 150),
    SHOTGUN(quickLoad("bullet"), 45, 150),
    SLOW( quickLoad("bullet"), 45, 150),
    EXPLOSIVE( quickLoad("bullet"), 95, 150);

    Texture texture;
    int damage;
    int speed;

    ProjectileType(Texture texture, int damage, int speed){
        this.texture = texture;
        this.damage = damage;
        this.speed = speed;
    }

}

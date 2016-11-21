package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.quickLoad;

import org.newdawn.slick.opengl.Texture;

public enum TowerType {

    SOLDIER( new Texture[]{quickLoad("selectedUnit"), quickLoad("soldier")}, 3, 300, TILE_SIZE*3/4, TILE_SIZE*3/4,
            2.99f, 45, 150, 16, ProjectileType.STANDARD),
    TANK(new Texture[]{quickLoad("tankbase"), quickLoad("tankGun")}, 45, 650, TILE_SIZE, TILE_SIZE,
            5, 150, 250, 32, ProjectileType.SLOW),

    ARTILLERY(new Texture[]{quickLoad("cannonBase"), quickLoad("cannonGun")}, 95, 1250, TILE_SIZE *3/2, TILE_SIZE*3/2,
            9, 5, 350, 64, ProjectileType.EXPLOSIVE);


    Texture[] textures;
    int damage, range;
    int width = TILE_SIZE;
    int height = TILE_SIZE;

    float firingSpeed;

    int speed;
    ProjectileType projectileType;
    int projectileSpeed;
    int projectileSize;

    TowerType(Texture[] textures, int damage, int range, int width, int height,
              float firingSpeed, int speed,
              int projectileSpeed, int projectileSize, ProjectileType projectileType){
        this.textures = textures;
        this.damage = damage;
        this.range = range;

        this.width = width;
        this.height = height;

        this.firingSpeed = firingSpeed;
        this.speed = speed;

        this.projectileSpeed = projectileType.speed;
        this.projectileSize = projectileSize;
        this.projectileType = projectileType;
    }
}

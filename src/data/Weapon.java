package data;

/**
 * Created by Kristoffer on 2016-10-10.
 */
public enum Weapon {
    rifle(1), machineGun(2), shotgun(3);


    private final int id;

    Weapon(int id) {
        this.id = id;
    }
}

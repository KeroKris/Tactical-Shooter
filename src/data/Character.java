package data;

import helpers.DatabaseHandler;

/**
 * Character class, made to contain information during session to be written to the database.
 * Also where the Player class will get the information to initialise the different Soldiers.
 *
 * Created by Kristoffer on 2016-09-06.
 */
public class Character {
    private int id;
    private String name;
    private int speed;
    private Weapon weapon;
    private int range;
    private int weaponID;


    public Character(int id, String name, int speed, int range, int weaponID){

        this.id = id;
        this.name = name;
        this.speed = speed;
        this.range = range;
        this.weaponID = weaponID;

        /*
        this.name = DatabaseHandler.getName(id);
        this.speed = Integer.parseInt(DatabaseHandler.getSpeed(id));
        this.range = Integer.parseInt(DatabaseHandler.getRange(id));
        this.weaponID = Integer.parseInt(DatabaseHandler.getWeaponID(id));
*/
    }




    public int getId() {
        return id;
    }

    public String getName() { return name; }

    public int getRange() { return range; }

    public int getSpeed() {
        return speed;
    }

    public int getWeaponID() {
        return weaponID;
    }

}

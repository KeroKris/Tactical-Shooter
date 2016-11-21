package data;

import javafx.geometry.Point2D;

/**
 * Created by Kristoffer on 2016-08-31.
 */
public class HitBox {

    private Point2D topLeftCorner;
    private Point2D topRightCorner;
    private Point2D bottomLeftCorner;
    private Point2D bottomRightCorner;



    HitBox(Entity unit ){

        topLeftCorner.add(unit.getX(), unit.getY() );
        topRightCorner.add(unit.getX() + unit.getWidth(), unit.getY()  );
        bottomLeftCorner.add(unit.getX(), unit.getY() + unit.getHeight() );
        bottomRightCorner.add(unit.getX() + unit.getWidth(), unit.getY());
    }

    HitBox(Entity unit, int angle){


    }


    public boolean collidingHitBox(Entity first, Entity second){



        return false;
    }


}

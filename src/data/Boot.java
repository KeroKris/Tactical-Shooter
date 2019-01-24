package data;

import helpers.*;
import helpers.DatabaseHandler;
import org.lwjgl.opengl.Display;

import helpers.StateManager;

public class Boot {

    private Boot() {

        System.out.println("starting up system...");

        Artist.beginSession();
        DatabaseHandler.databaseSession();

        //Main Game Loop
        while (!Display.isCloseRequested()) {
            Clock.update();
            StateManager.update();
            Display.update();
            Display.sync(60);
        }
        //closes the database connection
        DatabaseHandler.closeDB();
        Display.destroy();
    }
    public static void main(String[] args) {
        new Boot();
    }
}

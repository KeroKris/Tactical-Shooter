package data;

import helpers.*;
import helpers.DatabaseHandler;
import org.lwjgl.opengl.Display;

import helpers.StateManager;

public class Boot {

    private Boot() {

        System.out.println("starting up system...");
        //Call static method in Artist class to initialize OpenGL calls
        Artist.beginSession();
        //Call static method in DatabaseHandler to initialize DB
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

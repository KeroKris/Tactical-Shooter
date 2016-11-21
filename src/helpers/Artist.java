package helpers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;
import java.io.InputStream;
import java.nio.DoubleBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import data.Entity;
import data.Tile;

public class Artist {

    public static final int WIDTH = 1280 + 192, HEIGHT = 960;
    public static final int TILE_SIZE = 64;

    public static void beginSession() {

        Display.setTitle("Game");

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1); // Draws the actual screen in 3d,
        // near and far is 1, -1 for 2d
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    }

    public static boolean checkCollision(float x1, float y1, float width1, float height1, float x2, float y2,
                                         float width2, float height2) {

        if (x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2) return true;

        return false;

    }

    public static boolean checkCollisionWith(Entity mover, Entity target){
        float x1 = mover.getX(), y1 = mover.getY(), width1 = mover.getWidth(), height1 = mover.getHeight();
        float x2 = target.getX(), y2 = target.getY(), width2 = target.getWidth(), height2 = target.getHeight();

        if (x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2) return true;

        return false;
    }

    public static boolean checkCollisionWithTile(Entity mover, Tile tile){
//		float x1 = mover.getMoveX() + mover.getWidth(), y1 = mover.getMoveY(), width1 = mover.getWidth(), height1 = mover.getHeight();
//		float x2 = tile.getX(), y2 = tile.getY(), width2 = tile.getWidth(), height2 = tile.getHeight();
//
////		width1 = TILE_SIZE;
////		height1 = TILE_SIZE;
//		if (x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2 && !tile.getType().walkable) return true;

        if(!tile.getType().walkable) return true;


        return false;
    }


    public static boolean checkCollisionWithTileY(Entity mover, Tile tile){
        float x1 = mover.getMoveX(), y1 = mover.getMoveY(), width1 = mover.getWidth(), height1 = mover.getHeight();
        float x2 = tile.getX(), y2 = tile.getY(), width2 = tile.getWidth(), height2 = tile.getHeight();


        if (x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2 && !tile.getType().walkable) return true;

        if(!tile.getType().walkable) return true;

        return false;
    }


    public static void drawQuad(float x, float y, float width, float height) {
        glBegin(GL_QUADS); // Drawing squares (4 coords)
        glVertex2f(x, y); // Top left corner
        glVertex2f(x + width, y); // Top right corner
        glVertex2f(x + width, y + height); // Bottom right corner
        glVertex2f(x, y + height); // Botton left corner
        glEnd();
    }

    public static void drawQuadTex(Texture tex, float x, float y, float width, float height) {
        tex.bind(); // binds the texture for this drawQuad
        glTranslatef(x, y, 0); // makes the x, y become the local 0, 0
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(1, 0);
        glVertex2f(width, 0);
        glTexCoord2f(1, 1);
        glVertex2f(width, height);
        glTexCoord2f(0, 1);
        glVertex2f(0, height);
        glEnd();
        glLoadIdentity(); // remove to find out what it does

    }

    public static void drawQuadTexRot(Texture tex, float x, float y, float width, float height, float angle) {
        tex.bind(); // binds the texture for this drawQuad
        glTranslatef(x + width / 2, y + height / 2, 0); // makes the x, y become
        // the local 0, 0
        glRotatef(angle, 0, 0, 1);
        glTranslatef(-width / 2, -height / 2, 0);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(1, 0);
        glVertex2f(width, 0);
        glTexCoord2f(1, 1);
        glVertex2f(width, height);
        glTexCoord2f(0, 1);
        glVertex2f(0, height);
        glEnd();
        glLoadIdentity(); // remove to find out what it does

    }




    //Might remove altogether.
    public static void drawDamageText(Texture tex, float x, float y, float width, float height, String text){
        glMatrixMode(GL_PROJECTION);
        glGetDouble(GL_PROJECTION_MATRIX, DoubleBuffer.wrap(new double[16]));
        glLoadIdentity();
        glOrtho(0, 800, 0, 600, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glPushMatrix();
        glLoadIdentity();
        glRasterPos2f(x, y);
        for (int i = 0; i < 10; i++) {
//			glBitmap();
        }


    }

    public static void drawFog(Texture tex, float x, float y, float width, float height){


    }




    public static Texture loadTexture(String path, String fileType) {
        Texture tex = null;
        InputStream in = ResourceLoader.getResourceAsStream(path);
        try {
            tex = TextureLoader.getTexture(fileType, in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tex;

    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static Texture quickLoad(String name) {
        Texture tex = null;
        tex = loadTexture("res/" + name + ".png", "PNG");
        return tex;
    }

}

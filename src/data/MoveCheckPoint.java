package data;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.quickLoad;

import org.newdawn.slick.opengl.Texture;

public class MoveCheckPoint extends Entity{

    //	private int width, height;
    private Texture texture;


    public MoveCheckPoint(float x, float y) {
        this.x = x;
        this.y = y;
        this.texture = quickLoad("bullet");
        this.width = 32;
        this.height = 32;
    }

    public void update(){
        draw();
    }

    private void draw() {
        drawQuadTex(texture, x, y, width, height);

    }
}

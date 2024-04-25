import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

class Restart implements GameObject {
    private int WIDTH = 115;
    private int HEIGHT = 70;
    private Asset asset = new Asset("/images/Restart/restart.png", WIDTH, HEIGHT);
    private Sprite sprite;

    public Restart(double screenWidth, double screenHeight, GraphicsContext ctx) {
        sprite = new Sprite(asset);
        sprite.setPosX(screenWidth / 2 - WIDTH / 2);
        sprite.setPosY(screenHeight - 180);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
    }

    public boolean checkClick(double posX, double posY) {
        return sprite.intersects( new Rectangle2D(posX, posY, 1, 1) );
    }

    public void update(long now) {
    }

    public void render() {
        if (Osama.gameEnded)
            sprite.render();
    }
}
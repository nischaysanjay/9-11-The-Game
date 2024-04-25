import javafx.scene.canvas.GraphicsContext;

class GameOver implements GameObject {
    private int WIDTH = 236;
    private int HEIGHT = 70;
    private Asset asset = new Asset("images/GameOver/GameOver.png", WIDTH, HEIGHT);
    private Sprite sprite;

    public GameOver(double screenWidth, double screenHeight, GraphicsContext ctx) {
        sprite = new Sprite(asset);
        sprite.setPosX(screenWidth / 2 - WIDTH / 2);
        sprite.setPosY(40);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
    }

    public void update(long now) {
    }

    public void render() {
        if (Osama.gameEnded)
            sprite.render();
    }
}
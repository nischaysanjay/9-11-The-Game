import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.text.Font;

class Score implements GameObject {
    private int WIDTH = 108;
    private int HEIGHT = 146;
    private Asset asset = new Asset("/images/score.png", WIDTH, HEIGHT);
    private Sprite sprite;
    private GraphicsContext ctx;

    private int posX = 10;
    private int posY = 50;
    private int tablePosX, tablePosY;

    private double prevActivePipePosY = Osama.activePipes[0].getPosY();

    public Score(double screenWidth, double screenHeight, GraphicsContext ctx) {
        sprite = new Sprite(asset);
        tablePosX = (int) screenWidth / 2 - WIDTH / 2;
        tablePosY = ((int) screenHeight - 112 ) / 2 - HEIGHT / 2;
        sprite.setPosX(tablePosX);
        sprite.setPosY(tablePosY);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);

        posX = (int) screenWidth / 2 - 10;
        posY = 80;

        this.ctx = ctx;
        ctx.setFont(Osama.appFont);
        ctx.setStroke(Osama.appColor);
    }

    public void update(long now) {
        if (Osama.activePipes[0].getPosY() != prevActivePipePosY) {
            Osama.score++;
            prevActivePipePosY = Osama.activePipes[0].getPosY();

            if (Osama.score > Osama.highscore)
                Osama.highscore = Osama.score;
        }
    }

    public void render() {
        if (Osama.gameEnded) {
            sprite.render();
            ctx.setFill(Osama.appColor);
            ctx.setFont(new Font("04b_19", 32));
            ctx.fillText(Osama.score + "", posX + 2, tablePosY + 70);
            ctx.fillText(Osama.highscore + "", posX + 2, tablePosY + 126);
        }

        if (Osama.gameStarted && !Osama.gameEnded) {
            ctx.setFill(Color.WHITE);
            ctx.fillText(Osama.score + "", posX, posY);
            ctx.strokeText(Osama.score + "", posX, posY);
        }
    }
}
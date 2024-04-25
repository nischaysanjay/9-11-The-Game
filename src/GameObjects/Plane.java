import javafx.scene.canvas.GraphicsContext;

class Plane implements GameObject {
    private int WIDTH = 93;
    private int HEIGHT = 24;
    private Asset asset = new Asset("/images/Plane/plane.png", WIDTH, HEIGHT);
    private Sprite sprite;
    private float terminalVel = 8;
    private float shiftMax = 10;
    private float shiftDelta = 0;
    private double screenHeight;

    public Plane(double screenWidth, double screenHeight, GraphicsContext ctx) {
        this.screenHeight = screenHeight;

        sprite = new Sprite(asset);
        sprite.setPosX(screenWidth / 2 - WIDTH / 2);
        sprite.setPosY( Osama.gameEnded? screenHeight - 112 - HEIGHT : (screenHeight - 112) / 2 );
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
    }

    public void jumpHandler() {
        sprite.setVelY(-6);
    }

    public void update(long now) {
        if (!Osama.gameStarted &&!Osama.gameEnded){
            updatePlaneHovering();
        } else if (Osama.gameEnded) {
            updatePlaneFalldown();
        } else if (Osama.gameStarted) {
            if ((sprite.getPosY() + HEIGHT) > (screenHeight - 112) ||
                    sprite.intersects(Osama.activePipes[0]) ||
                    sprite.intersects(Osama.activePipes[1])
            ) {

                Osama.gameStarted = false;
                Osama.gameEnded = true;
            }

            updatePlanePlaying();
        }

        sprite.update();
    }

    public void updatePlaneHovering() {
        double vel = sprite.getVelY();

        if (shiftDelta == 0) {
            sprite.setVelY(0.5);
            shiftDelta += 0.5;
        } else if (shiftDelta > 0) {
            if (vel > 0.1) {
                if (shiftDelta < shiftMax / 2) {
                    float shift = (float) (vel * 1.06);
                    sprite.setVelY(shift);
                    shiftDelta += shift;
                } else {
                    float shift = (float) (vel * 0.8);
                    sprite.setVelY(shift);
                    shiftDelta += shift;
                }
            } else if (vel < 0.1) {
                if (vel > 0) {
                    sprite.setVelY(-0.5);
                } else {
                    float shift = (float) (vel * 1.06);
                    sprite.setVelY(shift);
                    shiftDelta += shift;
                }
            }
        } else if (shiftDelta < 0) {
            if (vel < -0.1) {
                if (shiftDelta > -shiftMax / 2) {
                    float shift = (float) (vel * 1.06);
                    sprite.setVelY(shift);
                    shiftDelta += shift;
                } else {
                    float shift = (float) (vel * 0.8);
                    sprite.setVelY(shift);
                    shiftDelta += shift;
                }
            } else if (vel > -0.1) {
                if (vel < 0) {
                    sprite.setVelY(0.5);
                } else {
                    float shift = (float) (vel * 1.06);
                    sprite.setVelY(shift);
                    shiftDelta += shift;
                }
            }
        }
    }

    public void updatePlanePlaying() {
        double vel = sprite.getVelY();

        if (vel >= terminalVel)
            sprite.setVelY(vel + 0.1);
        else
            sprite.setVelY(vel + 0.22);
    }

    public void updatePlaneFalldown() {
        if (sprite.getPosY() + HEIGHT >= screenHeight - 112) {
            sprite.setVel(0, 0);
            sprite.setPosY(screenHeight - 112 - HEIGHT);
        } else {
            updatePlanePlaying();
        }

    }

    public void render() {
        sprite.render();
    }
}
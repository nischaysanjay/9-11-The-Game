import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Osama extends Application {

    private GraphicsContext ctx;

    private static final long SKIP_TICKS = 1000000000 / 90;
    private long lastUpdate = 0;

    public static Font appFont = Font.loadFont(Objects.requireNonNull(Osama.class.getResource("./fonts/04b_19.ttf")).toExternalForm(), 42);
    public static Color appColor = Color.web("#543847");
    private double width = 450;
    private double height = 600;

    public static Map<String, GameObject> gameObjects = new LinkedHashMap<>();
    private Plane plane;
    private Restart restart;
    public static Sprite[] activePipes;

    public static boolean gameStarted = false;
    public static boolean gameEnded = false;

    public static int score = 0;
    public static int highscore = 0;

    public void start(Stage stage) {
        stage.setTitle("9/11 - The Game");
        stage.getIcons().add(new Image("/images/Icon/icon.png"));
        double minWidth = 365;
        stage.setMinWidth(minWidth);
        double minHeight = 412;
        stage.setMinHeight(minHeight);

        setScene(stage);
        initRender();
        startGameLoop();

        stage.show();
    }

    private void setScene(Stage stage) {
        Pane pane = new Pane();
        Canvas canvas = new Canvas();
        ctx = canvas.getGraphicsContext2D();

        canvas.heightProperty().bind(pane.heightProperty());
        canvas.widthProperty().bind(pane.widthProperty());

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            width = newVal.doubleValue();
            resizeHandler();
        });
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            height = newVal.doubleValue();
            resizeHandler();
        });

        pane.getChildren().addAll(canvas);
        Scene scene = new Scene(pane, width, height);

        setInputHandlers(scene);
        stage.setScene(scene);
    }

    private void setInputHandlers(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE)
                inputHandler(-1, -1);
        });

        scene.setOnMousePressed(e -> inputHandler(e.getX(), e.getY()));
    }

    private void inputHandler(double posX, double posY) {
        if (!gameEnded) {
            plane.jumpHandler();
            gameStarted = true;
        } else if (posX == -1 && posY == -1 || restart.checkClick(posX, posY)) {
            gameStarted = false;
            gameEnded = false;

            Osama.score = 0;
            initRender();
        }
    }

    private void resizeHandler() {
        initRender();
    }

    private void initRender() {
        ctx.clearRect(0, 0, width, height);
        gameObjects.clear();

        gameObjects.put("background",   new Background(width, height, ctx));
        gameObjects.put("pipes",        new Pipes(width, height, ctx));
        gameObjects.put("land",        new Land(width, height, ctx));

        restart = new Restart(width, height, ctx);
        plane = new Plane(width, height, ctx);

        gameObjects.put("plane",        plane);
        gameObjects.put("restart",      restart);
        gameObjects.put("score",        new Score(width, height, ctx));
        gameObjects.put("title",        new Title(width, height, ctx));
        gameObjects.put("gameover",     new GameOver(width, height, ctx));
    }

    private void updateGame(long now) {
        for (GameObject gameObject : gameObjects.values())
            gameObject.update(now);
    }

    private void renderGame() {
        ctx.clearRect(0, 0, width, height);

        for (GameObject gameObject : gameObjects.values())
            gameObject.render();
    }

    private void startGameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                if (now - lastUpdate >= SKIP_TICKS) {
                    updateGame(now);
                    renderGame();
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }
}
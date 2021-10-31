/*
Crystal, Steven, Anthony, Jamie, Emmanuel
 */
package godfight;

import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.awt.Graphics;
import java.util.Random;

public class GodFight extends Application implements EventHandler<KeyEvent>
{    
    public static final int HEIGHT = 600;
    public static final int WIDTH = 1200;
    
    //Hero
    private ImageView heroImageView;
    private ArrayList<Rectangle2D> heroViewPorts = new ArrayList<>();
    private int heroViewportCounter = 0;
    private int heroDX = 0;
    private int heroDY = 0;
    private int heroHealth = 500;
    //Enemy 1 (Dog)
    private ImageView enemy1ImageView;
    private ArrayList<Rectangle2D> enemy1ViewPorts = new ArrayList<>();
    private int enemy1ViewportCounter = 0;
    private int enemy1DX = -3;
    private int enemy1DY = 0;
    private ArrayList<ImageView> enemy1ImageViewArray = new ArrayList<>();
    //Enemy 2 (Goblin)
    private ImageView enemy2ImageView;
    private ArrayList<Rectangle2D> enemy2ViewPorts = new ArrayList<>();
    private int enemy2ViewportCounter = 0;
    private int enemy2DX = -2;
    private int enemy2DY = 0;
    private ArrayList<ImageView> enemy2ImageViewArray = new ArrayList<>();
    //Enemy 3 (Brain)
    private ImageView enemy3ImageView;
    private ArrayList<Rectangle2D> enemy3ViewPorts = new ArrayList<>();
    private int enemy3ViewportCounter = 0;
    private int enemy3DX = -1;
    private int enemy3DY = -1;
    private ArrayList<ImageView> enemy3ImageViewArray = new ArrayList<>();
    //Boss
    private ImageView bossImageView;
    private ArrayList<Rectangle2D> bossViewPorts = new ArrayList<>();
    private int bossViewportCounter = 0;
    private int bossDX = 0;
    private int bossDY = 2;
    private ArrayList<ImageView> bossImageViewArray = new ArrayList<>();
    private int bossHealth = 500;
    boolean bossIsHere = false;
    boolean bossDefeated = false;
    private int bossTime = 0; //Count for when the boss attacks
    //Bullets
    private ArrayList<ImageView> bulletImageViewArray = new ArrayList<>();
    private int bulletDX = -5;
    private int bulletDY = 0;
    private int bulletReload = 30;
    //Bullet2
    private ArrayList<ImageView> bullet2ImageViewArray = new ArrayList<>();
    private int bullet2DX = -6;
    private int bullet2DY = 0;
    private int bullet2Reload = 90;
    //SpecislBullet
    private ArrayList<ImageView> bullet3ImageViewArray = new ArrayList<>();
    private int bullet3DX = -3;
    private int bullet3DY = 0;
    private int bullet3Ammo = 3;
    private int bullet3Reload = 120;
    private Label specialBulletLabel;
    //EBullet
    private ArrayList<ImageView> ebulletImageViewArray = new ArrayList<>();
    private int ebulletDX = 5;
    private int ebulletDY = 0;
    //BossBullet
    private ArrayList<ImageView> bossbulletImageViewArray = new ArrayList<>();
    private int bossbulletDX = 8;
    private int bossbulletDY = 0;
    private int randomBossBullet;
    //Randomiser
    Random random = new Random();
    private int randomEnemy;
    private int randomEnemy2;
    private int randomEnemy3;
    //Explosion
    private ArrayList<ImageView> explosionImageViewArray = new ArrayList<>();
    //Game
    private ImageView background;
    private int score =0;
    private ImageView YouWin;
    private ImageView YouLose;
    private Label scoreLabel;
    private ArrayList<ImageView> hearts;
    private int MAXIMUMHEARTS = 5;
    private int MAXHEALTH = 500;
    private int MAXENEMIES = 11;
    private int timer = 120 * 60 * 1;
    private double seconds;
    private Label timeLabel;
    private Timeline timeline = new Timeline();
    private ImageView pause;
    private ImageView title;
    private ImageView titleBackground;
    private ImageView help;
    private enum STATE{
        PAUSE,
        GAME,
        TITLE
    };
    private STATE State = STATE.TITLE;
    Group root = new Group();
    @Override
    public void start(Stage stage) throws Exception {
        
        
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.initStyle(StageStyle.UTILITY);
        scene.setOnKeyPressed(this); 
        scene.setOnKeyReleased(this);
        stage.setTitle("God Fight");
        stage.setScene(scene);
        stage.show();
        
        
        //Score & Health & Timer & Bckground Display
        background = new ImageView("sprite/desert.png");
        background.setTranslateY(-200);
        background.setTranslateX(0);
        root.getChildren().add(background);
        scoreLabel = new Label("Score: " + score);
        root.getChildren().add(scoreLabel);
        timeLabel = new Label("Time Left: " + seconds + " seconds");
        root.getChildren().add(timeLabel);
        timeLabel.setTranslateY(HEIGHT - 30);
        specialBulletLabel = new Label("Special Bullets Left: " + bullet3Ammo);
        root.getChildren().add(specialBulletLabel);
        specialBulletLabel.setTranslateY(HEIGHT - 45);
        hearts = new ArrayList<>();
        for(int i = 1; i <= MAXIMUMHEARTS; i++){
            Image heartImage = new Image("sprite/SingleHealth.png");
            ImageView heartImageView = new ImageView(heartImage);
            heartImageView.setTranslateY(2);
            double x = WIDTH / 2 + 100 - (4 + heartImage.getWidth()) * i;
            heartImageView.setTranslateX(x);
            hearts.add(heartImageView);
            root.getChildren().add(heartImageView);
        }
        
        //CREATE HERO IMAGEVIEW
        heroImageView = new ImageView(new Image("sprite/goddessSprite.png"));
        heroImageView.setTranslateX(10);
        heroImageView.setTranslateY(HEIGHT/2- heroImageView.getFitHeight()/2);
        heroViewPorts.add(new Rectangle2D(0,0,80,130));
        heroImageView.setViewport(heroViewPorts.get(0));
        root.getChildren().add(heroImageView);


        //Title
        if (State == STATE.TITLE){
            titleBackground = new ImageView("sprite/GF.png");
            root.getChildren().add(titleBackground);
            title = new ImageView("sprite/Title.png");
            title.setTranslateY(HEIGHT * 2/7);
            title.setTranslateX(WIDTH/4 - 30);
            root.getChildren().add(title);
            help = new ImageView("sprite/Help.png");
            help.setTranslateY(HEIGHT * 4/7);
            help.setTranslateX(WIDTH/8);
            root.getChildren().add(help);
        }
        startGameLoop();
        
    }
    public void startGameLoop(){
        //GAME LOOP
        
        timeline.setCycleCount(Animation.INDEFINITE);//Runs Forever
        timeline.setAutoReverse(false);//The Animation does not reverse direction on alternating cycles.
        Duration durationBetweenFrames = Duration.millis(1000 / 120); //Frames per Second
        
        //onFinish
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (State == STATE.GAME){
                    //Removing Title
                    if (root.getChildren().contains(titleBackground)){
                        root.getChildren().remove(titleBackground);
                        root.getChildren().remove(title);
                        root.getChildren().remove(help);
                        
                    }
                    //Removing Pause
                    if (root.getChildren().contains(pause)){
                        root.getChildren().remove(pause);
                    }
                    //Removing Win
                    if (root.getChildren().contains(YouWin) && !bossDefeated){
                        root.getChildren().remove(YouWin);
                    }
                    //Removing Lose
                    if (root.getChildren().contains(YouLose) && heroHealth > 0){
                        root.getChildren().remove(YouLose);
                    }
                    //Time
                    if (timer > 0) {
                        timer--; //Countdown
                        seconds = timer / 120;
                        timeLabel.setText("Time Left: " + seconds + " seconds"); //Display Time
                    }
                    
                    ///Format of Enemies
                    //CREATE ENEMY1
                    if (seconds < 40 && seconds > 20 && enemy1ImageViewArray.size() < 1){
                        enemy1ImageView = new ImageView(new Image("sprite/EnemyDogSprite1.png"));
                        enemy1ImageView.setTranslateX(WIDTH - enemy1ImageView.getFitWidth()/2);
                        randomEnemy = random.nextInt(HEIGHT * 5/7) + (HEIGHT/7);  
                        enemy1ImageView.setTranslateY(randomEnemy - enemy1ImageView.getFitWidth()); //Random
                        enemy1ViewPorts.add(new Rectangle2D(0,0,154,164));
                        enemy1ImageView.setViewport(enemy1ViewPorts.get(0));
                        root.getChildren().add(enemy1ImageView);
                        enemy1ImageViewArray.add(enemy1ImageView);
                    } else if (seconds < 20 && seconds > 0 && enemy1ImageViewArray.size() < 2){
                        enemy1ImageView = new ImageView(new Image("sprite/EnemyDogSprite1.png"));
                        enemy1ImageView.setTranslateX(WIDTH - enemy1ImageView.getFitWidth()/2);
                        randomEnemy = random.nextInt(HEIGHT * 2/3) + (HEIGHT/7);  
                        enemy1ImageView.setTranslateY(randomEnemy - enemy1ImageView.getFitWidth()); //Random
                        enemy1ViewPorts.add(new Rectangle2D(0,0,154,164));
                        enemy1ImageView.setViewport(enemy1ViewPorts.get(0));
                        root.getChildren().add(enemy1ImageView);
                        enemy1ImageViewArray.add(enemy1ImageView);
                    }
        
                    //CREATE ENEMY2
                    if (seconds < 60 && seconds > 50 && enemy2ImageViewArray.size() < 1){
                        enemy2ImageView = new ImageView(new Image("sprite/Enemy2.png"));
                        enemy2ImageView.setTranslateX(WIDTH - enemy2ImageView.getFitWidth()/2);
                        randomEnemy2 = random.nextInt(HEIGHT * 1/3) + HEIGHT/2;
                        enemy2ImageView.setTranslateY(randomEnemy2 - enemy2ImageView.getFitWidth()); //Random
                        enemy2ViewPorts.add(new Rectangle2D(33,13,176,118));
                        enemy2ImageView.setViewport(enemy2ViewPorts.get(0));
                        root.getChildren().add(enemy2ImageView);
                        enemy2ImageViewArray.add(enemy2ImageView);
                    } else if (seconds < 50 && seconds > 40 && enemy2ImageViewArray.size() < 2){
                        enemy2ImageView = new ImageView(new Image("sprite/Enemy2.png"));
                        enemy2ImageView.setTranslateX(WIDTH - enemy2ImageView.getFitWidth()/2);
                        randomEnemy2 = random.nextInt(HEIGHT * 1/3) + HEIGHT/2;
                        enemy2ImageView.setTranslateY(randomEnemy2 - enemy2ImageView.getFitWidth()); //Random
                        enemy2ViewPorts.add(new Rectangle2D(33,13,176,118));
                        enemy2ImageView.setViewport(enemy2ViewPorts.get(0));
                        root.getChildren().add(enemy2ImageView);
                        enemy2ImageViewArray.add(enemy2ImageView);
                    } else if (seconds < 40 && seconds > 30 && enemy2ImageViewArray.size() < 3){
                        enemy2ImageView = new ImageView(new Image("sprite/Enemy2.png"));
                        enemy2ImageView.setTranslateX(WIDTH - enemy2ImageView.getFitWidth()/2);
                        randomEnemy2 = random.nextInt(HEIGHT * 1/3) + HEIGHT/2;
                        enemy2ImageView.setTranslateY(randomEnemy2 - enemy2ImageView.getFitWidth()); //Random
                        enemy2ViewPorts.add(new Rectangle2D(33,13,176,118));
                        enemy2ImageView.setViewport(enemy2ViewPorts.get(0));
                        root.getChildren().add(enemy2ImageView);
                        enemy2ImageViewArray.add(enemy2ImageView);
                    } else if (seconds < 30 && seconds > 0 && enemy2ImageViewArray.size() < 4){
                        enemy2ImageView = new ImageView(new Image("sprite/Enemy2.png"));
                        enemy2ImageView.setTranslateX(WIDTH - enemy2ImageView.getFitWidth()/2);
                        randomEnemy2 = random.nextInt(HEIGHT * 1/3) + HEIGHT/2;
                        enemy2ImageView.setTranslateY(randomEnemy2 - enemy2ImageView.getFitWidth()); //Random
                        enemy2ViewPorts.add(new Rectangle2D(33,13,176,118));
                        enemy2ImageView.setViewport(enemy2ViewPorts.get(0));
                        root.getChildren().add(enemy2ImageView);
                        enemy2ImageViewArray.add(enemy2ImageView);
                    }
        
                    //Create Enemy 3
                    if (seconds < 50 && seconds > 30 && enemy3ImageViewArray.size() < 1){
                        enemy3ImageView = new ImageView(new Image("sprite/EnemyBrainSprite.png"));
                        enemy3ImageView.setTranslateX(WIDTH * 3/4 - enemy3ImageView.getFitWidth()/2);
                        randomEnemy3 = random.nextInt(HEIGHT/3);
                        enemy3ImageView.setTranslateY(randomEnemy3 - enemy3ImageView.getFitHeight()); //R
                        enemy3ViewPorts.add(new Rectangle2D(0,0,103,95));
                        enemy3ImageView.setViewport(enemy3ViewPorts.get(0));
                        root.getChildren().add(enemy3ImageView);
                        enemy3ImageViewArray.add(enemy3ImageView);
                    } else if (seconds < 30 && seconds > 20 && enemy3ImageViewArray.size() < 2){
                        enemy3ImageView = new ImageView(new Image("sprite/EnemyBrainSprite.png"));
                        enemy3ImageView.setTranslateX(WIDTH * 3/4 - enemy3ImageView.getFitWidth()/2);
                        randomEnemy3 = random.nextInt(HEIGHT/3);
                        enemy3ImageView.setTranslateY(randomEnemy3 - enemy3ImageView.getFitHeight()); //R
                        enemy3ViewPorts.add(new Rectangle2D(0,0,103,95));
                        enemy3ImageView.setViewport(enemy3ViewPorts.get(0));
                        root.getChildren().add(enemy3ImageView);
                        enemy3ImageViewArray.add(enemy3ImageView);
                    } else if (seconds < 20 && seconds > 0 && enemy3ImageViewArray.size() < 3){
                        enemy3ImageView = new ImageView(new Image("sprite/EnemyBrainSprite.png"));
                        enemy3ImageView.setTranslateX(WIDTH * 3/4 - enemy3ImageView.getFitWidth()/2);
                        randomEnemy3 = random.nextInt(HEIGHT/3);
                        enemy3ImageView.setTranslateY(randomEnemy3 - enemy3ImageView.getFitHeight()); //R
                        enemy3ViewPorts.add(new Rectangle2D(0,0,103,95));
                        enemy3ImageView.setViewport(enemy3ViewPorts.get(0));
                        root.getChildren().add(enemy3ImageView);
                        enemy3ImageViewArray.add(enemy3ImageView);
                    }
                    
                    //Moving Enemy1
                    for (ImageView enemy1ImageView : enemy1ImageViewArray) {
                        enemy1ImageView.setTranslateX(enemy1ImageView.getTranslateX() + enemy1DX);
                        enemy1ImageView.setTranslateY(enemy1ImageView.getTranslateY() + enemy1DY);

                        if(enemy1ImageView.getTranslateX() < 0 && timer > 0){
                            enemy1ImageView.setTranslateX(WIDTH);
                            randomEnemy = random.nextInt(HEIGHT * 5/7) + (HEIGHT/7);  
                            enemy1ImageView.setTranslateY(randomEnemy - enemy1ImageView.getFitWidth()); //Random
                        }
                    }
                    if (seconds < 10) {
                        if (timer % 150 == 0)
                            enemy1DX--;
                        }
                    //Moving Enemy2
                    for (ImageView enemy2ImageView : enemy2ImageViewArray) {
                        enemy2ImageView.setTranslateX(enemy2ImageView.getTranslateX() + enemy2DX);
                        enemy2ImageView.setTranslateY(enemy2ImageView.getTranslateY() + enemy2DY);
                        if (seconds < 30)
                            enemy2DX = -3;
                        if(enemy2ImageView.getTranslateX() < 0 && timer > 0){
                            enemy2ImageView.setTranslateX(WIDTH);
                            randomEnemy2 = random.nextInt(HEIGHT * 1/3) + HEIGHT/2;
                            enemy2ImageView.setTranslateY(randomEnemy2 - enemy2ImageView.getFitWidth()); //Random
                        }
                    }
                    //Moving Enemy 3    
                    for (ImageView enemy3ImageView : enemy3ImageViewArray) {
                        enemy3ImageView.setTranslateX(enemy3ImageView.getTranslateX() + enemy3DX);
                        enemy3ImageView.setTranslateY(enemy3ImageView.getTranslateY() + enemy3DY);
                        if(enemy3ImageView.getTranslateY() < 0 || enemy3ImageView.getTranslateY() > HEIGHT/2){
                            enemy3DY = -enemy3DY;
                        }
                        if(enemy3ImageView.getTranslateX() < 0 && timer > 0){
                            enemy3ImageView.setTranslateX(WIDTH);
                        }
                    }
                    //Moving Boss
                    if (bossIsHere) {
                        bossImageView.setTranslateX(bossImageView.getTranslateX() + bossDX);
                        bossImageView.setTranslateY(bossImageView.getTranslateY() + bossDY);
                        double height = bossImageView.getViewport().getHeight();
                        bossTime++;
                        if(bossImageView.getTranslateY() < 0 || bossImageView.getTranslateY() > HEIGHT - height){
                            bossDY = -bossDY;
                            bossViewportCounter++;
                        }
                        //Boss EBullets
                        if(bossTime % 75 == 0 && !bossDefeated){ //How often he shoots
                            ImageView ebulletImageView = new ImageView(new Image("sprite/EnemyBullet.png"));
                            Rectangle2D eviewport = new Rectangle2D(5,45,72,81);
                            ebulletImageView.setViewport(eviewport);
                            int ewidth = (int) eviewport.getWidth();
                            ebulletImageView.setTranslateX(WIDTH - ewidth - ewidth);
                            ebulletImageView.setTranslateY((int)heroImageView.getTranslateY());
                            root.getChildren().add(ebulletImageView);
                            ebulletImageViewArray.add(ebulletImageView);
                        }
                        //Boss Boss Bullets
                        if(bossTime % 200 == 0 && !bossDefeated){ //How often he shoots
                            ImageView bossbulletImageView = new ImageView(new Image("sprite/BossHandSprite.png"));
                            Rectangle2D bossviewport = new Rectangle2D(0,0,133,88);
                            bossbulletImageView.setViewport(bossviewport);
                            int bosswidth = (int) bossviewport.getWidth();
                            bossbulletImageView.setTranslateX(WIDTH - bosswidth - bosswidth);
                            randomBossBullet = random.nextInt(HEIGHT); // Random Bullets
                            bossbulletImageView.setTranslateY(randomBossBullet);
                            root.getChildren().add(bossbulletImageView);
                            bossbulletImageViewArray.add(bossbulletImageView);
                        }
                    }
                
                    //Hero Movement
                    heroImageView.setTranslateX(heroImageView.getTranslateX() + heroDX);
                    heroImageView.setTranslateY(heroImageView.getTranslateY() + heroDY);
                    if (heroImageView.getTranslateX() < 0 || heroImageView.getTranslateX() > WIDTH) {
                        heroDX = 0;
                    }
                    if (heroImageView.getTranslateY() < 0 || heroImageView.getTranslateY() > HEIGHT) {
                        heroDY = 0;
                    }
                    if (heroDX != 0 || heroDY != 0) {
                        heroViewportCounter = (++heroViewportCounter) % (heroViewPorts.size());
                        heroImageView.setViewport(heroViewPorts.get(heroViewportCounter));
                    }
                
                    //Moving bullet
                    ArrayList<Integer> outOfViewBullet = new ArrayList<>();
                    bulletReload++;
                    for(int i = 0; i < bulletImageViewArray.size(); i++){
                        ImageView bulletImageView = bulletImageViewArray.get(i);
                        bulletImageView.setTranslateX(bulletImageView.getTranslateX() - bulletDX);
                        bulletImageView.setTranslateY(bulletImageView.getTranslateY() + bulletDY);
                        bulletImageView.setScaleX(1);
                        if(bulletImageView.getTranslateX() < 0){
                            bulletImageView.setVisible(false);
                            outOfViewBullet.add(i);
                        }
                    }
                    for (int i: outOfViewBullet){
                        bulletImageViewArray.remove(i);
                    }
                    //Moving bullet2
                    bullet2Reload++;
                    ArrayList<Integer> outOfViewBullet2 = new ArrayList<>();
                    for(int i = 0; i < bullet2ImageViewArray.size(); i++){
                        ImageView bullet2ImageView = bullet2ImageViewArray.get(i);
                        bullet2ImageView.setTranslateX(bullet2ImageView.getTranslateX() - bullet2DX);
                        bullet2ImageView.setTranslateY(bullet2ImageView.getTranslateY() + bullet2DY);
                        bullet2ImageView.setScaleX(1);
                        if(bullet2ImageView.getTranslateX() < 0){
                            bullet2ImageView.setVisible(false);
                            outOfViewBullet2.add(i);
                        }
                    }
                    for (int i: outOfViewBullet2){
                        bullet2ImageViewArray.remove(i);
                    }
                    //Moving bullet3
                    bullet3Reload++;
                    ArrayList<Integer> outOfViewBullet3 = new ArrayList<>();
                    for(int i = 0; i < bullet3ImageViewArray.size(); i++){
                        ImageView bullet3ImageView = bullet3ImageViewArray.get(i);
                        bullet3ImageView.setTranslateX(bullet3ImageView.getTranslateX() - bullet3DX);
                        bullet3ImageView.setTranslateY(bullet3ImageView.getTranslateY() + bullet3DY);
                        bullet3ImageView.setScaleX(1);
                        if(bullet3ImageView.getTranslateX() < 0){
                            bullet3ImageView.setVisible(false);
                            outOfViewBullet3.add(i);
                        }
                    }
                    for (int i: outOfViewBullet3){
                        bullet3ImageViewArray.remove(i);
                    }
                    //Moving ebullet
                    ArrayList<Integer> outOfViewEBullet = new ArrayList<>();
                    for(int i = 0; i < ebulletImageViewArray.size(); i++){
                        ImageView ebulletImageView = ebulletImageViewArray.get(i);
                        ebulletImageView.setTranslateX(ebulletImageView.getTranslateX() - ebulletDX);
                        ebulletImageView.setTranslateY(ebulletImageView.getTranslateY() + ebulletDY);
                        ebulletImageView.setScaleX(1);
                        if(ebulletImageView.getTranslateX() < 0){
                            ebulletImageView.setVisible(false);
                            outOfViewEBullet.add(i);
                        }
                    }
                    for (int i: outOfViewEBullet){
                        ebulletImageViewArray.remove(i);
                    }
                    //Moving bossbullet
                    ArrayList<Integer> outOfViewBossBullet = new ArrayList<>();
                    for(int i = 0; i < bossbulletImageViewArray.size(); i++){
                        ImageView bossbulletImageView = bossbulletImageViewArray.get(i);
                        bossbulletImageView.setTranslateX(bossbulletImageView.getTranslateX() - bossbulletDX);
                        bossbulletImageView.setTranslateY(bossbulletImageView.getTranslateY() + bossbulletDY);
                        bossbulletImageView.setScaleX(1);
                        if(bossbulletImageView.getTranslateX() < 0){
                            bossbulletImageView.setVisible(false);
                            outOfViewBossBullet.add(i);
                        }
                    }
                    for (int i: outOfViewBossBullet){
                        bossbulletImageViewArray.remove(i);
                    }
                handleCollisions();
                }
            }
        };
        KeyFrame keyframe = new KeyFrame(durationBetweenFrames, onFinished);
        timeline.getKeyFrames().add(keyframe);
        timeline.play();
    }
    @Override
    public void handle(KeyEvent event)
    {
        if(event.getEventType() == KeyEvent.KEY_PRESSED){
            if (State == STATE.GAME){
                heroViewportCounter = ++heroViewportCounter % heroViewPorts.size();
                //Pause
                if(event.getCode() == KeyCode.ENTER && !bossDefeated && heroHealth > 0){
                    pause = new ImageView("sprite/Pause.png");
                    pause.setTranslateY(HEIGHT * 3/7 - 30);
                    pause.setTranslateX(WIDTH/4 + 100);
                    root.getChildren().add(pause);
                    State = STATE.PAUSE;
                }
                //Restart
                if(event.getCode() == KeyCode.SPACE && bossDefeated){
                    titleBackground = new ImageView("sprite/GF.png");
                    root.getChildren().add(titleBackground);
                    title = new ImageView("sprite/Title.png");
                    title.setTranslateY(HEIGHT * 2/7);
                    title.setTranslateX(WIDTH/4 - 30);
                    root.getChildren().add(title);
                    help = new ImageView("sprite/Help.png");
                    help.setTranslateY(HEIGHT * 4/7);
                    help.setTranslateX(WIDTH/8);
                    root.getChildren().add(help);
                    State = STATE.TITLE;
                    root.getChildren().remove(YouWin);
                    bullet3Ammo = 3;
                    specialBulletLabel.setText("Special Bullets Left: " + bullet3Ammo); //Display Ammo Left
                    bossDefeated = false;
                    bossIsHere = false;
                    bossHealth = 1000;
                    timer = 120 * 60;
                }

                //Up
                if(event.getCode() == KeyCode.UP){
                    if (heroImageView.getTranslateY() < 0){
                        heroDY = 0;
                    }else{
                        heroDY = -3;
                    }
                }
                //Down
                else if(event.getCode() == KeyCode.DOWN){
                    if (heroImageView.getTranslateY() > (HEIGHT - heroViewPorts.get(heroViewportCounter).getHeight())){
                        heroDY = 0;
                        heroImageView.setTranslateY(HEIGHT - heroViewPorts.get(heroViewportCounter).getHeight());
                    }else
                        heroDY = 3;
                }
                //QUIT
                else if (event.getCode() == KeyCode.ESCAPE){
                    System.exit(0);
                }
                //Bullet
                else if(event.getCode() == KeyCode.Z && bulletReload >= 20 && !bossDefeated){
                    bulletReload = 0;
                    ImageView bulletImageView = new ImageView(new Image("sprite/Bullet.png"));
                    Rectangle2D viewport = new Rectangle2D(0,0,50,50);
                    bulletImageView.setViewport(viewport);
                    int width = (int) viewport.getWidth();
                    bulletImageView.setTranslateX(width / 2);
                    bulletImageView.setTranslateY((int)heroImageView.getTranslateY());
                    root.getChildren().add(bulletImageView);
                    bulletImageViewArray.add(bulletImageView);
                
                }
                //Bullet2
                else if(event.getCode() == KeyCode.X && bullet2Reload >= 100 && !bossDefeated){
                    bullet2Reload = 0;
                    ImageView bullet2ImageView = new ImageView(new Image("sprite/IceAttack.png"));
                    Rectangle2D viewport2 = new Rectangle2D(0,0,125,35);
                    bullet2ImageView.setViewport(viewport2);
                    int width2 = (int) viewport2.getWidth();
                    bullet2ImageView.setTranslateX(width2 / 2);
                    bullet2ImageView.setTranslateY((int)heroImageView.getTranslateY());
                    root.getChildren().add(bullet2ImageView);
                    bullet2ImageViewArray.add(bullet2ImageView);
                }
                //Bullet3
                else if(event.getCode() == KeyCode.C && bullet3Reload >= 150 && bullet3Ammo > 0 && !bossDefeated){
                    System.out.println("Shoot3");
                    bullet3Reload = 0;
                    bullet3Ammo--;
                    specialBulletLabel.setText("Special Bullets Left: " + bullet3Ammo); //Display Ammo Left
                    ImageView bullet3ImageView = new ImageView(new Image("sprite/HandAttack.png"));
                    Rectangle2D viewport3 = new Rectangle2D(0,0,80,585);
                    bullet3ImageView.setViewport(viewport3);
                    int width3 = (int) viewport3.getWidth();
                    bullet3ImageView.setTranslateX(width3 / 2);
                    bullet3ImageView.setTranslateY(1);
                    root.getChildren().add(bullet3ImageView);
                    bullet3ImageViewArray.add(bullet3ImageView);
                
                }
            }
            else if (State == STATE.PAUSE){
                //Continue
                if(event.getCode() == KeyCode.ENTER){
                    State = STATE.GAME;
                }
                //Exit
                else if (event.getCode() == KeyCode.ESCAPE){
                    System.exit(0);
                }
            }
            else if (State == STATE.TITLE){
                //Titlet to Game
                if(event.getCode() == KeyCode.ENTER){
                    State = STATE.GAME;
                }
                //Exit
                else if (event.getCode() == KeyCode.ESCAPE){
                    System.exit(0);
                }
            }
        }
        //Stopping
        else if(event.getEventType() == KeyEvent.KEY_RELEASED){
            if(event.getCode() == KeyCode.UP){
                heroDY = 0;
            }else if(event.getCode() == KeyCode.DOWN){
                heroDY = 0;
            }       
        }
    }
    private void handleCollisions(){ //remeber to add enemy bullet (Ebullets)
        ArrayList<ImageView> dyingEnemy1 = new ArrayList<>();
        ArrayList<ImageView> dyingEnemy2 = new ArrayList<>();
        ArrayList<ImageView> dyingEnemy3 = new ArrayList<>();
        ArrayList<ImageView> dyingBoss = new ArrayList<>();
        ArrayList<ImageView> usedBullets = new ArrayList<>();
        ArrayList<ImageView> usedBullets2 = new ArrayList<>();
        ArrayList<ImageView> usedEBullets = new ArrayList<>();
        //Enemy Collision
        for(int i = 0; i < enemy1ImageViewArray.size(); i++){
            //Enemy1 & Hero
            if(enemy1ImageViewArray.get(i).getBoundsInParent().intersects(heroImageView.getBoundsInParent())){
                System.out.println("Hero got hurt!");
                heroHealth--;
                int numRedHearts = Math.abs(MAXIMUMHEARTS * heroHealth / MAXHEALTH);
                for(int k = numRedHearts + 1; k <= MAXIMUMHEARTS; k++){
                    hearts.get(k - 1).setImage(new Image("sprite/greyHeart.png"));
                }
            }
            //Enemy1 & Bullet2
            for(int j = 0; j < bullet2ImageViewArray.size(); j++){
                if(bullet2ImageViewArray.get(j).getBoundsInParent().intersects(enemy1ImageViewArray.get(i).getBoundsInParent())){
                    dyingEnemy1.add(enemy1ImageViewArray.get(i));;
                    
                    score++;
                    scoreLabel.setText("Score: " + score);
                }
            }
            //Enemy1 & Bullet3
            for(int j = 0; j < bullet3ImageViewArray.size(); j++){
                if(bullet3ImageViewArray.get(j).getBoundsInParent().intersects(enemy1ImageViewArray.get(i).getBoundsInParent())){
                    dyingEnemy1.add(enemy1ImageViewArray.get(i));
                    
                    score++;
                    scoreLabel.setText("Score: " + score);
                }
            }
        }
        //Enemy 2 Collision
        for(int i = 0; i < enemy2ImageViewArray.size(); i++){
            //Enemy2 & Hero
            if(enemy2ImageViewArray.get(i).getBoundsInParent().intersects(heroImageView.getBoundsInParent())){
                System.out.println("Hero got hurt!");
                heroHealth--;
                int numRedHearts = Math.abs(MAXIMUMHEARTS * heroHealth / MAXHEALTH);
                for(int k = numRedHearts + 1; k <= MAXIMUMHEARTS; k++){
                    hearts.get(k - 1).setImage(new Image("sprite/greyHeart.png"));
                }
            }
            //Enemy2 & Bullet
            for(int j = 0; j < bulletImageViewArray.size(); j++){
                if(bulletImageViewArray.get(j).getBoundsInParent().intersects(enemy2ImageViewArray.get(i).getBoundsInParent())){
                    dyingEnemy2.add(enemy2ImageViewArray.get(i));
                    usedBullets.add(bulletImageViewArray.get(j));
                    
                    score++;
                    scoreLabel.setText("Score: " + score);
                }
            }
            //Enemy2 & Bullet3
            for(int j = 0; j < bullet3ImageViewArray.size(); j++){
                if(bullet3ImageViewArray.get(j).getBoundsInParent().intersects(enemy2ImageViewArray.get(i).getBoundsInParent())){
                    dyingEnemy2.add(enemy2ImageViewArray.get(i));
                    
                    score++;
                    scoreLabel.setText("Score: " + score);
                }
            }
        }
        //Enemy 3 Collision
        for(int i = 0; i < enemy3ImageViewArray.size(); i++){
            //Enemy3 & Hero
            if(enemy3ImageViewArray.get(i).getBoundsInParent().intersects(heroImageView.getBoundsInParent())){
                System.out.println("Hero got hurt!");
                heroHealth--;
                int numRedHearts = Math.abs(MAXIMUMHEARTS * heroHealth / MAXHEALTH);
                for(int k = numRedHearts + 1; k <= MAXIMUMHEARTS; k++){
                    hearts.get(k - 1).setImage(new Image("sprite/greyHeart.png"));
                }
            }
            //Enemy3 & Bullet
            for(int j = 0; j < bulletImageViewArray.size(); j++){
                if(bulletImageViewArray.get(j).getBoundsInParent().intersects(enemy3ImageViewArray.get(i).getBoundsInParent())){
                    dyingEnemy3.add(enemy3ImageViewArray.get(i));
                    usedBullets.add(bulletImageViewArray.get(j));
                    
                    score++;
                    scoreLabel.setText("Score: " + score);
                }
            }
            //Enemy3 & Bullet2
            for(int j = 0; j < bullet2ImageViewArray.size(); j++){
                if(bullet2ImageViewArray.get(j).getBoundsInParent().intersects(enemy3ImageViewArray.get(i).getBoundsInParent())){
                    dyingEnemy3.add(enemy3ImageViewArray.get(i));;
                    usedBullets2.add(bullet2ImageViewArray.get(j));
                    score++;
                    scoreLabel.setText("Score: " + score);
                }
            }
            //Enemy3 & Bullet3
            for(int j = 0; j < bullet3ImageViewArray.size(); j++){
                if(bullet3ImageViewArray.get(j).getBoundsInParent().intersects(enemy3ImageViewArray.get(i).getBoundsInParent())){
                    dyingEnemy3.add(enemy3ImageViewArray.get(i));
                    
                    score++;
                    scoreLabel.setText("Score: " + score);
                }
            }
        }
        //Boss Collision
        for(int i = 0; i < bossImageViewArray.size(); i++){
            //Boss & Hero
            if(bossImageViewArray.get(i).getBoundsInParent().intersects(heroImageView.getBoundsInParent())){
                System.out.println("Hero got hurt!");
                heroHealth--;
                int numRedHearts = Math.abs(MAXIMUMHEARTS * heroHealth / MAXHEALTH);
                for(int k = numRedHearts + 1; k <= MAXIMUMHEARTS; k++){
                    hearts.get(k - 1).setImage(new Image("sprite/greyHeart.png"));
                }
            }
            //Boss & Bullet
            for(int j = 0; j < bulletImageViewArray.size(); j++){
                if(bulletImageViewArray.get(j).getBoundsInParent().intersects(bossImageViewArray.get(i).getBoundsInParent())){
                    bossHealth--;
                    usedBullets.add(bulletImageViewArray.get(j));
                    if (bossHealth <= 0){
                        dyingBoss.add(bossImageViewArray.get(i));
                        score = score + 20;
                        scoreLabel.setText("Score: " + score);
                        bossDefeated = true;
                    }
                }
            }
            //Boss & Bullet2
            for(int g = 0; g < bullet2ImageViewArray.size(); g++){
                if(bullet2ImageViewArray.get(g).getBoundsInParent().intersects(bossImageViewArray.get(i).getBoundsInParent())){
                    bossHealth = bossHealth - 3;
                    if (bossHealth <= 0){
                        dyingBoss.add(bossImageViewArray.get(i));
                        usedBullets2.add(bullet2ImageViewArray.get(g));
                        score = score + 20;
                        scoreLabel.setText("Score: " + score);
                        bossDefeated = true;
                    }
                }
            }
            //Boss & Bullet3
            for(int g = 0; g < bullet3ImageViewArray.size(); g++){
                if(bullet3ImageViewArray.get(g).getBoundsInParent().intersects(bossImageViewArray.get(i).getBoundsInParent())){
                    bossHealth--;
                    if (bossHealth <= 0){
                        dyingBoss.add(bossImageViewArray.get(i));
                        score = score + 20;
                        scoreLabel.setText("Score: " + score);
                        bossDefeated = true;
                    }
                }
            }
        }
        //EBullet
        for(int z = 0; z < ebulletImageViewArray.size(); z++){
            //EBullet & Hero
            if(ebulletImageViewArray.get(z).getBoundsInParent().intersects(heroImageView.getBoundsInParent())){
                System.out.println("Hero got hurt BADLY!");
                heroHealth = heroHealth - 5;
                int numRedHearts = Math.abs(MAXIMUMHEARTS * heroHealth / MAXHEALTH);
                for(int k = numRedHearts + 1; k <= MAXIMUMHEARTS; k++){
                    hearts.get(k - 1).setImage(new Image("sprite/greyHeart.png"));
                }
            }
            //EBullets & Bullet2
            for(int j = 0; j < bullet2ImageViewArray.size(); j++){
                if(bullet2ImageViewArray.get(j).getBoundsInParent().intersects(ebulletImageViewArray.get(z).getBoundsInParent())){
                    usedEBullets.add(ebulletImageViewArray.get(z));
                    usedBullets2.add(bullet2ImageViewArray.get(j));
                }
            }
            //EBullets & Bullet3
            for(int j = 0; j < bullet3ImageViewArray.size(); j++){
                if(bullet3ImageViewArray.get(j).getBoundsInParent().intersects(ebulletImageViewArray.get(z).getBoundsInParent())){
                    usedEBullets.add(ebulletImageViewArray.get(z));
                }
            }
        }
        //BossBullet
        for(int z = 0; z < bossbulletImageViewArray.size(); z++){
            //EBullet & Hero
            if(bossbulletImageViewArray.get(z).getBoundsInParent().intersects(heroImageView.getBoundsInParent())){
                System.out.println("Hero got hurt BADLY!!!");
                heroHealth = heroHealth - 7;
                int numRedHearts = Math.abs(MAXIMUMHEARTS * heroHealth / MAXHEALTH);
                for(int k = numRedHearts + 1; k <= MAXIMUMHEARTS; k++){
                    hearts.get(k - 1).setImage(new Image("sprite/greyHeart.png"));
                }
            }
            //EBullets & Bullet
            for(int j = 0; j < bulletImageViewArray.size(); j++){
                if(bulletImageViewArray.get(j).getBoundsInParent().intersects(bossbulletImageViewArray.get(z).getBoundsInParent())){
                    usedBullets.add(bulletImageViewArray.get(j));
                }
            }
            //EBullets & Bullet2
            for(int j = 0; j < bullet2ImageViewArray.size(); j++){
                if(bullet2ImageViewArray.get(j).getBoundsInParent().intersects(bossbulletImageViewArray.get(z).getBoundsInParent())){
                    usedBullets2.add(bullet2ImageViewArray.get(j));
                }
            }
        }

        //REMOVE DYING ENEMIES1
        for(int i = 0; i < dyingEnemy1.size(); i++){
            root.getChildren().remove(dyingEnemy1.get(i));
            enemy1ImageViewArray.remove(dyingEnemy1.get(i));
        }
        //REMOVE DYING ENEMIES2
        for(int i = 0; i < dyingEnemy2.size(); i++){
            root.getChildren().remove(dyingEnemy2.get(i));
            enemy2ImageViewArray.remove(dyingEnemy2.get(i));
        }
        //REMOVE DYING ENEMIES3
         for(int i = 0; i < dyingEnemy3.size(); i++){
            root.getChildren().remove(dyingEnemy3.get(i));
            enemy3ImageViewArray.remove(dyingEnemy3.get(i));
        }
        //REMOVE DYING Boss
        for(int i = 0; i < dyingBoss.size(); i++){
            root.getChildren().remove(dyingBoss.get(i));
            bossImageViewArray.remove(dyingBoss.get(i));
        }
        //Show Boss
        if (timer == 0 && !bossIsHere && !bossDefeated) {
            //CREATE BOSS 
            bossImageView = new ImageView(new Image("sprite/BossSprite.png"));
            bossViewPorts.add(new Rectangle2D(0,0,225,380));
            bossImageView.setViewport(bossViewPorts.get(0));
            bossImageView.setTranslateX(WIDTH - bossImageView.getViewport().getWidth());
            bossImageView.setTranslateY(HEIGHT/2 - bossImageView.getViewport().getHeight()/2);
            
            root.getChildren().add(bossImageView);
            bossImageViewArray.add(bossImageView);
            bossIsHere = true;
        }
        //REMOVE USED BULLETS
        for(int j = 0; j < usedBullets.size(); j++){
            root.getChildren().remove(usedBullets.get(j));
            bulletImageViewArray.remove(usedBullets.get(j));
        }
        //REMOVE USED BULLETS2
        for(int g = 0; g < usedBullets2.size(); g++){
            root.getChildren().remove(usedBullets2.get(g));
            bullet2ImageViewArray.remove(usedBullets2.get(g));
        }
        //REMOVE USED EBULLET
        for (int z = 0; z < usedEBullets.size(); z++) {
            root.getChildren().remove(usedEBullets.get(z));
            ebulletImageViewArray.remove(usedEBullets.get(z));
        }
        //Losing
        if (heroHealth <= 0) {
            YouLose = new ImageView(new Image("sprite/YouLose.png"));
            YouLose.setTranslateX(WIDTH/4);
            YouLose.setTranslateY(HEIGHT * 3/7);
            root.getChildren().add(YouLose);
            timeline.stop();
        }
        //Win
        if(bossDefeated){
            YouWin = new ImageView(new Image("sprite/YouWin.png"));
            YouWin.setTranslateX(WIDTH/4);
            YouWin.setTranslateY(HEIGHT * 3/7);
            root.getChildren().add(YouWin);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}

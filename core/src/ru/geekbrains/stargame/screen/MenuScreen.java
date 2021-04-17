package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.ExitButton;
import ru.geekbrains.stargame.sprite.PlayButton;
import ru.geekbrains.stargame.sprite.SpaceShip;
import ru.geekbrains.stargame.sprite.Star;
import ru.geekbrains.stargame.sprite.Stars;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Stars stars;
    private float worldSpeed = 0.001f;
    private ExitButton exitButton;
    private PlayButton playButton;

    public MenuScreen(StarGame starGame) {
        this.starGame = starGame;
    }

    private final StarGame starGame;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        stars = new Stars(atlas, 128);
        exitButton = new ExitButton(atlas);
        playButton = new PlayButton(atlas, starGame);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        exitButton.resize(worldBounds);
        playButton.resize(worldBounds);
        stars.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        stars.update(worldSpeed, delta);
        stars.draw(batch);
        exitButton.draw(batch);
        playButton.draw(batch);
        batch.end();

    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitButton.touchDown(touch,pointer,button);
        playButton.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitButton.touchUp(touch,pointer,button);
        playButton.touchUp(touch,pointer,button);
        return false;
    }
}

package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.BaseButton;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.screen.GameScreen;

public class PlayButton extends BaseButton {

    private static final float HEIGHT = 0.6f;
    private final Game game;

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game=game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportional(HEIGHT);
        pos.set(0, 0.1f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}

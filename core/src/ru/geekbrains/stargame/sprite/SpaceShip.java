package ru.geekbrains.stargame.sprite;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class SpaceShip extends Sprite {


    protected Rect worldBounds;
    protected  float reloadInterval;
    protected float reloadTimer;



    public SpaceShip(TextureRegion region, int rows, int cols, int frames){
        super(region, rows, cols, frames);
    }

    public SpaceShip() {
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public void update(float speed, float delta) {
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds=worldBounds;
    }

    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    protected void shoot () {
    }
}

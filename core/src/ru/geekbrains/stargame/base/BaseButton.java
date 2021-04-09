package ru.geekbrains.stargame.base;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseButton extends Sprite {

    private static final float PRESS_SCALE = 0.9f;

    private int pointer;
    private boolean pressed;


    public BaseButton(TextureRegion region) {
        super(region);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(isMe(touch)){
            pressed=true;
            this.pointer=pointer;
            setHeightProportional(getHeight()*PRESS_SCALE);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(isMe(touch) && pressed && pointer==this.pointer) {
            action();
            setHeightProportional(getHeight()/PRESS_SCALE);
        }
        else if (pressed && pointer==this.pointer) {
            pressed=false;
            setHeightProportional(getHeight()/PRESS_SCALE);
        }
        return false;
    }

    public abstract void action();
}

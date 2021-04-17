package ru.geekbrains.stargame.math;

import com.badlogic.gdx.math.Vector2;

public class Rect {

    protected Vector2 pos;
    protected float halfWidth;
    protected float halfHeight;

    public Rect() {
        pos = new Vector2();
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Rect(Rect form) {
        this(form.pos.x, form.pos.y, form.getHalfWidth(), form.getHalfHeight());
    }

    public Rect(float x, float y, float halfWidth, float halfHeight) {
        pos = new Vector2();
        pos.set(x,y);
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    public float getLeft(){
        return pos.x-halfWidth;
    }

    public float getRight(){
        return pos.x+halfWidth;
    }

    public float getTop(){
        return pos.y+halfHeight;
    }

    public float getBottom(){
        return pos.y-halfHeight;
    }

    public float getWidth() {
        return halfWidth*2f;
    }

    public void setWidth(float Width) {
        this.halfWidth = Width/2f;
    }

    public float getHeight() {
        return halfHeight*2f;
    }

    public void setHeight(float Height) {
        this.halfHeight = Height/2f;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public void setHalfWidth(float halfWidth) {
        this.halfWidth = halfWidth;
    }

    public void setHalfHeight(float halfHeight) {
        this.halfHeight = halfHeight;
    }

    public void set (Rect form){
        pos.set(form.pos);
        halfHeight = form.getHalfHeight();
        halfWidth = form.getHalfWidth();
    }

    public void set(float x, float y, float halfWidth, float halfHeight) {
        pos.set(x,y);
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    public void setTop (float top){
        pos.y = top-halfHeight;
    }

    public void setBottom (float bottom){
        pos.y = bottom+halfHeight;
    }

    public void setLeft (float left){
        pos.x = left+halfWidth;
    }

    public void setRight (float right){
        pos.x = right-halfWidth;
    }

    public void setSize(float width, float height){
        this.halfWidth = width/2f;
        this.halfHeight = height/2f;
    }

    public boolean isMe (Vector2 touch){
        return touch.x >= this.getLeft() && touch.x <= this.getRight() &&
                        touch.y >= this.getBottom() && touch.y <= this.getTop();
    }

    public boolean isOutside (Rect form){
        return form.getLeft() > this.getRight() || form.getRight() < this.getLeft() ||
                form.getTop() < this.getBottom() || form.getBottom() > this.getTop();
    }

    @Override
    public String toString() {
        return "Rectangle: " +
                "pos=" + pos +
                ", Width=" + halfWidth * 2f +
                ", Height=" + halfHeight * 2f;
    }
}

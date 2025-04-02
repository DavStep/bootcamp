package com.rockbite.bootcamp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import lombok.Getter;

public class Logo extends Image {

    private static final float DUMPING = 0.8f;
    private static final float EPSILON = 0.1f;
    private static final float MAX_LIFETIME = 5;
    private float currentLifetime;
    private Array<Logo> logos;

    @Getter
    private final Vector2 velocity = new Vector2();

    public Logo () {
        setDrawable(new TextureRegionDrawable(new Texture("libgdx.png")));
        pack();
        resetProperties();
    }

    @Override
    public void act (float delta) {
        super.act(delta);

        currentLifetime += delta;

        if (currentLifetime >= MAX_LIFETIME) {
            die();
            return;
        }

        if (velocity.x == 0 && velocity.y == 0) {
            return;
        }

        if (Math.abs(velocity.x) < EPSILON) {
            velocity.x = 0;
        }
        if (Math.abs(velocity.y) < EPSILON) {
            velocity.y = 0;
        }

        float x = getX();
        float y = getY();
        x += velocity.x * delta;
        y += velocity.y * delta;

        // check horizontal boundaries and bounce
        if (x < 0) {
            x = 0;
            velocity.x = -velocity.x;
            applyDumping();
        } else if (x + getWidth() > Gdx.graphics.getWidth()) {
            x = Gdx.graphics.getWidth() - getWidth();
            velocity.x = -velocity.x;
            applyDumping();
        }

        // check vertical boundaries and bounce
        if (y < 0) {
            y = 0;
            velocity.y = -velocity.y;
            applyDumping();
        } else if (y + getHeight() > Gdx.graphics.getHeight()) {
            y = Gdx.graphics.getHeight() - getHeight();
            velocity.y = -velocity.y;
            applyDumping();
        }

        setPosition(x, y);
    }

    private void applyDumping () {
        velocity.x *= DUMPING;
        velocity.y *= DUMPING;
    }

    public void resetProperties () {
        velocity.set(100, 100);
        currentLifetime = 0;
    }

    public void setContainer (Array<Logo> logos) {
        this.logos = logos;
        this.logos.add(this);
    }

    public void die () {
        if (logos.contains(this, true)) {
            logos.removeValue(this, true);
        }
        Pools.free(this);
        resetProperties();
        remove();
    }
}

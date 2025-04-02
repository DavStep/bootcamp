package com.rockbite.bootcamp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Game extends ApplicationAdapter {

    private static final float GRAVITY = 1600f;
    private Stage stage;
    private final Array<Logo> logos = new Array<>();
    private static final int MAX_LOGO_CAP = 2;

    @Override
    public void create() {
        final ScreenViewport viewport = new ScreenViewport();
        final SpriteBatch batch = new SpriteBatch();
        stage = new Stage(viewport, batch);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        for (Logo logo : logos) {
            final Vector2 velocity = logo.getVelocity();
            velocity.y -= GRAVITY * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.justTouched()) {
            final int x = Gdx.input.getX();
            final int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (logos.size == MAX_LOGO_CAP) {
                logos.first().die();
            }

            final Logo newLogo = Pools.obtain(Logo.class);
            newLogo.setContainer(logos);
            newLogo.setPosition(x, y);
            stage.addActor(newLogo);
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

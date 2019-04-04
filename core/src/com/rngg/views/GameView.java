package com.rngg.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rngg.controllers.GameController;
import com.rngg.game.Rngg;
import com.rngg.models.Player;
import com.rngg.models.SquareMap;
import com.rngg.utils.GameAssetManager;

import java.util.List;

public class GameView extends View {

    private GameController controller;

    private SpriteBatch batch;
    private MapRenderer mapRenderer;
    private ShapeRenderer shapeRenderer;
    private List<Player> players;
    private HUDRenderer hudRenderer;
    private InGameMenuRenderer inGameMenuRenderer;


    public GameView(GameController controller, List<Player> players) {
        camera.viewportHeight = Rngg.HEIGHT * 10 / 8;

        this.controller = controller;
        this.players = players;

        batch = new SpriteBatch();
        font.setColor(Color.WHITE);
        this.shapeRenderer = new ShapeRenderer();
        mapRenderer = new SquareMapRenderer((SquareMap) controller.gameModel.getMap(), shapeRenderer, batch, font);
        hudRenderer = new HUDRenderer(controller.gameModel, font, GameAssetManager.getInstance(), controller);
        inGameMenuRenderer = new InGameMenuRenderer(controller.gameModel, font, GameAssetManager.getInstance(), controller, shapeRenderer);
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        controller.update(delta);
        controller.setCamera(camera);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.draw();
        hudRenderer.draw();
        inGameMenuRenderer.draw();
    }

    public ShapeRenderer getSR() {
        return this.shapeRenderer;
    }

    public BitmapFont getFont() {
        return font;
    }

    public SpriteBatch getBatch() {
        return this.batch;
    }
}

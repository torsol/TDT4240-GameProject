package com.rngg.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.rngg.singletons.RNG;
import com.rngg.views.GameView;

import java.util.Arrays;

public class GameModel {
    /*
        TODO this is "hardcoded" in a lot of ways, for instance using SquareMap and SquareZone.
        This should be changed
    */

    public int playerScore = 0;
    private SquareMap map;
    private Player[] players;
    private SquareZone attacker;
    private RNG rng;

    public GameModel(int numPlayers) {
        this.players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player("Player" + i);
        }
        this.map = new SquareMap(9, 16, players);
        this.rng = RNG.getInstance();
    }

    public void draw(GameView view) {
        map.draw(view);
    }

    public SquareMap getMap() {
        return this.map;
    }

    public void click(Vector3 coords) {
        SquareZone temp = map.screenCoordToZone(new Vector2(coords.x, coords.y));
        if (temp.getPlayer() == this.players[0]) {
            if (temp.getUnits() <= 1) {
                Gdx.app.log(this.getClass().getSimpleName(), temp.toString() + " has too few units to attack");
                return;
            }
            if (temp.getUnits() <= 1) {
                Gdx.app.log(this.getClass().getSimpleName(), temp.toString() + " has too few units to attack");
                return;
            }
            if (this.attacker != null) {
                this.attacker.unClick();
            }
            this.attacker = temp;
            this.attacker.click();
            Gdx.app.log(this.getClass().getSimpleName(), "Set " + this.attacker.toString() + " as attacker");
        } else {
            if (attacker == null) {
                Gdx.app.log(this.getClass().getSimpleName(), temp.toString() + " does not belong to " + this.players[0]);
                return;
            }
            if (!map.isNeighbors(this.attacker, temp)) {
                Gdx.app.log(this.getClass().getSimpleName(), temp.toString() + " and " + attacker.toString() + " are not neighbors");
                return;
            }
            this.attack(attacker, temp);
        }
    }

    public int attack(SquareZone attacker, SquareZone defender) {
        // TODO move this to another class
        /*
        Returns
            -1 if defender defends
             0 if attack is invalid
             1 if attacker wins
        */

        attacker.unClick();
        defender.unClick();
        this.attacker = null;

        Gdx.app.log(this.getClass().getSimpleName(),
                String.format("%s is attacking %s", attacker.getPlayer().toString(), defender.getPlayer().toString())
        );

        this.rng.roll(attacker.getUnits());
        float attackRoll = rng.valueFromRoll();
        Gdx.app.log(this.getClass().getSimpleName(),
                attacker.toString() + " rolled " + attackRoll + " (" + Arrays.toString(rng.labelFromRoll()) + ")");

        this.rng.roll(defender.getUnits());
        float defendRoll = rng.valueFromRoll();
        Gdx.app.log(this.getClass().getSimpleName(),
                defender.toString() + " rolled " + defendRoll + " (" + Arrays.toString(rng.labelFromRoll()) + ")");

        boolean attackerWon = attackRoll > defendRoll;

        if (attackerWon) {
            Gdx.app.log(this.getClass().getSimpleName(), "Attacker won");
            defender.setUnits(attacker.getUnits() - 1);
            defender.setPlayer(attacker.getPlayer());
            // TODO check if defender is alive
            // TODO check if game is over
        } else {
            Gdx.app.log(this.getClass().getSimpleName(), "Attacker lost");
        }
        attacker.setUnits(1);
        return attackerWon ? 1 : -1;
    }
}

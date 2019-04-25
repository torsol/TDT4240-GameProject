package com.rngg.models;

import java.util.ArrayList;
import java.util.List;

public class HexZone extends Zone {

    private int row, col;
    private HexMeshZone superZone;
    private List<HexZone> neighbors;

    public HexZone(Player player, int row, int col) {
        this.row = row;
        this.col = col;
        this.player = player;
        this.units = (int) ((Math.random() * 7) + 1);
        this.superZone = null;
        this.neighbors = new ArrayList<HexZone>();
    }

    public List<HexZone> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(HexZone neighbor) {
        this.neighbors.add(neighbor);
    }

    public void setSuperZone(HexMeshZone superZone) {
        this.superZone = superZone;
    }

    public HexMeshZone getSuperZone() {
        return superZone;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "HexZone{" +
                "row=" + row +
                ", col=" + col +
                ", player=" + player +
                '}';
    }
}

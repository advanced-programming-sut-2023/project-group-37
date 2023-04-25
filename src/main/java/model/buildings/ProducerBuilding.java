package model.buildings;

import model.game.Government;
import model.game.Item;
import model.game.Tile;

public class ProducerBuilding extends WorkerNeededBuilding{
    private final Item rawMaterial;
    private final Item product;

    public ProducerBuilding(Government loyalty, Tile location, BuildingType type) {
        super(loyalty, location, type);
        this.rawMaterial = type.getRawMaterial();
        this.product = type.getProduct();
    }
}

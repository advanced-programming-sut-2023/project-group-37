package model.game;

import controller.MultiMenuFunctions;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.DefensiveBuilding;
import model.people.*;

import java.util.ArrayList;

public class Tile extends Rectangle {
    private static Integer tileSize;
    private static ArrayList<Tile> selectedTiles;
    private final int x;
    private final int y;
    private Texture texture;
    private Texture treeTexture;
    private Image tileImage;
    private ImageView upperImage;
    private Tile miniTile;
    private ArrayList<Person> people;
    private ArrayList<MilitaryUnit> militaryUnits;
    private Building building;
    private boolean isPassable;
    private Territory territory;
    public int number;
    private boolean hasBuilding;

    static {
        tileSize = 20;
        selectedTiles = new ArrayList<>();
    }

    public Tile(int x, int y) {
        super(tileSize, tileSize);
        this.x = x;
        this.y = y;
        this.texture = Texture.GROUND;
        this.setTileImage(this.texture.getImage());
        this.miniTile = new Tile(this);

        this.people = new ArrayList<>();
        this.militaryUnits = new ArrayList<>();
        this.building = null;
        this.isPassable = true;
        this.hasBuilding = false;

        //todo : remove this :
        if (y == 15 && x <= 20 && x>= 10)
            this.militaryUnits.add(new Troop(null ,TroopType.ARCHER, this));
    }

    public Tile(Tile bigTile) { // for creating miniTile
        super((double) tileSize/40, (double) tileSize/40);
        this.x = bigTile.x;
        this.y = bigTile.y;
        this.texture = bigTile.texture;
    }

    private static void removeSelectedTiles() {
        for (Tile selectedTile : selectedTiles) {
            selectedTile.setWidth(20);
            selectedTile.setHeight(20);
            selectedTile.strokeProperty().set(null);
        }
        selectedTiles = new ArrayList<>();
    }

    public static void setSelectedTiles(ArrayList<Tile> selectedTiles) {
        removeSelectedTiles();
        Tile.selectedTiles.addAll(selectedTiles);
    }

    public static void zoom() {
        if (tileSize <= 40)
            tileSize += 4;
    }

    public static void zoomOut() {
        if (tileSize >= 23)
            tileSize -= 4;
    }

    public static int getTileSize() {
        return tileSize;
    }

    public void updateSize() {
        this.setHeight(tileSize);
        this.setWidth(tileSize);
        this.updateImage();
    }

    // Effects :
    public void setRectangleSelectedEffect() {
        this.setWidth(tileSize - 1);
        this.setHeight(tileSize - 1);
        this.setStroke(Color.GREEN);
        selectedTiles.add(this);
    }

    public void setSelectedEffect() {
        removeSelectedTiles();
        this.setWidth(tileSize - 1);
        this.setHeight(tileSize - 1);
        this.setStroke(Color.GREEN);
        selectedTiles.add(this);
    }

    public void setUpRectangleEffect() {
        this.setRectangleSelectedEffect();
    }
    public void setDownRectangleEffect() {
        this.setRectangleSelectedEffect();
    }
    public void setLeftRectangleEffect() {
        this.setRectangleSelectedEffect();
    }
    public void setRightRectangleEffect() {
        this.setRectangleSelectedEffect();
    }

    public int getLocationX() {
        return x;
    }

    public int getLocationY() {
        return y;
    }

    public Tile getMiniTile() {
        return this.miniTile;
    }

    public Texture getTexture() {
        return texture;
    }

    public void changeTexture(Texture texture) {
        this.texture = texture;
    }

    public void changeTreeTexture(Texture treeTexture) {
        this.treeTexture = treeTexture;
    }

    public ArrayList<MilitaryUnit> getMilitaryUnits() {
        return militaryUnits;
    }

    public Building getBuilding() {
        if (!this.hasBuilding)
            return null;

        return building;
    }

    public void receiveDamage(int damage, Government government) {
        int firstDamage = damage;
        for (MilitaryUnit militaryUnit : militaryUnits) {
            if (militaryUnit.getLoyalty() != government) {
                damage -= militaryUnit.takeDamage(damage);
                if (damage < 1)
                    break;
            }
        }

        if (damage == firstDamage && this.building != null) {
            if (!(this.building instanceof DefensiveBuilding) && this.building.getType() != BuildingType.KILLING_PIT &&
                    this.building.getLoyalty() != government)
                this.building.takeDamage(damage);
        }
    }

    public void receiveBuildingDamage(int damage, Government government) {
        if (this.building instanceof DefensiveBuilding && this.building.getType() != BuildingType.KILLING_PIT &&
                this.building.getLoyalty() != government)
            this.building.takeDamage(damage);
    }

    public void removeBuilding() {
        this.hasBuilding = false;
        this.building = null;
    }

    private void setTileImage(Image inputImage) {
        if (this.tileImage != inputImage) {
            this.tileImage = inputImage;
            this.setFill(new ImagePattern(inputImage));

            if (this.miniTile != null)
                this.miniTile.setTileImage(this);
        }
    }

    private void setTileImage(Tile tile) {
        this.tileImage = tile.tileImage;
        this.setFill(new ImagePattern(this.tileImage));
    }

    public void setBuilding(Building building) {
        this.building = building;

        if (building != null) {
            this.hasBuilding = true;
            this.updateImage();

            if (building instanceof DefensiveBuilding defensiveBuilding)
                this.setTileImage(defensiveBuilding.getDefensiveType().getImage());
            else this.setTileImage(building.getType().getImage());
        }

        else this.hasBuilding = false;
    }

    private void checkForImage(Image image , Color color) {
        if (image != this.upperImage.getImage()) {
            MultiMenuFunctions.removeTileImage(this.upperImage);
            this.upperImage = new ImageView(new Image(image.getUrl(),
                    Tile.getTileSize(), Tile.getTileSize(), false, false));

            MultiMenuFunctions.setTileImage(this, this.upperImage);
            this.miniTile.setFill(color);
        }
    }

    public void updateImage() {
        this.setTileImage(this.texture.getImage());
        Image image;

        if (this.militaryUnits.size() > 0) {
            image = ((Troop) this.militaryUnits.get(this.militaryUnits.size() -1)).getType().getImage();
            this.checkForImage(image, Color.HOTPINK);
            return;
        }

        if (this.hasBuilding) {
            if (this.building instanceof DefensiveBuilding defensiveBuilding)
                this.checkForImage(defensiveBuilding.getDefensiveType().getImage(), Color.WHITESMOKE);
            else
                this.checkForImage(this.building.getType().getImage(), Color.LIGHTYELLOW);

            return;
        }

        if (this.treeTexture != null)
            this.checkForImage(this.treeTexture.getImage(), Color.DARKGREEN);
    }

    public boolean isPassable() {
        return isPassable;
    }

    public Territory getTerritory() {
        return this.territory;
    }

    public ArrayList<Person> getPeople() {
        return this.people;
    }

    public void addPerson(Person person) {
        this.people.add(person);
    }

    public void addMilitaryUnit(MilitaryUnit troop) {
        this.militaryUnits.add(troop);
        this.updateImage();
    }

    public void setPassability(boolean passability) {
        isPassable = passability;
    }

    public boolean isTotallyNotEmpty() {
        return !this.people.isEmpty() || !this.militaryUnits.isEmpty() || this.getBuilding() != null;
    }

    public boolean hasEnemy(Government government) {
        for (MilitaryUnit militaryUnit : this.militaryUnits) {
            if (militaryUnit.getLoyalty() != government)
                return true;
        }
        return false;
    }

    public boolean equals(Tile tile) {
        return (this.x == tile.x && this.y == tile.y);
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public void removeMilitaryUnits() {
        militaryUnits = new ArrayList<>();
    }

    public void changeMoat() {
        if (texture == Texture.MOAT) {
            texture = Texture.GROUND;
            this.isPassable = true;
        } else {
            texture = Texture.MOAT;
            this.isPassable = false;
        }
    }

    public boolean hasBuilding() {
        return hasBuilding;
    }

}
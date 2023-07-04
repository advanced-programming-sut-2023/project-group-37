package model.game;

import controller.MultiMenuFunctions;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.DefensiveBuilding;
import model.people.MilitaryMachine;
import model.people.MilitaryUnit;
import model.people.Person;
import model.people.Troop;

import java.util.ArrayList;

public class Tile extends Rectangle {
    private static ArrayList<Tile> modifiedTiles;
    private static Integer tileSize;
    private static ArrayList<Tile> selectedTiles;
    private final int x;
    private final int y;
    private Texture texture;
    private Texture treeRockTexture;
    private Image image;
    private Tile miniTile;
    private ArrayList<Person> people;
    private ArrayList<MilitaryUnit> militaryUnits;
    private Building building;
    private boolean isPassable;
    private Territory territory;
    public int number;
    private boolean hasBuilding;

    static {
        modifiedTiles = new ArrayList<>();
        tileSize = 20;
        selectedTiles = new ArrayList<>();
    }

    public Tile(int x, int y) {
        super(tileSize, tileSize);
        this.x = x;
        this.y = y;
        this.setLayoutX(this.x * tileSize);
        this.setLayoutY(this.y * tileSize);
        this.texture = Texture.GROUND;
        this.setImage(this.texture.getImage());
        this.miniTile = new Tile(this);
        this.people = new ArrayList<>();
        this.militaryUnits = new ArrayList<>();
        this.building = null;
        this.isPassable = true;
        this.hasBuilding = false;
    }

    public Tile(Tile bigTile) { // for creating miniTile
        super((double) tileSize / 40, (double) tileSize / 40);
        this.x = bigTile.x;
        this.y = bigTile.y;
        this.texture = bigTile.texture;
    }

    public static void addModifiedTile(Tile tile) {
        if (!modifiedTiles.contains(tile))
            modifiedTiles.add(tile);
    }

    public static void removeSelectedTiles() {
        for (Tile selectedTile : selectedTiles) {
            selectedTile.setWidth(20);
            selectedTile.setHeight(20);
            selectedTile.strokeProperty().set(null);
        }
        selectedTiles = new ArrayList<>();
    }

    public static ArrayList<Tile> updateImages() {
        for (Tile tile : modifiedTiles)
            tile.updateImage();
        ArrayList<Tile> tiles = modifiedTiles;

        modifiedTiles = new ArrayList<>();
        return tiles;
    }

    public String getInfo() {
        String info = "Texture : " + this.texture.getName() + "\n";
        if (this.getMilitaryUnits() != null) {
            for (MilitaryUnit militaryUnit : this.getMilitaryUnits()) {
                info += "Name : " + militaryUnit.getName() + "  Hp : " + militaryUnit.getHitpoints() + "\n";
            }
        }

        if (this.hasBuilding) {
            info += "Building Name : " + this.building.getName() + "  HP : " + building.getHitpoints() + "\n";
        }
        info = info.trim();
        return info;
    }

    public static void setSelectedTiles(ArrayList<Tile> selectedTiles) {
        removeSelectedTiles();
        Tile.selectedTiles.addAll(selectedTiles);
    }

    public static boolean zoom() {
        if (tileSize <= 40) {
            tileSize += 4;
            return true;
        }
        return false;
    }

    public static boolean zoomOut() {
        if (tileSize >= 23) {
            tileSize -= 4;
            return true;
        }
        return false;
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

    public void changeTreeRockTexture(Texture treeRockTexture) {
        this.treeRockTexture = treeRockTexture;
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
            if (this.building.getLoyalty() != government)
                this.building.takeDamage(damage/2);
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

    private void setImage(Image inputImage) {
        this.image = inputImage;
        this.setFill(new ImagePattern(inputImage));

        if (this.miniTile != null)
            this.miniTile.setImage(this);
    }

    private void setImage(Tile tile) {
        this.image = tile.image;
        this.setFill(new ImagePattern(this.image));
    }

    public void setBuilding(Building building) {
        this.building = building;

        if (building != null) {
            this.hasBuilding = true;
            this.updateImage();

            if (building instanceof DefensiveBuilding defensiveBuilding)
                this.setImage(defensiveBuilding.getDefensiveType().getImage());
            else this.setImage(building.getType().getImage());
        } else this.hasBuilding = false;
        this.updateImage();
    }

    public void updateImage() {
        addModifiedTile(this);
        try {
            this.setImage(this.texture.getImage());

            if (this.treeRockTexture != null) {
                MultiMenuFunctions.setTileImage(this, treeRockTexture.getImage(), treeRockTexture.getName());
                this.miniTile.setFill(Color.DARKGREEN);
            }

            if (this.hasBuilding) {
                if (this.building instanceof DefensiveBuilding defensiveBuilding) {
                    MultiMenuFunctions.setTileImage(this, defensiveBuilding.getDefensiveType().getImage(), this.building.getName());
                    this.miniTile.setFill(Color.WHITESMOKE);
                } else {
                    MultiMenuFunctions.setTileImage(this, this.building.getType().getImage(), this.building.getName());
                    this.miniTile.setFill(Color.LIGHTYELLOW);
                }
            }

            if (this.militaryUnits.size() > 0) {
                for (MilitaryUnit militaryUnit : this.militaryUnits) {
                    if (militaryUnit instanceof MilitaryMachine militaryMachine)
                        MultiMenuFunctions.setTileImage(this, militaryMachine.getType().getImage());
                    else if (militaryUnit instanceof Troop troop)
                        MultiMenuFunctions.setTileImage(this, troop.getStandingImage());
                }

                this.miniTile.setFill(Color.HOTPINK);
            }
            Tooltip.install(this, new Tooltip(this.getInfo()));
        } catch (Exception ignored) {
        }
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

    public void updateTile(Tile tile) {
        this.militaryUnits = tile.getMilitaryUnits();
        this.setBuilding(tile.getBuilding());
    }
}
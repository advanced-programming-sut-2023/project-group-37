package model.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {

    // TODO: handle load maps!
    private static ArrayList<Map> maps;
    private final String name;
    private final int size;
    private final Tile[][] field;
    private final boolean[][] tilesPassability;
    private HashMap<Integer, Government> territories;
    private HashMap<Integer, Tile> headQuarters;
    private static final Gson gson = new Gson();

    static {
        maps = new ArrayList<>();
    }

    // TODO: handle headquarters illegible on page 29!

    public Map(String name) {
        this.name = name;
        this.size = 200;
        this.field = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                field[i][j] = new Tile(i, j);
        }
        this.tilesPassability = new boolean[size][size];
        this.initializeTiles();
        headQuarters = new HashMap<>();
    }

    public Map(String name, int size, Tile[][] map, boolean[][] tilesPassability,
               HashMap<Integer, Government> territories, HashMap<Integer, Tile> headQuarters) {
        this.name = name;
        this.size = size;
        this.field = map;
        this.tilesPassability = tilesPassability;
        this.territories = territories;
        this.headQuarters = headQuarters;
        this.initializeTiles();
    }

    public static Map getMapByName(String name) {
        for (Map map : maps)
            if (map.getName().equals(name))
                return map;
        return null;
    }

    public static ArrayList<Map> getMaps() {
        return maps;
    }


    public static Map getMapCopyByName(String name) {
        for (Map map : maps)
            if (map.getName().equals(name))
                return new Map(name, map.size, map.getField(), map.getTilesPassability(),
                        map.getTerritories(), map.getHeadQuarters());
        return null;
    }


    public static void loadMaps() {
        String filePath = "src/main/resources/sampleMaps.json";
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            ArrayList<Map> sampleMaps = gson.fromJson(json, new TypeToken<List<Map>>() {
            }.getType());
            if (sampleMaps != null) {
                Map.maps = sampleMaps;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

//        public static void main(String[] args) {
//        writeMapsToFile();
//    }
    public static void writeMapsToFile() {
        maps.add(GenerateMap.createMap1());
        //maps.add(GenerateMap.createMap2());
        String filePath = "src/main/resources/sampleMaps.json";
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(new ObjectMapper().writeValueAsString(maps));
            fileWriter.close();
        } catch (IOException ignored) {
            File file = new File(filePath);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public Tile[][] getField() {
        return this.field;
    }

    public boolean[][] getTilesPassability() {
        return this.tilesPassability;
    }

    public HashMap<Integer, Government> getTerritories() {
        return this.territories;
    }

    public HashMap<Integer, Tile> getHeadQuarters() {
        return this.headQuarters;
    }

    public void setTilesState() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++)
                this.field[i][j].setState();
        }
    }

    public void setTilesPassability() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++)
                tilesPassability[i][j] = this.field[i][j].isPassable();
        }
    }

    public void resetNumbers() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++)
                this.field[i][j].number = 0;
        }
    }

    public Tile getTileByLocation(int x, int y) {
        if (x >= this.size || y >= this.size || x < 0 || y < 0)
            return null;

        return this.field[x][y];
    }
    public boolean AreaContainsSomething(int x1,int y1,int x2,int y2){
        for (int i = y1; i <= y2; i++) {
            for (int j = x1; j <= x2 ; j++) {
                if (field[i][j].getBuilding() != null || field[i][j].getPeople().size() != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean getPassabilityByLocation(int x, int y) {
        return this.field[x][y].isPassable();
    }

    private void initializeTiles() {
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                this.field[i][j] = new Tile(i, j);
    }

    public Tile getHeadQuarter(int territory) {
        return headQuarters.get(territory);
    }
}

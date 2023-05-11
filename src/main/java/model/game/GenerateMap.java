package model.game;

public class GenerateMap {
    private static Map map;

    //    public static void main(String[] args) {
//        createMap1();
//        printMap();
//    }
    public static Map createMap1() {
        map = new Map("North vs South");
        //set ground
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                map.getField()[i][j].changeTexture(Texture.GROUND);
                map.getField()[i][j].setPassability(Texture.GROUND.canHaveBuildingAndUnit());
            }
        }
        // river in the middle
        for (int i = 75; i < 125; i++) {
            for (int j = 0; j < 200; j++) {
                map.getField()[i][j].changeTexture(Texture.RIVER);
                map.getField()[i][j].setPassability(Texture.RIVER.canHaveBuildingAndUnit());
            }
        }
        // passable ways in river
        for (int i = 75; i < 125; i++) {
            for (int j = 60; j < 140; j++) {
                if (j < 70 || (90 < j && j < 110) || 130 < j) {
                    map.getField()[i][j].changeTexture(Texture.SHALLOW_POND);
                    map.getField()[i][j].setPassability(Texture.SHALLOW_POND.canHaveBuildingAndUnit());
                }
            }
        }
        //stones (mountains)
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                if (!((75 <= i && i < 125) || (30 < i && i < 50) || (150 < i && i < 170)) && ((55 < j && j < 60) ||
                        (140 < j && j < 145))) {
                    map.getField()[i][j].changeTexture(Texture.STONE);
                    map.getField()[i][j].setPassability(Texture.STONE.canHaveBuildingAndUnit());
                }
            }
        }
        //20 * 10 rocks (can be mined)
        for (int j = 90; j < 110; j++) {
            for (int i = 60; i < 140; i++) {
                if (i < 70 || 130 < i) {
                    map.getField()[i][j].changeTexture(Texture.ROCK);
                    map.getField()[i][j].setPassability(Texture.ROCK.canHaveBuildingAndUnit());
                }
            }
        }
        //trees
        for (int i = 65; i < 135; i++) {
            for (int j = 0; j < 200; j++) {
                if (!(75 < j && j < 125) && (i < 75 || i >= 125) && !((60 <= j && j < 70) || (130 <= j && j < 140))) {
                    map.getField()[i][j].changeTexture(Texture.OLIVE_TREE);
                    map.getField()[i][j].setPassability(Texture.OLIVE_TREE.canHaveBuildingAndUnit());
                }
            }
        }
        //grass and dense meadow
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 200; j++) {
                if (j < 100) {
                    map.getField()[i][j].changeTexture(Texture.DENSE_MEADOW);
                    map.getField()[i][j].setPassability(Texture.DENSE_MEADOW.canHaveBuildingAndUnit());
                } else {
                    map.getField()[i][j].changeTexture(Texture.GRASS);
                    map.getField()[i][j].setPassability(Texture.GRASS.canHaveBuildingAndUnit());
                }
            }
        }
        for (int i = 190; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                if (j < 100) {
                    map.getField()[i][j].changeTexture(Texture.DENSE_MEADOW);
                    map.getField()[i][j].setPassability(Texture.DENSE_MEADOW.canHaveBuildingAndUnit());
                } else {
                    map.getField()[i][j].changeTexture(Texture.GRASS);
                    map.getField()[i][j].setPassability(Texture.GRASS.canHaveBuildingAndUnit());
                }
            }
        }
        //20 * 10 iron
        for (int j = 90; j < 110; j++) {
            for (int i = 30; i < 170; i++) {
                if (i < 40 || 160 < i) {
                    map.getField()[i][j].changeTexture(Texture.IRON);
                    map.getField()[i][j].setPassability(Texture.IRON.canHaveBuildingAndUnit());
                }
            }
        }
        // 30 * 30 territory
        setTerritory(map.getField()[35][35], 1);
        setTerritory(map.getField()[35][75], 2);
        setTerritory(map.getField()[35][125], 3);
        setTerritory(map.getField()[35][165], 4);
        setTerritory(map.getField()[165][35], 5);
        setTerritory(map.getField()[165][75], 6);
        setTerritory(map.getField()[165][125], 7);
        setTerritory(map.getField()[165][165], 8);

        map.setTilesPassability();
        return map;
    }

    public static void setTerritory(Tile headQuarter, int territory) {
        map.getHeadQuarters().put(territory, headQuarter);
        for (int i = headQuarter.getX() - 14; i < headQuarter.getX() + 15; i++) {
            for (int j = headQuarter.getY() - 14; j < headQuarter.getY() + 15; j++) {
                map.getField()[i][j].setTerritoryNumber(territory);
            }
        }
    }

    public static Map createMap2() {
        //TODO ...
        return map;
    }

    public static void printMap() {
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                System.out.print(map.getField()[i][j].getTexture().name().substring(0, 2) + "--");
            }
            System.out.println();
        }
    }
}

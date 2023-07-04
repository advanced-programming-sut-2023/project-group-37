package model.game;

public class GenerateMap {

    private static Map map;

    public static Map createMap1() { // all i,j changes
        map = new Map("North vs South");
        //set ground
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                map.getField()[j][i].changeTexture(Texture.GROUND);
                map.getField()[j][i].setPassability(Texture.GROUND.canHaveBuildingAndUnit());
            }
        }
        // river in the middle
        for (int i = 75; i < 125; i++) {
            for (int j = 0; j < 200; j++) {
                map.getField()[j][i].changeTexture(Texture.RIVER);
                map.getField()[j][i].setPassability(Texture.RIVER.canHaveBuildingAndUnit());
            }
        }
        // passable ways in river
        for (int i = 75; i < 125; i++) {
            for (int j = 60; j < 140; j++) {
                if (j < 70 || (90 < j && j < 110) || 130 < j) {
                    map.getField()[j][i].changeTexture(Texture.SHALLOW_POND);
                    map.getField()[j][i].setPassability(Texture.SHALLOW_POND.canHaveBuildingAndUnit());
                }
            }
        }

        //trees
        for (int i = 65; i < 135; i++) {
            for (int j = 0; j < 200; j++) {
                if (!(75 < j && j < 125) && (i < 75 || i >= 125) && !((60 <= j && j < 70) || (130 <= j && j < 140))) {
                    if (j % 2 == 1) {
                        map.getField()[j][i].changeTreeRockTexture(Texture.COCONUT_PALM);
                        map.getField()[j][i].setPassability(Texture.COCONUT_PALM.canHaveBuildingAndUnit());
                    }
                }
            }
        }

        //20 * 10 rocks (can be mined)
        for (int j = 90; j < 110; j++) {
            for (int i = 60; i < 140; i++) {
                if (i < 70 || 130 < i) {
                    map.getField()[j][i].changeTexture(Texture.ROCK);
                    map.getField()[j][i].setPassability(Texture.ROCK.canHaveBuildingAndUnit());
                }
            }
        }

        //grass and dense meadow
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 200; j++) {
                if (j < 100) {
                    map.getField()[j][i].changeTexture(Texture.DENSE_MEADOW);
                    map.getField()[j][i].setPassability(Texture.DENSE_MEADOW.canHaveBuildingAndUnit());
                } else {
                    map.getField()[j][i].changeTexture(Texture.GRASS);
                    map.getField()[j][i].setPassability(Texture.GRASS.canHaveBuildingAndUnit());
                }
            }
        }
        for (int i = 190; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                if (j < 100) {
                    map.getField()[j][i].changeTexture(Texture.DENSE_MEADOW);
                    map.getField()[j][i].setPassability(Texture.DENSE_MEADOW.canHaveBuildingAndUnit());
                } else {
                    map.getField()[j][i].changeTexture(Texture.GRASS);
                    map.getField()[j][i].setPassability(Texture.GRASS.canHaveBuildingAndUnit());
                }
            }
        }
        //stones (mountains)
        for (int i = 10; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                if (!((75 <= i && i < 125) || (30 < i && i < 50) || (150 < i && i < 170)) && ((55 < j && j < 60) ||
                        (140 < j && j < 145))) {
                    map.getField()[j][i].changeTreeRockTexture(Texture.STONE);
                    map.getField()[j][i].setPassability(Texture.STONE.canHaveBuildingAndUnit());
                }
            }
        }

        //20 * 10 iron
        for (int j = 90; j < 110; j++) {
            for (int i = 30; i < 170; i++) {
                if (i < 40 || 160 < i) {
                    map.getField()[j][i].changeTexture(Texture.IRON);
                    map.getField()[j][i].setPassability(Texture.IRON.canHaveBuildingAndUnit());
                }
            }
        }
        // 30 * 30 territory
        setTerritory(map.getField()[35][35], 1);
        setTerritory(map.getField()[75][35], 2);
        setTerritory(map.getField()[125][35], 3);
        setTerritory(map.getField()[165][35], 4);
        setTerritory(map.getField()[35][165], 5);
        setTerritory(map.getField()[75][165], 6);
        setTerritory(map.getField()[125][165], 7);
        setTerritory(map.getField()[165][165], 8);

        map.setTilesPassability();
        return map;
    }

    public static Map createMap2() { // all i,j changes
        map = new Map("Closed Encounters");

        //set ground
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                map.getField()[j][i].changeTexture(Texture.GROUND);
                map.getField()[j][i].setPassability(Texture.GROUND.canHaveBuildingAndUnit());
            }
        }

        for (int i = 0; i < 200; i++) {
            for (int j = 60; j < 70; j++) {
                map.getField()[j][i].changeTexture(Texture.GRASS);
                map.getField()[j][i + 70].changeTexture(Texture.GRASS);
                map.getField()[i][j].changeTexture(Texture.GRASS);
                map.getField()[i + 70][j].changeTexture(Texture.GRASS);
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 200; j++) {
                map.getField()[j][i].changeTexture(Texture.IRON);
                map.getField()[j][200 - i].changeTexture(Texture.IRON);
                map.getField()[i][j].changeTexture(Texture.IRON);
                map.getField()[200 - i][j].changeTexture(Texture.IRON);
            }
        }

        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 200; j++) {
                map.getField()[j][i].changeTexture(Texture.ROCK);
                map.getField()[j][200 - i].changeTexture(Texture.ROCK);
                map.getField()[i][j].changeTexture(Texture.ROCK);
                map.getField()[200 - i][j].changeTexture(Texture.ROCK);
            }
        }

        //trees
        for (int i = 60; i < 130; i++) {
            for (int j = 60; j < 130; j++) {
                if (i % 4 == 1 && j % 4 == 1) {
                    map.getField()[j][i].changeTreeRockTexture(Texture.DATE_PALM);
                    map.getField()[j][i].setPassability(Texture.DATE_PALM.canHaveBuildingAndUnit());
                } else if (i % 4 == 3 && j % 4 == 3) {
                    map.getField()[j][i].changeTreeRockTexture(Texture.STONE);
                    map.getField()[j][i].setPassability(Texture.STONE.canHaveBuildingAndUnit());
                }
            }
        }

        // 30 * 30 territory
        setTerritory(map.getField()[30][30], 1);

        setTerritory(map.getField()[30][100], 2);

        setTerritory(map.getField()[30][170], 3);

        setTerritory(map.getField()[100][30], 4);

        setTerritory(map.getField()[100][170], 5);

        setTerritory(map.getField()[170][30], 6);

        setTerritory(map.getField()[170][100], 7);

        setTerritory(map.getField()[170][170], 8);

        map.setTilesPassability();
        return map;
    }

    public static void setTerritory(Tile headQuarter, int territoryNumber) {
        Territory territory = new Territory(map, territoryNumber, headQuarter);
        map.getKeeps().put(territoryNumber, territory);
        for (int i = headQuarter.getLocationX() - 14; i < headQuarter.getLocationX() + 15; i++) {
            for (int j = headQuarter.getLocationY() - 14; j < headQuarter.getLocationY() + 15; j++) {
                map.getField()[i][j].setTerritory(territory);
            }
        }
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

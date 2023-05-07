package model.game;

public class GenerateMap {
    private static final Map map = new Map(200);


    public static Map createMap1() {
        // river in the middle
        for (int i = 75; i < 125; i++) {
            for (int j = 0; j < 200; j++) {
                map.getMap()[i][j].changeTexture(Texture.RIVER);
            }
        }
        // passable ways in river
        for (int i = 75; i < 125; i++) {
            for (int j = 0; j < 200; j++) {
                if((40 < j && j < 60) || (90 < j && j < 110) || (140 < j && j < 160)){
                    map.getMap()[i][j].changeTexture(Texture.SHALLOW_POND);
                }
            }
        }
        //rocks (mountains) : not passable
        for (int i = 0; i < 200; i++) {
            for (int j = 30; j < 35; j++) {
                if(!(75 <= i && i < 125)){
                    map.getMap()[i][j].changeTexture(Texture.ROCK);
                }
            }
            for (int j = 165; j < 170; j++) {
                if(!(75 <= i && i < 125)){
                    map.getMap()[i][j].changeTexture(Texture.ROCK);
                }
            }
        }


        return map;
    }
}

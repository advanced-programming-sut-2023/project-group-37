package model.game;

public class GenerateMap {
    private static final Map map = new Map("North vs South");

    /*public static void main(String[] args) {
        createMap1();
        printMap();
    }*/
    public static Map createMap1() {
        //set ground
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                map.getMap()[i][j].changeTexture(Texture.GROUND);
            }
        }
        // river in the middle
        for (int i = 75; i < 125; i++) {
            for (int j = 0; j < 200; j++) {
                map.getMap()[i][j].changeTexture(Texture.RIVER);
            }
        }
        // passable ways in river
        for (int i = 75; i < 125; i++) {
            for (int j = 60; j < 140; j++) {
                if(j < 70 || (90 < j && j < 110) || 130 < j){
                    map.getMap()[i][j].changeTexture(Texture.SHALLOW_POND);
                }
            }
        }
        //rocks (mountains)
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                if(!((75 <= i && i < 125) || (30 < i && i < 50) || (150 < i && i < 170)) && ((55 < j && j < 60) ||
                        (140 < j && j < 145))){
                    map.getMap()[i][j].changeTexture(Texture.STONE);
                }
            }
        }
        //10 * 10 stones (can be mined)
        for (int j = 90; j < 110 ; j++) {
            for (int i = 60; i < 140; i++) {
                if(i < 70 || 130 < i){
                    map.getMap()[i][j].changeTexture(Texture.ROCK);
                }
            }
        }
        //trees and grass
        for (int i = 65; i < 75; i++) {
            for (int j = 0; j < 200; j++) {
                if(!(75 < j && j < 125)){
                    map.getMap()[i][j].changeTexture(Texture.OLIVE_TREE);
                }
            }
        }
        for (int i = 125; i < 135; i++) {
            for (int j = 0; j < 200; j++) {
                if(!(75 < j && j < 125)){
                    map.getMap()[i][j].changeTexture(Texture.OLIVE_TREE);
                }
            }
        }
        // 30 * 30 territory
        return map;
    }


    public static Map createMap2(){
        //TODO ...
        return map;
    }

    public static void printMap(){
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                System.out.print(map.getMap()[i][j].getTexture().name() + "-----------");
            }
            System.out.println();
        }
    }
}

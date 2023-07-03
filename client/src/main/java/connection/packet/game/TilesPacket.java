package connection.packet.game;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.buildings.Building;
import model.game.Texture;
import model.game.Tile;
import model.people.MilitaryUnit;

import java.util.ArrayList;

public class TilesPacket extends Packet {
    private final ArrayList<TilePacket> tiles;

    public TilesPacket(ArrayList<Tile> modifiedTiles) {
        super.type = PacketType.TILES_PACKET;
        this.tiles = new ArrayList<>();
        for (Tile tile : modifiedTiles)
            this.tiles.add(new TilePacket(tile));
    }

    public static class TilePacket {
        private final Building building;
        private final ArrayList<MilitaryUnit> militaryUnits;
        private final Texture texture;

        public TilePacket(Tile tile) {
            this.building = tile.getBuilding();
            this.militaryUnits = tile.getMilitaryUnits();
            this.texture = tile.getTexture();
        }

        public Building getBuilding() {
            return building;
        }

        public ArrayList<MilitaryUnit> getMilitaryUnits() {
            return militaryUnits;
        }

        public Texture getTexture() {
            return texture;
        }
    }

    public ArrayList<TilePacket> getTiles() {
        return tiles;
    }
}

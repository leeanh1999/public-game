package avatar.model;

import avatar.lucky.DialLucky;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import avatar.db.DbManager;
import avatar.lucky.DialLuckyManager;
import lombok.Getter;

@Getter
public class GameData {
    private static final GameData instance = new GameData();

    public static final GameData getInstance() {
        return instance;
    }

    private List<ImageInfo> itemImageDatas = new ArrayList<>();
    private List<ImageInfo> farmImageDatas = new ArrayList<>();
    private List<MapItem> mapItems = new ArrayList<>();
    private List<MapItemType> mapItemTypes = new ArrayList<>();

    public void load() {
        loadItemImageData();
        loadFarmImageData();
        loadMapItem();
        loadMapItemType();
    }

    public void loadItemImageData() {
        try {
            itemImageDatas.clear();
            PreparedStatement ps = DbManager.getInstance().getConnectionForGame()
                    .prepareStatement("SELECT * FROM `item_image_data`;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int bigImageID = rs.getInt("image_id");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int w = rs.getInt("w");
                int h = rs.getInt("h");
                itemImageDatas.add(ImageInfo.builder().id(id).bigImageID(bigImageID).x(x).y(y).w(w).h(h).build());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void loadFarmImageData() {
        try {
            farmImageDatas.clear();
            PreparedStatement ps = DbManager.getInstance().getConnectionForGame().prepareStatement("SELECT * FROM `farm_image_data`;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int bigImageID = rs.getInt("image_id");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int w = rs.getInt("w");
                int h = rs.getInt("h");
                farmImageDatas.add(
                        ImageInfo.builder().id(id).bigImageID(bigImageID).x(x).y(y).w(w).h(h).build());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void loadMapItem() {
        try {
            mapItems.clear();
            PreparedStatement ps = DbManager.getInstance().getConnectionForGame()
                    .prepareStatement("SELECT * FROM `map_item`;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                short id = rs.getShort("id");
                short typeID = rs.getShort("type_id");
                byte type = rs.getByte("type");
                byte x = rs.getByte("x");
                byte y = rs.getByte("y");
                mapItems.add(MapItem.builder().id(id).typeID(typeID).type(type).x(x).y(y).build());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void loadMapItemType() {
        try {
            mapItemTypes.clear();
            PreparedStatement ps = DbManager.getInstance().getConnectionForGame()
                    .prepareStatement("SELECT * FROM `map_item_type`;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                short id = rs.getShort("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                short imageID = rs.getShort("image");
                short iconID = rs.getShort("icon");
                short priceCoin = rs.getShort("price_coin");
                short priceGold = rs.getShort("price_gold");
                byte buy = rs.getByte("buy");
                byte dx = rs.getByte("dx");
                byte dy = rs.getByte("dy");
                JSONArray jPosition = (JSONArray) JSONValue.parse(rs.getString("position"));
                int size = jPosition.size();
                List<Position> positions = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    JSONObject obj = (JSONObject) jPosition.get(i);
                    byte x = ((Long) obj.get("x")).byteValue();
                    byte y = ((Long) obj.get("y")).byteValue();
                    Position p = Position.builder()
                            .x(x)
                            .y(y)
                            .build();
                    positions.add(p);
                }
                mapItemTypes.add(MapItemType.builder().id(id).name(name).des(description).imgID(imageID).iconID(iconID)
                        .priceXu(priceCoin).priceLuong(priceGold).buy(buy).dx(dx).dy(dy).listNotTrans(positions)
                        .build());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public MapItemType findMapItemType(int idType) {
        for (MapItemType mapItemType : mapItemTypes) {
            if (mapItemType.getId() == idType) {
                return mapItemType;
            }
        }
        return null;
    }
}

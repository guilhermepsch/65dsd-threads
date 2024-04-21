package factories;

import model.Map;
import model.Node;
import java.util.List;

public class MapFactory {

    public Map createMap(Node[][] map, List<Node> entrances, List<Node> exits) {
        return new Map(map, entrances.toArray(new Node[0]), exits.toArray(new Node[0]));
    }
}

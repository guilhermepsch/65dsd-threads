import factories.MapFactory;
import factories.NodeFactory;
import factories.PathFactory;
import model.Car;
import model.Map;
import model.Node;
import service.MapParser;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            MapParser mapParser = new MapParser(new NodeFactory(), new MapFactory());
            Map map = mapParser.createMapFromFile(new File(Main.class.getResource("/malha-exemplo-3.txt").getPath()));
            PathFactory pathFactory = new PathFactory(map);

//            printMapInfo(map);

            Car car = new Car(map.entrances()[1], map, pathFactory, 200);
            car.start();
        } catch (IOException e) {
            System.err.println("Error reading file!");
            e.printStackTrace();
        }
    }

    private static void printMapInfo(Map map) {
        Node[][] matrix = map.map();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Node n = matrix[i][j];
                System.out.print(n.isCrossRoadStart() + " ");
            }
            System.out.println();
        }
    }
}

import model.Car;
import model.Node;
import model.Map;

import java.io.File;
import java.io.IOException;

import static service.MapParser.createMap;

public class Main {
    public static void main(String[] args) {
        try {
            Map map = createMap(new File(Main.class.getResource("/malha-exemplo-1.txt").getPath()));

//            Node[][] matrix = map.getMap();
//            for (int i = 0; i < matrix.length; i++) {
//                for (int j = 0; j < matrix[i].length; j++) {
//                    Node n = matrix[i][j];
//                    System.out.print(n.isCrossroadExit() + " ");
//                }
//                System.out.println();
//            }

            Car car = new Car(map.getEntrances()[1], map);
            car.start();
        } catch (IOException e) {
            System.err.println("Error reading file!");
            e.printStackTrace();
        }
    }
}
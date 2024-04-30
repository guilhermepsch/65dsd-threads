import factories.MapFactory;
import factories.NodeFactory;
import factories.PathFactory;
import model.Car;
import model.Map;
import model.Node;
import service.MapParser;
import view.CarSimulationView;
import view.MapDisplay;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            MapParser mapParser = new MapParser(new NodeFactory(), new MapFactory());
            Map map = mapParser.createMapFromFile(new File(Main.class.getResource("/malha-exemplo-3.txt").getPath()), false);
            PathFactory pathFactory = new PathFactory(map);
            MapDisplay mapDisplay = new MapDisplay(map, pathFactory);
            CarSimulationView carSimView = new CarSimulationView(mapDisplay);
            carSimView.run();
        } catch (IOException e) {
            System.err.println("Error reading file!");
            e.printStackTrace();
        }
    }
}

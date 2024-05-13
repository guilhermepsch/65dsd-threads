import javax.swing.*;

import factories.MapFactory;
import factories.NodeFactory;
import factories.PathFactory;
import model.Map;
import service.MapParser;
import view.CarSimulationView;
import view.ExclusionOption;
import view.MapDisplay;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        boolean exclusionStrategyOption = ExclusionOption.showDialog();
        try {
            MapParser mapParser = new MapParser(new NodeFactory(), new MapFactory());
            Map map = mapParser.createMapFromFile(new File(Main.class.getResource("/malha-exemplo-1.txt").getPath()), exclusionStrategyOption);
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

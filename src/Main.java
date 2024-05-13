import javax.swing.*;

import factories.MapFactory;
import factories.NodeFactory;
import factories.PathFactory;
import model.Map;
import service.MapParser;
import view.CarSimulationView;
import view.MapDisplay;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        boolean exclusionStrategyOption;
        String[] options = {"Monitor", "Semaphore (Default)"};
        int choice = JOptionPane.showOptionDialog(null,
                "Selecione uma opção de exclusão mútua:",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        System.out.println(choice);
        exclusionStrategyOption = (choice == 0);
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

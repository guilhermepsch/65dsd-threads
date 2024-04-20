package service;

import model.Map;
import model.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapParser {

    public static Map createMap(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        int numRows = Integer.parseInt(reader.readLine());
        int numCols = Integer.parseInt(reader.readLine());

        Node[][] map = new Node[numRows][numCols];

        String line;
        int row = 0;
        ArrayList<Node> entrances = new ArrayList<>();
        ArrayList<Node> exits = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\s+");
            for (int col = 0; col < numCols; col++) {
                int direction = Integer.parseInt(parts[col]);
                boolean isStart = (direction == Node.UP && row == numRows - 1)
                        || (direction == Node.DOWN && row == 0)
                        || (direction == Node.RIGHT && col == 0)
                        || (direction == Node.LEFT && col == numCols - 1);
                boolean isEnd = (direction == Node.UP && row == 0)
                        || (direction == Node.DOWN && row == numRows - 1)
                        || (direction == Node.LEFT && col == 0)
                        || (direction == Node.RIGHT && col == numCols - 1);
                map[row][col] = new Node(direction, isStart, isEnd);
                if (isStart){
                    entrances.add(map[row][col]);
                }
                if (isEnd){
                    exits.add(map[row][col]);
                }
            }
            row++;
        }
        reader.close();
        return new Map(map, entrances.toArray(new Node[0]), exits.toArray(new Node[0]));
    }
}

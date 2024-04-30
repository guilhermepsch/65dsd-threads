package view;

import javax.swing.*;
import java.awt.*;

public class CarSimulationView extends JFrame {

    private MapDisplay mapDisplay;
    private JTextField carQuantityField;
    private JButton startButton;
    private JButton stopButton; // New stop button
    private int carQuantity;

    public CarSimulationView(MapDisplay mapDisplay) {
        this.mapDisplay = mapDisplay;
        setTitle("Car Simulation");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JLabel carQuantityLabel = new JLabel("Car Quantity:");
        controlPanel.add(carQuantityLabel);

        carQuantityField = new JTextField(10);
        controlPanel.add(carQuantityField);

        startButton = new JButton("Start Simulation");
        startButton.addActionListener(e -> startSimulation());
        controlPanel.add(startButton);

        stopButton = new JButton("Stop Simulation"); // Initialize stop button
        stopButton.addActionListener(e -> stopSimulation()); // Add action listener
        controlPanel.add(stopButton); // Add stop button to control panel

        // Add the map display panel instead of the map display frame
        add(mapDisplay.getPanel(), BorderLayout.CENTER);

        add(controlPanel, BorderLayout.NORTH);
    }

    private void startSimulation() {
        try {
            carQuantity = Integer.parseInt(carQuantityField.getText());
            if (carQuantity < 0) {
                JOptionPane.showMessageDialog(this, "Invalid car quantity. Please enter a positive integer.");
                return;
            }
            mapDisplay.startSimulation(carQuantity);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer.");
        }
    }

    private void stopSimulation() {
        mapDisplay.stopSimulation();
    }

    public void run() {
        setVisible(true);
    }
}


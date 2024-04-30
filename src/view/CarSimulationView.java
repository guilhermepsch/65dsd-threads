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

        JLabel carQuantityLabel = new JLabel("Quantidade de Carros:");
        controlPanel.add(carQuantityLabel);

        carQuantityField = new JTextField(10);
        controlPanel.add(carQuantityField);

        startButton = new JButton("Iniciar");
        startButton.addActionListener(e -> startSimulation());
        controlPanel.add(startButton);

        stopButton = new JButton("Parar");
        stopButton.addActionListener(e -> stopSimulation());
        controlPanel.add(stopButton);

        add(mapDisplay.getPanel(), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);
    }

    private void startSimulation() {
        try {
            carQuantity = Integer.parseInt(carQuantityField.getText());
            if (carQuantity < 0) {
                JOptionPane.showMessageDialog(this, "Quantidade inválida de carros, insira um número maior que 0.");
                return;
            }
            mapDisplay.startSimulation(carQuantity);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida. Insira um inteiro válido");
        }
    }

    private void stopSimulation() {
        mapDisplay.stopSimulation();
    }

    public void run() {
        setVisible(true);
    }
}


package view;

import javax.swing.*;

public class ExclusionOption {

    public static boolean showDialog() {
        boolean exclusionStrategyOption = true;
        String[] options = {"Semaphore (Default)", "Monitor"};
        int choice = JOptionPane.showOptionDialog(null,
                "Select an option:",
                "Option Dialog",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (choice == 1) {
            exclusionStrategyOption = false;
        }
        return exclusionStrategyOption;
    }
}

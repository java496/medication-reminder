import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

// Main Class
public class MedicineReminderApp {
    // List to store all medications
    private static ArrayList<Medication> medications = new ArrayList<>();

    public static void main(String[] args) {
        // Launch the GUI
        SwingUtilities.invokeLater(() -> new MedicineReminderApp().createMainGUI());
    }

    // Create Main GUI
    private void createMainGUI() {
        JFrame frame = new JFrame("Medicine Reminder App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new FlowLayout());

        // Add components
        JButton addButton = new JButton("Add Medication");
        JButton viewButton = new JButton("View Medications");
        JButton startRemindersButton = new JButton("Start Reminders");

        // Add action listeners
        addButton.addActionListener(e -> createAddMedicationGUI());
        viewButton.addActionListener(e -> createViewMedicationsGUI());
        startRemindersButton.addActionListener(e -> startReminders());

        // Add buttons to frame
        frame.add(addButton);
        frame.add(viewButton);
        frame.add(startRemindersButton);

        frame.setVisible(true);
    }

    // GUI to Add Medication
    private void createAddMedicationGUI() {
        JFrame addFrame = new JFrame("Add Medication");
        addFrame.setSize(400, 300);
        addFrame.setLayout(new GridLayout(5, 2));

        // Input fields
        JLabel nameLabel = new JLabel("Medication Name:");
        JTextField nameField = new JTextField();

        JLabel dosageLabel = new JLabel("Dosage:");
        JTextField dosageField = new JTextField();

        JLabel timeLabel = new JLabel("Time (HH:mm):");
        JTextField timeField = new JTextField();

        JLabel frequencyLabel = new JLabel("Frequency (hours):");
        JTextField frequencyField = new JTextField();

        JButton saveButton = new JButton("Save");

        // Save button action
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String dosage = dosageField.getText();
            String time = timeField.getText();
            int frequency;
            try {
                frequency = Integer.parseInt(frequencyField.getText());
                Medication medication = new Medication(name, dosage, time, frequency);
                medications.add(medication);
                JOptionPane.showMessageDialog(addFrame, "Medication added successfully!");
                addFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addFrame, "Invalid frequency! Please enter a number.");
            }
        });

        // Add components to frame
        addFrame.add(nameLabel);
        addFrame.add(nameField);
        addFrame.add(dosageLabel);
        addFrame.add(dosageField);
        addFrame.add(timeLabel);
        addFrame.add(timeField);
        addFrame.add(frequencyLabel);
        addFrame.add(frequencyField);
        addFrame.add(saveButton);

        addFrame.setVisible(true);
    }

    // GUI to View Medications
    private void createViewMedicationsGUI() {
        JFrame viewFrame = new JFrame("View Medications");
        viewFrame.setSize(400, 300);
        viewFrame.setLayout(new BorderLayout());

        // Text area to display medications
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        if (medications.isEmpty()) {
            textArea.setText("No medications added yet.");
        } else {
            for (Medication medication : medications) {
                textArea.append(medication.toString() + "\n");
            }
        }

        // Add components to frame
        viewFrame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        viewFrame.setVisible(true);
    }

    // Start Reminders
    private void startReminders() {
        if (medications.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No medications to remind!");
            return;
        }

        Timer timer = new Timer();
        for (Medication medication : medications) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(null,
                            "Reminder: Take " + medication.getName() + " (" + medication.getDosage() + ") at " + medication.getTime());
                }
            }, 0, medication.getFrequency() * 3600 * 1000L); // Frequency in milliseconds
        }

        JOptionPane.showMessageDialog(null, "Reminders started!");
    }
}

// Medication Class
class Medication {
    private String name;
    private String dosage;
    private String time; // In HH:mm format
    private int frequency; // Frequency in hours

    public Medication(String name, String dosage, String time, int frequency) {
        this.name = name;
        this.dosage = dosage;
        this.time = time;
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public String getDosage() {
        return dosage;
    }

    public String getTime() {
        return time;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Dosage: " + dosage + ", Time: " + time + ", Frequency: " + frequency + " hours";
    }
}

package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater() to ensure thread safety for GUI
        // start the GUI window
        SwingUtilities.invokeLater(() -> {
            new ColorCircleFrame().setVisible(true);
        });
    }
}

// inherit from JFrame TO create window
// Q1: The GUI (Graphical User Interface) to your Java program 
// consists of a circle, one text entry field (dropdown choices) and a text output field.
class ColorCircleFrame extends JFrame {
    private CirclePanel circlePanel;
    private JComboBox<String> colorDropdown;
    private JTextField outputField; // JTextField is the text output field

    public ColorCircleFrame() {
        setTitle("Color Circle Window");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         // Center on screen
        setLocationRelativeTo(null);
        // borderlayout will have 5 regions like north east...
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // The Dropdown Text Entry Field
        String[] colors = {"Select a Color", "Red", "Blue", "Green"};
        colorDropdown = new JComboBox<>(colors);
        
        // The Text Output Field
        outputField = new JTextField(20);
        outputField.setEditable(false);
        outputField.setText("No color selected");
        outputField.setHorizontalAlignment(JTextField.CENTER);

        // Add components to the control panel
        controlPanel.add(new JLabel("Choose:"));
        controlPanel.add(colorDropdown);
        controlPanel.add(outputField);

        // Initially the circle
        circlePanel = new CirclePanel();

        // Add panels to the Frame
        add(controlPanel, BorderLayout.NORTH);
        add(circlePanel, BorderLayout.CENTER);

        // Add Event Listener
        // Q4: You choose a color in the text entry dropdown box.
        colorDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedColorName = (String) colorDropdown.getSelectedItem();
                
                // Update the text output field
                outputField.setText("Color: " + selectedColorName);

                // choose the actual color
                // Q3: The dropdown entry box contains 
                // a selection of different colors loke “Red, Blue, Green”.
                Color colorToFill = null;
                switch (selectedColorName) {
                    case "Red":
                        colorToFill = Color.RED;
                        break;
                    case "Blue":
                        colorToFill = Color.BLUE;
                        break;
                    case "Green":
                        colorToFill = Color.GREEN;
                        break;
                    default:
                        colorToFill = null; // "Select a Color" results in clear
                        outputField.setText("No color selected");
                        break;
                }

                // Update the circle panel and refresh
                circlePanel.setFillColor(colorToFill);
            }
        });
    }
}

// The Custom Panel to draw the circle
class CirclePanel extends JPanel {
    private Color fillColor;

    // Q2: Initially the circle is clear (no color inside)
    public CirclePanel() {
        this.fillColor = null;
    }

    // Method to update color and redraw
    public void setFillColor(Color c) {
        this.fillColor = c;
        repaint(); // Triggers paintComponent immediately
    }

    // Override paintComponent to draw the circle
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Convert to Graphics2D for smoother lines (Anti-aliasing)
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate center and size
        int diameter = Math.min(getWidth(), getHeight()) - 50; // Leave some padding
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        // If a color is selected, fill the circle
        // Q5: In response to the selection, 
        // the circle is filled with the appropriate color 
        // and the text output field shows the name of this color.
        if (fillColor != null) {
            g2d.setColor(fillColor);
            g2d.fillOval(x, y, diameter, diameter);
        }

        // draw the black outline
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3)); // Make outline slightly thicker
        g2d.drawOval(x, y, diameter, diameter);
    }
}
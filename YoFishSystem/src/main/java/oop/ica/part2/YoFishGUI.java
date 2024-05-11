/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package oop.ica.part2;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;


public class YoFishGUI extends JFrame{
    private JTable table;
    private JLabel imageLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new YoFishGUI().createAndShowGUI();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            for (int row = 0; row < table.getRowCount(); row++) {
                for (int col = 0; col < table.getColumnCount(); col++) {
                    writer.write(table.getValueAt(row, col).toString());
                    if (col < table.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAndShowGUI() throws IOException {
        this.setTitle("Yo-Fish");
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setSize(1600, 600);
        this.getContentPane().setBackground(new Color(230, 230, 230)); // Set light grey background

        // Adjust the proportions in the BorderLayout
        this.setLayout(new BorderLayout(52, 52));

        // Add a border around the frame
        ((JComponent) this.getContentPane()).setBorder(BorderFactory.createEmptyBorder(72, 72, 72, 72));

        String[] columnNames = {"ID", "ITEM", "PRICE", "STOCK", "MAX SIZE", "LOW TEMP", "HIGH TEMP"};
        ArrayList<String[]> fishData = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("yo-fish.txt"));
        BufferedReader reader2 = new BufferedReader(new FileReader("pondlife.txt"));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] fishInfo = line.split(",");
            fishData.add(fishInfo);
        }
        while ((line = reader2.readLine()) != null) {
            String[] fishInfo = line.split(",");
            String[] newFishInfo = new String[fishInfo.length - 1];
            for (int i = 0, j = 0; i < fishInfo.length; i++) {
                if (i != 2) {
                    newFishInfo[j++] = fishInfo[i];
                }
            }
            fishData.add(newFishInfo);
        }
        reader.close();

        String[][] data = new String[fishData.size()][];
        for (int i = 0; i < fishData.size(); i++) {
            data[i] = fishData.get(i);
        }

        table = new JTable(data, columnNames);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                int row = table.getSelectedRow();
                if (row != -1) { // -1 means no row is selected
                    String stockvalue = (String) table.getValueAt(row, 3);
                    String itemname = (String) table.getValueAt(row, 1);
                    if (Integer.parseInt(stockvalue) < 5 ) {
                        JOptionPane.showMessageDialog(table, itemname+" has "+stockvalue +" left", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Add $ before the price value
                if (column == 2) {
                    value = "$ " + value;
                }

                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Set background color of the selected row to blue
                if (isSelected) {
                    c.setBackground(Color.BLUE);
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(230, 230, 230)); // Very light grey
                }

                // Select the picture when a row is selected
                if (isSelected) {
                    String selectedFishId = (String) table.getValueAt(row, 0);
                    String imagePath = "./pics/" + selectedFishId + ".jpg";
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        // Load the image and scale it to fit the label
                        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH));
                        imageLabel.setIcon(icon);
                    } else {
                        imageLabel.setText("No Image Available");
                    }
                }

                return c;
            }
        });

        // Remove the grid lines in the text areas but let them remain in the headings
        table.setShowGrid(false);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); // Add bottom border to header

        // Set the column headings to bold
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));

        JScrollPane infoScrollPane = new JScrollPane(table);

        this.add(infoScrollPane, BorderLayout.CENTER);

        imageLabel = new JLabel();
        JLabel imageHeading = new JLabel("Item Photo", SwingConstants.CENTER);
        JLabel fishPhotoHeading = new JLabel("Fish Photo", SwingConstants.CENTER);
        JPanel imagePanel = new JPanel(new BorderLayout()); // Set BorderLayout for imagePanel
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        imagePanel.add(imageLabel, BorderLayout.CENTER); // Add imageLabel to CENTER position

        // Set the size of the photo holder to 1/4 of the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        imagePanel.setPreferredSize(new Dimension(screenSize.width / 4, screenSize.height / 4));

        JPanel photoHeadingPanel = new JPanel(new BorderLayout());
        photoHeadingPanel.add(fishPhotoHeading, BorderLayout.NORTH);
        photoHeadingPanel.add(imagePanel, BorderLayout.CENTER);

        // Buttons placed below the photo holder
        JButton buyButton = new JButton("Buy");
        JButton addButton = new JButton("Add");
        JButton quitButton = new JButton("Quit");
        quitButton.setBackground(Color.RED);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(buyButton);
        buttonPanel.add(addButton);
        buttonPanel.add(quitButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(photoHeadingPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(bottomPanel, BorderLayout.EAST);
        //Add close action lister
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
                System.exit(0);
            }
        });
        // Action listener for Buy button
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyItem();
            }
        });

        // Action listener for Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        this.setVisible(true);
    }
    
    private void buyItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item buy.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedFishName = (String) table.getValueAt(selectedRow, 1);
        String selectedFishPrice = (String) table.getValueAt(selectedRow, 2);
        String selectedFishStock = (String) table.getValueAt(selectedRow, 3);

        // Display pop-up dialog to request quantity
        String quantityInput = JOptionPane.showInputDialog(this, "Please enter the quantity you wish to buy of Item: " + selectedFishName + " (" + selectedFishStock + " available)", "Buy Item", JOptionPane.QUESTION_MESSAGE);
        if (quantityInput == null || quantityInput.isEmpty()) {
            // User clicked cancel or closed the dialog
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityInput);
            int currentStock = Integer.parseInt(selectedFishStock);
            if (quantity < 1 || quantity > currentStock) {
                JOptionPane.showMessageDialog(this, "Please enter a quantity between 1 and " + currentStock + ".", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update stock quantity
            int newStock = currentStock - quantity;
            table.setValueAt(String.valueOf(newStock), selectedRow, 3);

            // Show confirmation message
            String confirmationMessage = "Confirmation of Sell\n\n";
            confirmationMessage += "Item: " + selectedFishName + "\n";
            confirmationMessage += "Units Added: " + quantity + "\n";
            confirmationMessage += "New Stock Quantity: " + newStock;
            JOptionPane.showMessageDialog(this, confirmationMessage, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to add stock.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedFishName = (String) table.getValueAt(selectedRow, 1);
        String selectedFishStock = (String) table.getValueAt(selectedRow, 3);

        // Display pop-up dialog to request quantity
        String quantityInput = JOptionPane.showInputDialog(this, "Please enter the quantity you wish to add of Item: " + selectedFishName + " (" + selectedFishStock + " available)", "Add Stock", JOptionPane.QUESTION_MESSAGE);
        if (quantityInput == null || quantityInput.isEmpty()) {
            // User clicked cancel or closed the dialog
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityInput);
            if (quantity < 5 || quantity > 20) {
                JOptionPane.showMessageDialog(this, "Please enter a quantity between 5 and 20.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update stock quantity
            int newStock = Integer.parseInt(selectedFishStock) + quantity;
            table.setValueAt(String.valueOf(newStock), selectedRow, 3);

            // Show confirmation message
            String confirmationMessage = "Confirmation of Added Stock\n\n";
            confirmationMessage += "Item: " + selectedFishName + "\n";
            confirmationMessage += "Units Added: " + quantity + "\n";
            confirmationMessage += "New Stock Quantity: " + newStock;
            JOptionPane.showMessageDialog(this, confirmationMessage, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

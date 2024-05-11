/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.ica.part2;

import javax.swing.table.AbstractTableModel;
import java.util.Map;
import java.util.Set;

public class YoFishTableModel extends AbstractTableModel {
    private final Map<Integer, YoFishItem> fishMap;
    private final String[] columnNames = {"ID", "ITEM", "PRICE", "STOCK", "MAX SIZE", "LOW TEMP", "HIGH TEMP"};

    public YoFishTableModel(Map<Integer, YoFishItem> fishMap) {
        this.fishMap = fishMap;
    }

    @Override
    public int getRowCount() {
        return fishMap.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // Get the keys (IDs) from the fishMap
        Set<Integer> keys = fishMap.keySet();
        // Convert the set of keys to an array
        Integer[] keyArray = keys.toArray(new Integer[0]);
        // Get the fish using the key from the array
        YoFishItem fish = fishMap.get(keyArray[rowIndex]);
        switch (columnIndex) {
            case 0:
                return fish.getId();
            case 1:
                return fish.getItem();
            case 2:
                return fish.getPrice();
            case 3:
                return fish.getStock();
            case 4:
                return  fish.getMaxSize();
            case 5:
                return fish.getLowTemp();
            case 6:
                return fish.getHighTemp();
            default:
                return null;
        }
    }
}

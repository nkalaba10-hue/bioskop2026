/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.client.components;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import rs.ac.bg.fon.ai.communication.model.Hall;

/**
 *
 * @author nkala
 */
public class TableModelHall extends AbstractTableModel {

    private List<Hall> halls;
    private final String[] columns = {"ID", "Naziv", "Kapacitet"};

    public TableModelHall(List<Hall> halls) {
        this.halls = halls;
    }

    @Override
    public int getRowCount() {
        return halls.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Hall h = halls.get(rowIndex);
        return switch (columnIndex) {
            case 0 ->
                h.getId();
            case 1 ->
                h.getName();
            case 2 ->
                h.getCapacity();
            default ->
                null;
        };
    }

    public void addHall(Hall h) {
        halls.add(h);
        fireTableRowsInserted(halls.size() - 1, halls.size() - 1);
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
        fireTableDataChanged();
    }

    public void updateHall(Hall updatedHall) {
        for (int i = 0; i < halls.size(); i++) {
            Hall hall = halls.get(i);
            if (hall.getId() == updatedHall.getId()) {
                hall.setName(updatedHall.getName());
                hall.setCapacity(updatedHall.getCapacity());
                fireTableRowsUpdated(i, i);
                break;
            }
        }
    }

    public void removeHall(Hall hall) {
        for (int i = 0; i < halls.size(); i++) {
            if (halls.get(i).equals(hall)) {
                halls.remove(i);
                fireTableRowsDeleted(i, i);
                break;
            }
        }
    }

    public Hall getHallAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < halls.size()) {
            return halls.get(rowIndex);
        }
        return null;
    }
}

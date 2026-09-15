/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.client.components;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import rs.ac.bg.fon.ai.communication.model.Projection;

/**
 *
 * @author nkala
 */
public class TableModelProjection extends AbstractTableModel {

    private final String[] columnNames = {
        "Film", "Sala", "Datum", "Vreme pocetka", "Trajanje", "Prodate karte", "Akcija"
    };

    private final Class<?>[] columnClasses = {
        String.class, String.class, String.class, String.class, String.class, String.class, Object.class
    };

    private List<Projection> data; // Projection je tvoja klasa koja drži podatke o projekciji

    public TableModelProjection(List<Projection> data) {
        this.data = data;
    }

    public TableModelProjection() {
    }

    @Override
    public int getRowCount() {
        return data.size();
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
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Projection p = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getFilm();
            case 1:
                return p.getHall().getName();
            case 2:
                return p.getDate();
            case 3:
                return p.getTime();
            case 4:
                return p.getFilm().getDuration();
            case 5:
                return p.getSoldTickets() + "/" + p.getHall().getCapacity();
            case 6:
                return "Kupi kartu"; // dugme
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 6; // samo dugme je klikabilno
    }

    public void addProjection(Projection p) {
        data.add(p);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    public List<Projection> getHalls() {
        return data;
    }

    public Projection getProjectionAt(int selectedRow) {
        return data.get(selectedRow);
    }

    public void setProjections(List<Projection> projections) {
        this.data = projections;
        fireTableDataChanged();
    }

    public void editProjection(Projection projectionToEdit) {

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).equals(projectionToEdit)) { // Pretpostavljam da Projection klasa ima equals metodu
                data.set(i, projectionToEdit);
                fireTableRowsUpdated(i, i);
                break;
            }
        }
    }

    public void removeProjection(Projection projection) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == projection.getId()) {
                data.remove(i);
                fireTableRowsDeleted(i, i);
                break;
            }
        }
    }

}

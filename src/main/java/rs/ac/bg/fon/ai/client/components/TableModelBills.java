/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.client.components;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import rs.ac.bg.fon.ai.communication.model.Bill;

/**
 *
 * @author nkala
 */
public class TableModelBills extends AbstractTableModel {

    private final String[] columnNames = {"ID", "Datum", "Saved By", "Iznos", "Detalji"};
    private List<Bill> data;

    public TableModelBills(List<Bill> data) {
        this.data = data;
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        Bill b = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return b.getId();
            case 1:
                return b.getDateTime();
            case 2:
                return b.getSavedBy();
            case 3:
                return b.getTotalAmount();
            case 4:
                return "Prikaži detalje";
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;
    }

    public Bill getBillAt(int row) {
        return data.get(row);
    }
    
    public void add(Bill b){
        data.add(b);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    public void setBills(List<Bill> bills) {
        this.data = bills;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.client.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import rs.ac.bg.fon.ai.client.forms.BillDialog;
import rs.ac.bg.fon.ai.client.logic.Controller;
import rs.ac.bg.fon.ai.communication.model.Bill;

/**
 *
 * @author nkala
 */
public class ButtonEditorBills extends DefaultCellEditor {

    private JButton button;
    private String label;
    private boolean clicked;
    private JTable table;

    public ButtonEditorBills(JCheckBox checkBox, JTable table) {
        super(checkBox);
        button = new JButton();
        this.table = table;
        button.setOpaque(true);
        button.addActionListener((ActionEvent e) -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Bill bill = ((TableModelBills) table.getModel()).getBillAt(selectedRow);
                
                try {
                    // Učitaj kompletne podatke o računu (sa ticketima)
                    Bill fullBill = loadFullBillData(bill.getId());
                    
                    // Otvori dijalog za izmenu
                    BillDialog dialog = new BillDialog(fullBill);
                    dialog.setVisible(true);
                    
                    // Osveži tabelu ako je bilo izmena
                    if (dialog.isSaved()) {
                        refreshTableData();
                    }
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // Pojednostavljena obrada greške
                    javax.swing.JOptionPane.showMessageDialog(null, 
                        "Greška pri učitavanju računa: " + ex.getMessage(), 
                        "Greška", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        clicked = false;
        return label;
    }

    private Bill loadFullBillData(Long billId) throws Exception {
        // Uzmi sve račune i nađi traženi
        List<Bill> allBills = Controller.getInstance().getAllBills();
        for (Bill b : allBills) {
            if (b.getId().equals(billId)) {
                // Učitaj tikete za ovaj račun (ako već nisu učitani)
                if (b.getTickets() == null || b.getTickets().isEmpty()) {
                    // Ako nema učitane tikete, možda treba da pozoveš getTicketsByBillId
                    // List<Ticket> tickets = Controller.getInstance().getTicketsByBillId(billId);
                    // b.setTickets(tickets);
                }
                return b;
            }
        }
        throw new Exception("Račun nije pronađen sa ID: " + billId);
    }

    private void refreshTableData() {
        // Osveži podatke u tabeli
        if (table.getModel() instanceof TableModelBills) {
            TableModelBills model = (TableModelBills) table.getModel();
            try {
                List<Bill> updatedBills = Controller.getInstance().getAllBills();
                model.setBills(updatedBills);
                model.fireTableDataChanged();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }
}

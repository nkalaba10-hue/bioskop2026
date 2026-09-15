/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.client.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import rs.ac.bg.fon.ai.client.forms.TicketDialog;
import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.ProjectionStatus;

/**
 *
 * @author nkala
 */
public class ButtonEditor extends DefaultCellEditor {

    private JButton button;
    private String label;
    private boolean clicked;
    private JTable table;
    //private TableModelBills modelBills;

    public ButtonEditor(JCheckBox checkBox, JTable table) {

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
                Projection p = ((TableModelProjection) table.getModel()).getProjectionAt(selectedRow);

                LocalDate projectionDate = p.getDate();
                LocalTime projectionTime = p.getTime();
                LocalDateTime projectionDateTime = LocalDateTime.of(projectionDate, projectionTime);

                if (projectionDateTime.isBefore(LocalDateTime.now())) {

                    JOptionPane.showMessageDialog(table,
                            "Nije moguće kupiti kartu za projekcije iz prošlosti!",
                            "Greška", JOptionPane.ERROR_MESSAGE);
                    clicked = false;
                    return label;

                }
                if (p.getStatus() == ProjectionStatus.SOLD_OUT) {
                    JOptionPane.showMessageDialog(table,
                            "Nije moguće kupiti kartu - projekcija je popunjena!",
                            "Greška", JOptionPane.ERROR_MESSAGE);
                    clicked = false;
                    return label;
                }

                new TicketDialog(p, (TableModelProjection) table.getModel()).setVisible(true);
            }
        }
        clicked = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }
}

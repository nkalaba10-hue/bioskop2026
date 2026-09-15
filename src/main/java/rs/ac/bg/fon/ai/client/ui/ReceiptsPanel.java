/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import rs.ac.bg.fon.ai.client.components.ButtonEditorBills;
import rs.ac.bg.fon.ai.client.components.ButtonRenderer;
import rs.ac.bg.fon.ai.client.components.TableModelBills;
import rs.ac.bg.fon.ai.client.logic.Controller;
import rs.ac.bg.fon.ai.communication.model.Bill;

/**
 *
 * @author nkala
 */
public class ReceiptsPanel extends JPanel {

    private JTable table;
    private TableModelBills modelBills;

    public ReceiptsPanel() {
        initComponents();
        // loadBills();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        modelBills = new TableModelBills(new ArrayList<>());
        loadBills();

        table = new JTable(modelBills);

        setupTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupTable() {
        table.setFillsViewportHeight(true);
        table.setBackground(Color.YELLOW);
        table.setForeground(Color.RED);
        table.setFont(new Font("Arial", Font.BOLD, 13));
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(255, 204, 102));

        // === Renderer za tekstualne kolone ===
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(Color.YELLOW);
                c.setForeground(Color.RED);
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        };
        table.setDefaultRenderer(Object.class, renderer);

        // === Renderer i Editor za dugme "Prikaži detalje" ===
        table.getColumn("Detalji").setCellRenderer(new ButtonRenderer());
        table.getColumn("Detalji").setCellEditor(new ButtonEditorBills(new JCheckBox(), table));
    }

    private void loadBills() {
        List<Bill> bills = new ArrayList<>();
        try {
            bills = Controller.getInstance().getAllBills();
        } catch (Exception ex) {
            Logger.getLogger(ReceiptsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        modelBills.setBills(bills);
    }

    // Metoda za osvežavanje podataka ako bude potrebno
//    public void refreshData() {
//        loadBills();
//    }
}

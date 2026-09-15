/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import rs.ac.bg.fon.ai.client.components.TableModelHall;
import rs.ac.bg.fon.ai.client.forms.HallDialog;
import rs.ac.bg.fon.ai.client.logic.Controller;
import rs.ac.bg.fon.ai.communication.model.Hall;

/**
 *
 * @author nkala
 */
public class HallPanel extends JPanel {

    private JTable table;
    private TableModelHall model;
    private JButton btnEdit;
    private JButton btnDelete;

    public HallPanel() {
        initComponents();
        //loadHalls();
        setupTableSelectionListener();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

        table = new JTable();
        JScrollPane scrollPane = createHallTable(table);
        add(scrollPane, BorderLayout.CENTER);
        add(createHallBottomPanel(), BorderLayout.SOUTH);
    }

    private JScrollPane createHallTable(JTable table) {
        List<Hall> halls = new ArrayList<>();
        try {
            halls = Controller.getInstance().getHalls();
        } catch (Exception ex) {
            Logger.getLogger(HallPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        model = new TableModelHall(halls);
        table.setModel(model);
        table.setRowHeight(25);

        return new JScrollPane(table);
    }

    private JPanel createHallBottomPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton btnAdd = new JButton("Dodaj");
        btnEdit = new JButton("Izmeni");
        btnDelete = new JButton("Obriši");

        // Inicijalno onemogući edit i delete dok se ne selektuje red
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);

        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnEdit);
        buttonsPanel.add(btnDelete);

        setupButtonActions(btnAdd, btnEdit, btnDelete);

        return buttonsPanel;
    }

    private void setupTableSelectionListener() {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    updateButtonStates();
                }
            }
        });
    }

    private void updateButtonStates() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            // Nema selektovanog reda - onemogući dugmad
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        Hall selectedHall = model.getHallAt(modelRow);

        if (selectedHall != null) {
            try {
                boolean hasUpcomingProjections = Controller.getInstance().hasUpcomingProjectionsForHall(selectedHall);

                // Onemogući edit i delete ako postoje predstojeće projekcije
                btnEdit.setEnabled(!hasUpcomingProjections);
                btnDelete.setEnabled(!hasUpcomingProjections);

            } catch (Exception ex) {
                Logger.getLogger(HallPanel.class.getName()).log(Level.SEVERE, null, ex);
                // U slučaju greške, onemogući dugmiće
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);
            }
        }
    }

    private void setupButtonActions(JButton btnAdd, JButton btnEdit, JButton btnDelete) {
        btnAdd.addActionListener(e -> addHall());
        btnEdit.addActionListener(e -> editHall());
        btnDelete.addActionListener(e -> deleteHall());
    }

    private void addHall() {
        HallDialog dialog = new HallDialog(
                (javax.swing.JFrame) SwingUtilities.getWindowAncestor(this),
                true,
                model
        );
        dialog.setVisible(true);
        refreshData();
    }

    private void editHall() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        int modelRow = table.getSelectedRow();
        Hall selectedHall = model.getHallAt(modelRow);

        HallDialog dialog = new HallDialog(
                (javax.swing.JFrame) SwingUtilities.getWindowAncestor(this),
                true,
                model,
                selectedHall
        );
        dialog.setVisible(true);
        refreshData();
    }

    private void deleteHall() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        int modelRow = table.getSelectedRow();
        Hall hallToDelete = model.getHallAt(modelRow);

        int result = JOptionPane.showConfirmDialog(this,
                "Da li ste sigurni da želite da obrišete selektovanu salu: '" + hallToDelete.getName() + "'?",
                "Potvrda brisanja",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            try {
                Controller.getInstance().deleteHall(hallToDelete);
                model.removeHall(hallToDelete);
                JOptionPane.showMessageDialog(this,
                        "Sala uspešno obrisana!",
                        "Uspeh",
                        JOptionPane.INFORMATION_MESSAGE);
                refreshData();
            } catch (Exception ex) {
                Logger.getLogger(HallPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this,
                        "Greška pri brisanju sale: " + ex.getMessage(),
                        "Greška",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadHalls() {
        List<Hall> halls = new ArrayList<>();
        try {
            halls = Controller.getInstance().getHalls();
        } catch (Exception ex) {
            Logger.getLogger(HallPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        model.setHalls(halls);
    }

    public void refreshData() {
        //loadHalls();
        // Resetuj selekciju i stanje dugmića nakon osvežavanja
        table.clearSelection();
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }
}

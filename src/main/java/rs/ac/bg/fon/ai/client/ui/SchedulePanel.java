/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import rs.ac.bg.fon.ai.client.components.ButtonEditor;
import rs.ac.bg.fon.ai.client.components.ButtonRenderer;
import rs.ac.bg.fon.ai.client.components.TableModelProjection;
import rs.ac.bg.fon.ai.client.forms.DateLabelFormatter;
import rs.ac.bg.fon.ai.client.forms.ProjectionDialog;
import rs.ac.bg.fon.ai.client.logic.Controller;
import rs.ac.bg.fon.ai.communication.model.Projection;

/**
 *
 * @author nkala
 */
public class SchedulePanel extends JPanel {

    private JTable tblSchedule;
    private JDatePickerImpl datePicker;
    private JLabel lblDate;
    private JButton btnPrevDate;
    private JButton btnNextDate;
    private LocalDate lastSelectedDate = null;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnCancel;
    private TableModelProjection model;

    public SchedulePanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createFilterPanel(), BorderLayout.NORTH);
        tblSchedule = new JTable();
        model = new TableModelProjection();
        tblSchedule.setModel(model);
        add(createScheduleTable(tblSchedule), BorderLayout.CENTER);
        add(createScheduleBottomPanel(tblSchedule), BorderLayout.SOUTH);

        loadProjectionsForDate(lastSelectedDate);
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblDate = new JLabel("Datum:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(lblDate, gbc);

        lastSelectedDate = LocalDate.now();

        UtilDateModel model = new UtilDateModel();
        model.setValue(java.sql.Date.valueOf(lastSelectedDate));

        Properties p = new Properties();
        p.put("text.today", "Danas");
        p.put("text.month", "Mesec");
        p.put("text.year", "Godina");

        JDatePanelImpl datePanelPicker = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanelPicker, new DateLabelFormatter());

        datePicker.addActionListener(e -> {
            UtilDateModel m = (UtilDateModel) datePicker.getModel();
            Date selectedDate = m.getValue();
            if (selectedDate != null) {
                lastSelectedDate = selectedDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }
            loadProjectionsForDate(lastSelectedDate);
            checkDateAndDisableButtons();

        });

        gbc.gridx = 1;
        gbc.gridy = 0;
        filterPanel.add(datePicker, gbc);

        btnPrevDate = new JButton("←");
        btnNextDate = new JButton("→");
        btnPrevDate.addActionListener(e -> changeDate(-1));
        btnNextDate.addActionListener(e -> changeDate(1));

        gbc.gridx = 2;
        filterPanel.add(btnPrevDate, gbc);
        gbc.gridx = 3;
        filterPanel.add(btnNextDate, gbc);

        return filterPanel;
    }

    private void changeDate(int days) {
        if (lastSelectedDate == null) {
            lastSelectedDate = LocalDate.now();
        }

        lastSelectedDate = lastSelectedDate.plusDays(days);

        UtilDateModel model = (UtilDateModel) datePicker.getModel();
        model.setValue(java.sql.Date.valueOf(lastSelectedDate));

        try {
            datePicker.getJFormattedTextField().setValue(java.sql.Date.valueOf(lastSelectedDate));
        } catch (Exception ignored) {
        }
        loadProjectionsForDate(lastSelectedDate);

        checkDateAndDisableButtons();
    }

    private JScrollPane createScheduleTable(JTable tabela) {
        prepareScheduleTable(tabela);
        return new JScrollPane(tabela);
    }

    private void prepareScheduleTable(JTable tabela) {
        List<Projection> projections = new ArrayList<>();
        try {
            projections = Controller.getInstance().getAllProjections();
        } catch (Exception ex) {
            Logger.getLogger(SchedulePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        model.setProjections(projections);

        tabela.getColumn("Akcija").setCellRenderer(new ButtonRenderer());
        tabela.getColumn("Akcija").setCellEditor(new ButtonEditor(new JCheckBox(), tabela));
        tabela.setRowHeight(30);
    }

    private JPanel createScheduleBottomPanel(JTable tabela) {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel leftButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnAdd = new JButton("Dodaj projekciju");
        btnEdit = new JButton("Izmeni projekciju");
        btnCancel = new JButton("Otkazi projekciju");

        leftButtonsPanel.add(btnAdd);
        leftButtonsPanel.add(btnEdit);
        leftButtonsPanel.add(btnCancel);

        bottomPanel.add(leftButtonsPanel, BorderLayout.WEST);

        setupScheduleActions(btnAdd, btnEdit, btnCancel, tabela);

        checkDateAndDisableButtons();

        return bottomPanel;
    }

    private void setupScheduleActions(JButton btnAdd, JButton btnEdit, JButton btnCancel, JTable tabela) {
        btnAdd.addActionListener(e -> {
            LocalDate localDate = lastSelectedDate != null ? lastSelectedDate : LocalDate.now();
            ProjectionDialog dialog = new ProjectionDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(tabela),
                    true,
                    (TableModelProjection) tabela.getModel(),
                    localDate
            );
            dialog.setVisible(true);
            //loadProjectionsForDate(lastSelectedDate);
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = tblSchedule.getSelectedRow();
            if (selectedRow >= 0) {
                Projection projection = ((TableModelProjection) tblSchedule.getModel()).getProjectionAt(selectedRow);

                // Provera da li je projekcija u prošlosti
                if (projection.getDate().isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(this,
                            "Ne možete menjati projekcije iz prošlosti!",
                            "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (projection.getSoldTickets() > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Ne možete menjati projekciju za koju postoje prodaje karte!",
                            "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ProjectionDialog editDialog = new ProjectionDialog(
                        (JFrame) SwingUtilities.getWindowAncestor(tabela),
                        true,
                        (TableModelProjection) tblSchedule.getModel(),
                        projection
                );
                editDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Molimo izaberite projekciju za izmenu.");
            }
        });

        btnCancel.addActionListener(e -> {
            int selectedRow = tblSchedule.getSelectedRow();
            if (selectedRow >= 0) {
                Projection projection = ((TableModelProjection) tblSchedule.getModel()).getProjectionAt(selectedRow);

                // Provera da li je projekcija u prošlosti
                if (projection.getDate().isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(this,
                            "Ne možete otkazivati projekcije iz prošlosti!",
                            "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Provera da li ima prodatih karata
                if (projection.getSoldTickets() > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Ne možete otkazati projekciju za koju postoje prodaje karte!",
                            "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Da li ste sigurni da želite da otkažete projekciju?",
                        "Potvrda otkazivanja", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        // Pozovi controller da obriše projekciju iz baze
                        Controller.getInstance().deleteProjection(projection);

                        // Ukloni projekciju iz modela tabele
                        ((TableModelProjection) tblSchedule.getModel()).removeProjection(projection);

                        JOptionPane.showMessageDialog(this,
                                "Projekcija je uspešno otkazana!",
                                "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,
                                "Greška pri otkazivanju projekcije: " + ex.getMessage(),
                                "Greška", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Molimo izaberite projekciju za otkazivanje.");
            }
        });
    }

    private void loadProjectionsForDate(LocalDate date) {
        List<Projection> projections = new ArrayList<>();
        try {
            projections = Controller.getInstance().getProjectionsByDate(date);
        } catch (Exception ex) {
            Logger.getLogger(SchedulePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        model.setProjections(projections);

//        tblSchedule.getColumn("Akcija").setCellRenderer(new ButtonRenderer());
//        tblSchedule.getColumn("Akcija").setCellEditor(new ButtonEditor(new JCheckBox(), tblSchedule));
//        tblSchedule.setRowHeight(30);
    }

    private void checkDateAndDisableButtons() {
        if (lastSelectedDate == null) {
            lastSelectedDate = LocalDate.now();
        }

        boolean isPastDate = lastSelectedDate.isBefore(LocalDate.now());

        // Onemogući dugmiće za prošle datume
        btnAdd.setEnabled(!isPastDate);
        btnEdit.setEnabled(!isPastDate);
        btnCancel.setEnabled(!isPastDate);

        // Postavi tooltip poruke
        if (isPastDate) {
            btnAdd.setToolTipText("Ne možete dodavati projekcije za prošle datume");
            btnEdit.setToolTipText("Ne možete menjati projekcije za prošle datume");
            btnCancel.setToolTipText("Ne možete otkazivati projekcije za prošle datume");
        } else {
            btnAdd.setToolTipText("Dodaj novu projekciju za izabrani datum");
            btnEdit.setToolTipText("Izmeni izabranu projekciju");
            btnCancel.setToolTipText("Otkaži izabranu projekciju");
        }
    }

}

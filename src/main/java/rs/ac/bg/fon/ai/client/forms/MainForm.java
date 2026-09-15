/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package rs.ac.bg.fon.ai.client.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import rs.ac.bg.fon.ai.client.ui.AddMoviePanel;
import rs.ac.bg.fon.ai.client.ui.HallPanel;
import rs.ac.bg.fon.ai.client.ui.ReceiptsPanel;
import rs.ac.bg.fon.ai.client.ui.SchedulePanel;

/**
 *
 * @author nkala
 */
public class MainForm extends javax.swing.JFrame {

    private JPanel jPanelMenu;
    private JPanel jPanelShow;

    // Dugmici za meni (sada lokalne varijable)
    private JButton btnSchedule;
    private JButton btnAddFilm;
    private JButton btnReceipts;
    private JButton btnHall;

    // Panel reference
//    private SchedulePanel schedulePanel;
//    private HallPanel hallPanel;
//    private ReceiptsPanel receiptsPanel;
//    private AddMoviePanel addMoviePanel;
    public MainForm() {
        initCustomComponents();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainForm().setVisible(true));
    }

    private void initCustomComponents() {
        jPanelMenu = createMenuPanel();
        jPanelShow = new JPanel(new BorderLayout());
        setupButtonActions();

        // ----- GLAVNI LAYOUT -----
        this.setLayout(new BorderLayout());
        this.add(jPanelMenu, BorderLayout.WEST);
        this.add(jPanelShow, BorderLayout.CENTER);

        showPanel("Schedule");

        this.setSize(800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(50, 50, 50));

        btnSchedule = new JButton("Schedule");
        btnAddFilm = new JButton("Add Film");
        btnReceipts = new JButton("Receipts");
        btnHall = new JButton("Hall");

        JButton[] dugmad = {btnSchedule, btnAddFilm, btnReceipts, btnHall};
        for (JButton b : dugmad) {
            configureButton(b);
            panel.add(b);
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        return panel;
    }

    private void configureButton(JButton b) {
        b.setFocusPainted(false);
        b.setBackground(new Color(60, 63, 65));
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, b.getPreferredSize().height));
    }

    private void setupButtonActions() {
        btnSchedule.addActionListener(e -> showPanel("Schedule"));
        btnAddFilm.addActionListener(e -> showPanel("Add Film"));
        btnReceipts.addActionListener(e -> showPanel("Receipts"));
        btnHall.addActionListener(e -> showPanel("Hall"));
    }

    public void showPanel(String panelName) {
        // Ukloni sve postojeće komponente
        jPanelShow.removeAll();

        // Kreiraj novi panel
        JPanel newPanel = createPanelByName(panelName);

        // Dodaj novi panel
        jPanelShow.add(newPanel, BorderLayout.CENTER);

        // Osveži prikaz
        jPanelShow.revalidate();
        jPanelShow.repaint();

        //highlightActiveButton(panelName);
        System.gc(); // Sugerisi garbage collection
    }

    private JPanel createPanelByName(String panelName) {
        switch (panelName) {
            case "Schedule":
                return new SchedulePanel();
            case "Add Film":
                return new AddMoviePanel();
            case "Receipts":
                return new ReceiptsPanel();
            case "Hall":
                return new HallPanel();
            default:
                return new JPanel();
        }
    }

    //    private JPanel createShowPanel() {
//        JPanel panel = new JPanel(new CardLayout());
//
//        // Kreiranje panela - sada su zasebne klase
//        schedulePanel = new SchedulePanel();
////        hallPanel = new HallPanel();
////        receiptsPanel = new ReceiptsPanel();
////        addMoviePanel = new AddMoviePanel();
////
//        panel.add(schedulePanel, "Schedule");
////        panel.add(addMoviePanel, "Add Film");
////        panel.add(receiptsPanel, "Receipts");
////        panel.add(hallPanel, "Hall");
//
//        return panel;
//    }
    //    private void showCard(String name) {
//        CardLayout cl = (CardLayout) jPanelShow.getLayout();
//        cl.show(jPanelShow, name);
//
//        // Opciono: Osveži podatke kada se prikaže panel
//        if ("Receipts".equals(name)) {
//            receiptsPanel.refreshData();
//        }
//    }
}

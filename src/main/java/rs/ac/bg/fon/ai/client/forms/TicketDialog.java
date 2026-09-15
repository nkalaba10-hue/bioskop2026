/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.client.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import rs.ac.bg.fon.ai.client.components.TableModelProjection;
import rs.ac.bg.fon.ai.client.logic.Controller;
import rs.ac.bg.fon.ai.communication.model.Bill;
import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.Ticket;

/**
 *
 * @author nkala
 */
public class TicketDialog extends JDialog {

    private Projection projection;
    private JTable ticketTable;
    private DefaultTableModel ticketModel;
    private List<Ticket> tickets;
    private TableModelProjection model;
    private JLabel lblTotal;

    public TicketDialog(Projection projection, TableModelProjection model) {
        this.projection = projection;
        this.model = model;
        this.tickets = new ArrayList<>();

        initializeDialog();
        initializeComponents();
    }

    private void initializeDialog() {
        setTitle("Kupovina karata");
        setModal(true);
        setSize(650, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void initializeComponents() {
        initializeInfoPanel();
        initializeCenterPanel();
        initializeSouthPanel();
    }

    private void initializeInfoPanel() {
        JPanel infoPanel = createInfoPanel();
        add(infoPanel, BorderLayout.NORTH);
    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Detalji projekcije"));

        GridBagConstraints gbc = createGridBagConstraints();
        addProjectionDetailsToPanel(infoPanel, gbc);
        addAddTicketButtonToPanel(infoPanel, gbc);

        return infoPanel;
    }

    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        return gbc;
    }

    private void addProjectionDetailsToPanel(JPanel panel, GridBagConstraints gbc) {
        addLabelAndTextField(panel, gbc, 0, "Film:", projection.getFilm().getTitle());
        addLabelAndTextField(panel, gbc, 1, "Sala:", projection.getHall().getName());
        addLabelAndTextField(panel, gbc, 2, "Datum:", projection.getDate().toString());
        addLabelAndTextField(panel, gbc, 3, "Vreme:", projection.getTime().toString());
    }

    private void addLabelAndTextField(JPanel panel, GridBagConstraints gbc, int row, String labelText, String value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        JTextField textField = new JTextField(value);
        textField.setEditable(false);
        panel.add(textField, gbc);
    }

    private void addAddTicketButtonToPanel(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton btnAdd = new JButton("Dodaj kartu");
        btnAdd.addActionListener(e -> addTicket());
        panel.add(btnAdd, gbc);
    }

    private void initializeCenterPanel() {
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));

        initializeTicketTable();
        centerPanel.add(new JScrollPane(ticketTable), BorderLayout.CENTER);

        JPanel buttonPanel = createTicketButtonPanel();
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        return centerPanel;
    }

    private void initializeTicketTable() {
        ticketModel = new DefaultTableModel(new Object[]{"Naziv", "Količina"}, 0);
        ticketTable = new JTable(ticketModel);
    }

    private JPanel createTicketButtonPanel() {
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnRemoveOne = new JButton("Ukloni jedan");
        JButton btnRemoveAll = new JButton("Ukloni sve");

        btnRemoveOne.addActionListener(e -> removeOneTicket());
        btnRemoveAll.addActionListener(e -> removeAllTickets());

        btnPanel.add(btnRemoveOne);
        btnPanel.add(btnRemoveAll);

        return btnPanel;
    }

    private void initializeSouthPanel() {
        JPanel southPanel = createSouthPanel();
        add(southPanel, BorderLayout.SOUTH);
    }

    private JPanel createSouthPanel() {
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        lblTotal = new JLabel("Ukupan iznos: 0.00 RSD");
        JButton btnGenerate = createGenerateBillButton();

        southPanel.add(lblTotal);
        southPanel.add(btnGenerate);

        return southPanel;
    }

    private JButton createGenerateBillButton() {
        JButton btnGenerate = new JButton("Generiši račun");
        btnGenerate.addActionListener(e -> {
            try {
                validateDialog();
                generateBill();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        return btnGenerate;
    }

    // Pomoćne metode - ostale su iste
    private void refreshTable() {
        ticketModel.setRowCount(0);
        if (!tickets.isEmpty()) {
            String ticketName = "Karte za projekciju filma " + projection.getFilm().getTitle();
            int qty = tickets.size();
            ticketModel.addRow(new Object[]{ticketName, qty});
        }
    }

    private void addTicket() {
        tickets.add(new Ticket(projection, projection.getPrice(), null));
        refreshTable();
        updateTotal();
    }

    private void removeOneTicket() {
        if (tickets.isEmpty()) {
            return;
        }
        tickets.remove(tickets.size() - 1);
        refreshTable();
        updateTotal();
    }

    private void removeAllTickets() {
        tickets.clear();
        refreshTable();
        updateTotal();
    }

    private void generateBill() {
        try {
            if (tickets.isEmpty()) {
                throw new Exception("Niste dodali nijednu kartu");
            }

            // KREIRAJ BILL SA SVIM TICKETIMA
            BigDecimal total = BigDecimal.valueOf(tickets.size()).multiply(projection.getPrice());
            Bill bill = new Bill(LocalDateTime.now(), tickets, total, Controller.getInstance().getCurrentUser());

            // POSTAVI PROJEKCIJU NA SVE TICKETE
            for (Ticket t : tickets) {
                t.setProjection(projection); // OBAVEZNO - server će koristiti ovo za proveru
            }

            // JEDAN POZIV - SVE PROVERE SU NA SERVERU!
            Bill savedBill = Controller.getInstance().saveBill(bill);

            // OVO JE KLJUČNO: OSVEŽI PROJEKCIJU IZ BAZE
            Projection updatedProjection = Controller.getInstance().getProjectionById(projection.getId());

            // AŽURIRAJ MODEL SA NOVIM PODACIMA IZ BAZE
            model.editProjection(updatedProjection);

            
            JOptionPane.showMessageDialog(this, "Uspešno kupljeno " + tickets.size() + " karata. Račun #" + savedBill.getId());
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTotal() {
        BigDecimal total = projection.getPrice().multiply(BigDecimal.valueOf(tickets.size()));
        lblTotal.setText("Ukupan iznos: " + total + " RSD");
    }

    private void validateDialog() throws Exception {
        int selectedTickets = tickets.size();
        int hallCapacity = projection.getHall().getCapacity();
        int soldTickets = projection.getSoldTickets();

        if (selectedTickets + soldTickets > hallCapacity) {
            throw new Exception("Pokusavate kupiti " + selectedTickets + " karata, a dostupno je " + (hallCapacity - soldTickets) + " mesta");
        }

        if (tickets.size() == 0) {
            throw new Exception("Niste dodali nista!");
        }
    }
}

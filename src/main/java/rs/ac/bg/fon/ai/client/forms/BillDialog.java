/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.client.forms;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import rs.ac.bg.fon.ai.client.logic.Controller;
import rs.ac.bg.fon.ai.communication.model.Bill;
import rs.ac.bg.fon.ai.communication.model.Ticket;

/**
 *
 * @author nkala
 */
public class BillDialog extends JDialog {

    private Bill bill;
    private boolean saved = false;
    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnIncrease;
    private JButton btnDecrease;
    private JLabel lblTicketCount;
    private JLabel lblTotalAmount;
    private int currentTicketCount;
    private BigDecimal ticketPrice;

    public BillDialog() {
    }

    public BillDialog(Bill bill) {
        this.bill = bill;
        setTitle("Izmena računa #" + bill.getId());
        setModal(true);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== Naslov =====
        JLabel lblTitle = new JLabel("Račun #" + bill.getId());
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblTitle);
        contentPanel.add(Box.createVerticalStrut(15));

        // ===== Datum =====
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        JLabel lblDate = new JLabel("Datum: " + bill.getDateTime().format(formatter));
        lblDate.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPanel.add(lblDate);
        contentPanel.add(Box.createVerticalStrut(10));

        // ===== Informacije o projekciji =====
        if (!bill.getTickets().isEmpty()) {
            Ticket firstTicket = bill.getTickets().get(0);
            JLabel lblFilm = new JLabel("Film: " + firstTicket.getProjection().getFilm().getTitle());
            lblFilm.setFont(new Font("Arial", Font.BOLD, 14));
            contentPanel.add(lblFilm);

            JLabel lblHall = new JLabel("Sala: " + firstTicket.getProjection().getHall().getName());
            lblHall.setFont(new Font("Arial", Font.PLAIN, 13));
            contentPanel.add(lblHall);

            JLabel lblTime = new JLabel("Vreme: " + firstTicket.getProjection().getDate() + " "
                    + firstTicket.getProjection().getTime());
            lblTime.setFont(new Font("Arial", Font.PLAIN, 13));
            contentPanel.add(lblTime);
        }
        contentPanel.add(Box.createVerticalStrut(15));

        // ===== Kontrole za broj karata =====
        JPanel ticketControlPanel = new JPanel();
        ticketControlPanel.setLayout(new GridLayout(1, 4, 10, 0));
        ticketControlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnDecrease = new JButton("-");
        lblTicketCount = new JLabel("", JLabel.CENTER);
        btnIncrease = new JButton("+");

        // Postavi početne vrednosti
        currentTicketCount = bill.getTickets().size();
        ticketPrice = bill.getTickets().isEmpty() ? BigDecimal.ZERO : bill.getTickets().get(0).getPrice();

        btnDecrease.addActionListener((ActionEvent e) -> {
            if (currentTicketCount > 1) {
                currentTicketCount--;
                updateDisplay();
            }
        });

        btnIncrease.addActionListener((ActionEvent e) -> {
            if (!bill.getTickets().isEmpty()) {
                int maxCapacity = bill.getTickets().get(0).getProjection().getHall().getCapacity();
                int soldTickets = bill.getTickets().get(0).getProjection().getSoldTickets();
                int originalTicketCount = bill.getTickets().size();

                // Koliko karata će biti prodato nakon izmene?
                int totalAfterChange = soldTickets - originalTicketCount + currentTicketCount + 1;

                if (totalAfterChange <= maxCapacity) {
                    currentTicketCount++;
                    updateDisplay();
                } else {
                    int availableSeats = maxCapacity - (soldTickets - originalTicketCount + currentTicketCount);
                    JOptionPane.showMessageDialog(this,
                            "Nema dovoljno slobodnih mesta! Dostupno: " + availableSeats,
                            "Greška", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                currentTicketCount++;
                updateDisplay();
            }
        });

        ticketControlPanel.add(btnDecrease);
        ticketControlPanel.add(lblTicketCount);
        ticketControlPanel.add(btnIncrease);

        JLabel lblCountText = new JLabel(" broj karata");
        lblCountText.setFont(new Font("Arial", Font.PLAIN, 12));
        ticketControlPanel.add(lblCountText);

        contentPanel.add(ticketControlPanel);
        contentPanel.add(Box.createVerticalStrut(10));

        // ===== Cena po karti =====
        JLabel lblPricePerTicket = new JLabel("Cena po karti: " + ticketPrice + " RSD");
        lblPricePerTicket.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPricePerTicket.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblPricePerTicket);
        contentPanel.add(Box.createVerticalStrut(5));

        // ===== Ukupan iznos =====
        lblTotalAmount = new JLabel();
        lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblTotalAmount);
        contentPanel.add(Box.createVerticalStrut(15));

        // ===== Dugmad =====
        JPanel buttonPanel = new JPanel();
        btnSave = new JButton("Sačuvaj izmene");
        btnCancel = new JButton("Otkaži");

        btnSave.addActionListener((ActionEvent e) -> {
            saveChanges();
        });

        btnCancel.addActionListener((ActionEvent e) -> {
            cancel();
        });

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // ===== Scroll panel =====
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // SADA POZIVAMO updateDisplay() NAKON ŠTO SU SVE KOMPONENTE INICIJALIZOVANE
        updateDisplay();

        pack();
        setLocationRelativeTo(null);
    }

    private void updateDisplay() {
        // Proveri da li su komponente inicijalizovane pre nego što ih koristiš
        if (lblTicketCount != null) {
            lblTicketCount.setText(String.valueOf(currentTicketCount));
        }

        if (lblTotalAmount != null) {
            BigDecimal totalAmount = ticketPrice.multiply(BigDecimal.valueOf(currentTicketCount));
            lblTotalAmount.setText("Ukupan iznos: " + totalAmount + " RSD");

            // Ažuriraj ukupan iznos u bill objektu
            bill.setTotalAmount(totalAmount);
        }
    }

    private void saveChanges() {
        try {
            // Kreiraj novu listu tiketa sa ažuriranim brojem
            List<Ticket> newTickets = new ArrayList<>();

            if (!bill.getTickets().isEmpty()) {
                Ticket templateTicket = bill.getTickets().get(0);

                for (int i = 0; i < currentTicketCount; i++) {
                    // Koristi postojeći ticket ako postoji, inače kreiraj novi
                    if (i < bill.getTickets().size()) {
                        // Koristi postojeći ticket (sačuvaj mu ID)
                        Ticket existingTicket = bill.getTickets().get(i);
                        newTickets.add(existingTicket);
                    } else {
                        // Kreiraj novi ticket (bez ID-ja)
                        Ticket newTicket = new Ticket();
                        newTicket.setProjection(templateTicket.getProjection());
                        newTicket.setPrice(templateTicket.getPrice());
                        newTicket.setBill(bill);
                        newTickets.add(newTicket);
                    }
                }
            }

            bill.setTickets(newTickets);

            
            Controller.getInstance().editBill(bill);
            this.saved = true;
            JOptionPane.showMessageDialog(this, "Račun uspešno ažuriran!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Greška pri ažuriranju računa: " + ex.getMessage(),
                    "Greška", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void cancel() {
        this.saved = false;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }
}

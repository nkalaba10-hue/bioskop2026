/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package rs.ac.bg.fon.ai.client.forms;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import rs.ac.bg.fon.ai.client.components.TableModelProjection;
import rs.ac.bg.fon.ai.client.logic.Controller;
import rs.ac.bg.fon.ai.communication.model.Film;
import rs.ac.bg.fon.ai.communication.model.Hall;
import rs.ac.bg.fon.ai.communication.model.Projection;

/**
 *
 * @author nkala
 */
public class ProjectionDialog extends javax.swing.JDialog {

    private TableModelProjection model;
    private LocalDate selectedDate;
    private Projection projectionToEdit; // Nova promenljiva za edit mode
    private boolean isEditMode; // Flag da li je edit mode

    /**
     * Creates new form ProjectionDialog
     */
    public ProjectionDialog(java.awt.Frame parent, boolean modal, TableModelProjection model, LocalDate selectedDate) {
        super(parent, modal);
        this.model = model;
        this.selectedDate = selectedDate;

        initComponents();
        try {
            prepareForm();
        } catch (Exception ex) {
            Logger.getLogger(ProjectionDialog.class.getName()).log(Level.SEVERE, null, ex);
        }

        setLocationRelativeTo(parent);
        pack();
    }

    public ProjectionDialog(java.awt.Frame parent, boolean modal, TableModelProjection model, Projection projectionToEdit) {
        super(parent, modal);
        this.model = model;
        this.projectionToEdit = projectionToEdit;
        this.selectedDate = projectionToEdit.getDate();
        this.isEditMode = true;

        initComponents();
        try {
            prepareForm();
        } catch (Exception ex) {
            Logger.getLogger(ProjectionDialog.class.getName()).log(Level.SEVERE, null, ex);
        }

        setLocationRelativeTo(parent);
        pack();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbFilm = new javax.swing.JComboBox();
        cmbHall = new javax.swing.JComboBox();
        txtDate = new javax.swing.JTextField();
        txtVreme = new javax.swing.JTextField();
        txtCena = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cmbFilm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbHall.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAdd.setText("Dodaj");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel1.setText("Film:");

        jLabel2.setText("Sala:");

        jLabel3.setText("Datum:");

        jLabel4.setText("Vreme:");

        jLabel5.setText("Cena:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdd)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbHall, 0, 152, Short.MAX_VALUE)
                            .addComponent(cmbFilm, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDate)
                            .addComponent(txtVreme)
                            .addComponent(txtCena))))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFilm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbHall, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtVreme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAdd)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        try {
            validateInput(); // Baca izuzetak ako validacija ne prođe

            Film f = (Film) cmbFilm.getSelectedItem();
            Hall h = (Hall) cmbHall.getSelectedItem();
            String date = txtDate.getText();
            LocalDate datum = LocalDate.parse(date);
            String text = txtVreme.getText();
            LocalTime time = LocalTime.parse(text);
            BigDecimal cena = new BigDecimal(txtCena.getText());

            if (isEditMode && projectionToEdit != null) {
                // Edit mode - ažuriraj postojeću projekciju
                projectionToEdit.setFilm(f);
                projectionToEdit.setHall(h);
                projectionToEdit.setDate(datum);
                projectionToEdit.setTime(time);
                projectionToEdit.setPrice(cena);

                Controller.getInstance().editProjection(projectionToEdit);
                model.editProjection(projectionToEdit); // Ova metoda će biti dodana u TableModelProjection
                JOptionPane.showMessageDialog(this, "Uspešno izmenjena projekcija");
            } else {
                // Add mode - kreiraj novu projekciju
                Projection p = new Projection(f, h, datum, time, cena);
                Projection savedProjection = Controller.getInstance().saveProjection(p);
                model.addProjection(savedProjection);
                JOptionPane.showMessageDialog(this, "Uspešno dodata projekcija u tabelu");
            }

            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JComboBox cmbFilm;
    private javax.swing.JComboBox cmbHall;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtCena;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtVreme;
    // End of variables declaration//GEN-END:variables

    private void prepareForm() throws Exception {

        if (isEditMode && projectionToEdit != null) {
            // Edit mode - popuni podatke postojeće projekcije
            txtDate.setText(projectionToEdit.getDate().toString());
            cmbFilm.setSelectedItem(projectionToEdit.getFilm());
            cmbHall.setSelectedItem(projectionToEdit.getHall());
            txtVreme.setText(projectionToEdit.getTime().toString());
            txtCena.setText(projectionToEdit.getPrice().toString());
            btnAdd.setText("Izmeni"); // Promeni tekst dugmeta
            setTitle("Izmena projekcije");
        } else {
            // Add mode
            txtDate.setText(selectedDate.toString());
            btnAdd.setText("Dodaj");
            setTitle("Dodavanje projekcije");
        }

        txtDate.setEditable(false);
        prepareComboBoxFilm();
        prepareComboBoxHall();

    }

    private void prepareComboBoxFilm() throws Exception {
        cmbFilm.removeAllItems();

        for (Film f : Controller.getInstance().getAllFilms()) {
            cmbFilm.addItem(f);
        }
        
        if (!(isEditMode && projectionToEdit != null))
        cmbFilm.setSelectedIndex(-1);
    }

    private void prepareComboBoxHall() throws Exception {

        cmbHall.removeAllItems();

        for (Hall h : Controller.getInstance().getHalls()) {
            cmbHall.addItem(h);

        }
        
        if (!(isEditMode && projectionToEdit != null))
        cmbHall.setSelectedIndex(-1);
    }

    private void validateInput() throws Exception {
        StringBuilder errors = new StringBuilder();

        // Validacija filma
        if (cmbFilm.getSelectedIndex() == -1) {
            errors.append("• Morate odabrati film!\n");
        }

        // Validacija sale
        if (cmbHall.getSelectedIndex() == -1) {
            errors.append("• Morate odabrati salu!\n");
        }

        // Validacija vremena
        String timeText = txtVreme.getText().trim();
        if (timeText.isEmpty()) {
            errors.append("• Morate uneti vreme!\n");
        } else {
            try {
                LocalTime.parse(timeText);
            } catch (Exception e) {
                errors.append("• Vreme nije u ispravnom formatu! Format: HH:MM\n");
            }
        }

        // Validacija cene
        String priceText = txtCena.getText().trim();
        if (priceText.isEmpty()) {
            errors.append("• Morate uneti cenu!\n");
        } else {
            try {
                BigDecimal price = new BigDecimal(priceText);
                if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    errors.append("• Cena mora biti veća od 0!\n");
                }
            } catch (NumberFormatException e) {
                errors.append("• Cena nije u ispravnom formatu! Unesite validan broj.\n");
            }
        }

        // Ako ima grešaka, baci izuzetak
        if (errors.length() > 0) {
            throw new Exception("Popravite sledeće greške:\n\n" + errors.toString());
        }
    }
}

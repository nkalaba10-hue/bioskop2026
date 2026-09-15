/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.client.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import rs.ac.bg.fon.ai.client.logic.Controller;
import rs.ac.bg.fon.ai.communication.model.Film;
import rs.ac.bg.fon.ai.communication.model.Genre;

/**
 *
 * @author nkala
 */
public class AddMoviePanel extends JPanel {

    private JTextField nameField;
    private JTextField yearField;
    private JTextField durationField;
    private JTextArea descArea;
    private Map<Genre, JCheckBox> genreCheckboxMap;

    public AddMoviePanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Naziv
        JLabel nameLabel = new JLabel("Naziv:");
        nameField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        // Godina releasea
        JLabel yearLabel = new JLabel("Godina releasea:");
        yearField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(yearLabel, gbc);
        gbc.gridx = 1;
        add(yearField, gbc);

        // Trajanje
        JLabel durationLabel = new JLabel("Trajanje (min):");
        durationField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(durationLabel, gbc);
        gbc.gridx = 1;
        add(durationField, gbc);

        // Opis (TextArea)
        JLabel descLabel = new JLabel("Opis:");
        descArea = new JTextArea(5, 20);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(descLabel, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(descScroll, gbc);

        // Žanrovi
        JPanel genrePanel = createGenrePanel();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;
        add(genrePanel, gbc);

        // Dugme dodaj
        JButton addButton = new JButton("Dodaj film");
        addButton.addActionListener(e -> addMovie());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(addButton, gbc);
    }

    private JPanel createGenrePanel() {
        JPanel genrePanel = new JPanel(new GridLayout(0, 2));
        genrePanel.setBorder(BorderFactory.createTitledBorder("Žanrovi"));

        genreCheckboxMap = new HashMap<>();
        List<Genre> genres = loadGenres();

        for (Genre g : genres) {
            JCheckBox cb = new JCheckBox(g.getName());
            genreCheckboxMap.put(g, cb);
            genrePanel.add(cb);
        }

        return genrePanel;
    }

    private List<Genre> loadGenres() {
        List<Genre> genres = new ArrayList<>();
        try {
            genres = Controller.getInstance().getAllGenres();
        } catch (Exception ex) {
            Logger.getLogger(AddMoviePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return genres;
    }

    private void addMovie() {
        try {
            // Validacija sa akumuliranjem grešaka
            String errorMessage = validateInput();
            if (!errorMessage.isEmpty()) {
                throw new Exception(errorMessage);
            }

            // Ostali inputi - sada smo sigurni da su validni
            String name = nameField.getText().trim();
            String dateText = yearField.getText().trim();
            int duration = Integer.parseInt(durationField.getText().trim());
            String description = descArea.getText().trim();

            // Parsiranje datuma - sada je sigurno validan
            LocalDate releaseDate = parseDate(dateText);

            // Koji checkboxovi su selektovani - sada je sigurno bar jedan
            List<Genre> selectedGenres = getSelectedGenres();

            Film film = new Film(name, duration, description, releaseDate, selectedGenres);

            Controller.getInstance().addMovie(film);
            JOptionPane.showMessageDialog(this, "Film uspešno dodat!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            clearForm();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška pri dodavanju filma:\n" + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        // Validacija naziva
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            errorMessage.append("• Naziv filma je obavezan!\n");
        } else if (name.length() < 2) {
            errorMessage.append("• Naziv filma mora imati najmanje 2 karaktera!\n");
        }

        // Validacija datuma
        String dateText = yearField.getText().trim();
        if (dateText.isEmpty()) {
            errorMessage.append("• Datum je obavezan!\n");
        } else {
            LocalDate releaseDate = parseDate(dateText);
            if (releaseDate == null) {
                errorMessage.append("• Nevalidan format datuma! Dozvoljeni format: YYYY-MM-DD (npr. 2023-05-15)\n");
            } else {
                // Provera da li je datum u budućnosti ili pre 1888 (prvi film)
                LocalDate firstMovieDate = LocalDate.of(1888, 1, 1);
                LocalDate maxFutureDate = LocalDate.now().plusYears(5);

                if (releaseDate.isBefore(firstMovieDate)) {
                    errorMessage.append("• Datum ne može biti pre 1888. godine!\n");
                } else if (releaseDate.isAfter(maxFutureDate)) {
                    errorMessage.append("• Datum ne može biti više od 5 godina u budućnosti!\n");
                }
            }
        }

        // Validacija trajanja
        String durationText = durationField.getText().trim();
        if (durationText.isEmpty()) {
            errorMessage.append("• Trajanje je obavezno!\n");
        } else {
            try {
                int duration = Integer.parseInt(durationText);
                if (duration <= 0) {
                    errorMessage.append("• Trajanje mora biti veće od 0 minuta!\n");
                } else if (duration > 1000) {
                    errorMessage.append("• Trajanje ne može biti duže od 1000 minuta!\n");
                }
            } catch (NumberFormatException e) {
                errorMessage.append("• Trajanje mora biti broj!\n");
            }
        }

        // Validacija opisa
        String description = descArea.getText().trim();
        if (description.isEmpty()) {
            errorMessage.append("• Opis filma je obavezan!\n");
        } else if (description.length() < 10) {
            errorMessage.append("• Opis mora imati najmanje 10 karaktera!\n");
        } else if (description.length() > 1000) {
            errorMessage.append("• Opis ne sme biti duži od 1000 karaktera!\n");
        }

        // Validacija žanrova
        List<Genre> selectedGenres = getSelectedGenres();
        if (selectedGenres.isEmpty()) {
            errorMessage.append("• Morate izabrati bar jedan žanr!\n");
        }

        return errorMessage.toString();
    }

    private LocalDate parseDate(String dateText) {
        try {
            dateText = dateText.trim();

            // Pokušaj parsiranja u formatu YYYY-MM-DD
            return LocalDate.parse(dateText);

        } catch (Exception e) {
            // Hvata DateTimeParseException i ostale izuzetke
            return null;
        }
    }

    private List<Genre> getSelectedGenres() {
        List<Genre> selectedGenres = new ArrayList<>();
        for (Map.Entry<Genre, JCheckBox> entry : genreCheckboxMap.entrySet()) {
            if (entry.getValue().isSelected()) {
                selectedGenres.add(entry.getKey());
            }
        }
        return selectedGenres;
    }

    private void clearForm() {
        nameField.setText("");
        yearField.setText("");
        durationField.setText("");
        descArea.setText("");

        for (JCheckBox cb : genreCheckboxMap.values()) {
            cb.setSelected(false);
        }
    }

    // Metoda za osvežavanje žanrova ako bude potrebno
    public void refreshGenres() {
        JPanel genrePanel = createGenrePanel();
        // Ovde bi trebalo zameniti stari genrePanel sa novim
        // Ovo je malo kompleksnije jer moramo pronaći i zameniti komponentu
    }

}

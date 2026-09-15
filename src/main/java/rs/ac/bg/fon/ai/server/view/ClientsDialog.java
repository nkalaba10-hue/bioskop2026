/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.view;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import rs.ac.bg.fon.ai.communication.model.Employee;
import rs.ac.bg.fon.ai.server.threads.HandleClientThread;

/**
 *
 * @author nkala
 */
public class ClientsDialog extends javax.swing.JDialog {

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClients;

    public ClientsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadClients();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClients = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Connected Clients");

        // Kreiranje tabele
        tblClients.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Client ID", "Username", "Full Name", "IP Address", "Port"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        jScrollPane1.setViewportView(tblClients);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    private void loadClients() {
        List<HandleClientThread> activeClients = HandleClientThread.getActiveClients();
        DefaultTableModel model = (DefaultTableModel) tblClients.getModel();
        model.setRowCount(0); // Clear existing rows

        for (HandleClientThread client : activeClients) {
            Employee employee = client.getLoggedInEmployee();
            if (employee != null) {
                model.addRow(new Object[]{
                    String.valueOf(client.getId()),
                    employee.getUsername(),
                    employee.getFirstname() + " " + employee.getLastname(),
                    client.getSocket().getInetAddress().getHostAddress(),
                    client.getSocket().getPort()
                });
            } else {
                model.addRow(new Object[]{
                    String.valueOf(client.getId()),
                    "Not logged in",
                    "N/A",
                    client.getSocket().getInetAddress().getHostAddress(),
                    client.getSocket().getPort()
                });
            }
        }
    }
}

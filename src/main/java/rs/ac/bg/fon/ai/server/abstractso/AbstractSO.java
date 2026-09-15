/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.abstractso;

import rs.ac.bg.fon.ai.server.repository.DBBroker;
import rs.ac.bg.fon.ai.server.repository.DbRepository;

/**
 *
 * @author nkala
 */
public abstract class AbstractSO {
    
    protected DbRepository repository;

    public AbstractSO() {
        this.repository = new DBBroker();
    }

    /**
     * Template method koji definise tok izvrsavanja sistemske operacije.
     * 1. Provera preduslova
     * 2. Pokretanje transakcije  
     * 3. Izvršavanje operacije
     * 4. Potvrda transakcije
     * 5. U slučaju greške - rollback transakcije
     */
    public final void execute(Object param) throws Exception {
        try {
            System.out.println("Starting system operation: " + this.getClass().getSimpleName());
            
            precondition(param);
            startTransaction();
            executeOperation(param);
            commitTransaction();
            
            System.out.println("✓ System operation completed successfully: " + this.getClass().getSimpleName());
            
        } catch (Exception e) {
            rollbackTransaction();
            System.out.println("✗ System operation failed: " + this.getClass().getSimpleName() + " - " + e.getMessage());
            throw e; // Prosleđujemo grešku dalje
        }
    }

    /**
     * Proverava preduslove za izvršavanje operacije.
     * @param param objekat nad kojim se vrši operacija
     * @throws Exception ako preduslovi nisu ispunjeni
     */
    protected abstract void precondition(Object param) throws Exception;

    /**
     * Izvršava glavnu logiku sistemske operacije.
     * @param param objekat nad kojim se vrši operacija
     * @throws Exception ako operacija ne uspe
     */
    protected abstract void executeOperation(Object param) throws Exception;

    /**
     * Pokreće transakciju - uspostavlja konekciju sa bazom.
     */
    private void startTransaction() throws Exception {
        repository.connect();
        System.out.println("  → Transaction started");
    }

    /**
     * Potvrđuje transakciju - čuva promene u bazi.
     */
    private void commitTransaction() throws Exception {
        repository.commit();
        System.out.println("  → Transaction committed");
    }

    /**
     * Ponistava transakciju - odbacuje promene u bazi.
     */
    private void rollbackTransaction() throws Exception {
        repository.rollback();
        System.out.println("  → Transaction rolled back");
    }
}

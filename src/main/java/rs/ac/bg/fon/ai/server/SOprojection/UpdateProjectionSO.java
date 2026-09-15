/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SOprojection;

import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.ProjectionStatus;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 *
 * @author nkala
 */
public class UpdateProjectionSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Projection)) {
            throw new Exception("Invalid parameter type - expected Projection");
        }

        Projection projection = (Projection) param;

        if (projection.getId() == null) {
            throw new Exception("Projection ID is required for update");
        }

        if (projection.getSoldTickets() < 0) {
            throw new Exception("Sold tickets cannot be negative");
        }

        if (projection.getHall() == null || projection.getHall().getCapacity() == 0) {
            throw new Exception("Hall capacity information is required");
        }

        // Provera da li broj prodatih karata premašuje kapacitet
        if (projection.getSoldTickets() > projection.getHall().getCapacity()) {
            throw new Exception("Sold tickets cannot exceed hall capacity");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Projection projection = (Projection) param;

        // Automatsko ažuriranje statusa na SOLD_OUT ako su sve karte prodate
        if (projection.getSoldTickets() >= projection.getHall().getCapacity()) {
            projection.setStatus(ProjectionStatus.SOLD_OUT);
            System.out.println("  → Projection marked as SOLD_OUT");
        }

        repository.edit(projection);
        System.out.println("  → Projection updated - Sold tickets: " + projection.getSoldTickets() + ", Status: " + projection.getStatus());
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SOprojection;

import java.time.LocalDateTime;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 *
 * @author nkala
 */
public class DeleteProjectionSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Projection)) {
            throw new Exception("Invalid parameter type - expected Projection");
        }

        Projection projection = (Projection) param;

        if (projection.getId() == null) {
            throw new Exception("Projection ID is required for deletion");
        }

        // Provera da li projekcija postoji
        Projection existing = getExistingProjection(projection.getId());
        if (existing == null) {
            throw new Exception("Projection with ID " + projection.getId() + " not found");
        }

        // Provera da li je projekcija u prošlosti
        LocalDateTime projectionDateTime = LocalDateTime.of(existing.getDate(), existing.getTime());
        if (projectionDateTime.isBefore(LocalDateTime.now())) {
            throw new Exception("Cannot delete past projections");
        }

        // Provera da li ima prodatih karata
        if (existing.getSoldTickets() > 0) {
            throw new Exception("Cannot delete projection with sold tickets");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Projection projection = (Projection) param;
        repository.delete(projection);
        System.out.println("  → Projection deleted: ID " + projection.getId());
    }

    private Projection getExistingProjection(Long projectionId) throws Exception {
        Projection template = new Projection();
        String query = " WHERE p.id = " + projectionId;
        List result = repository.getByQuery(template, query);
        return result.isEmpty() ? null : (Projection) result.get(0);
    }
}

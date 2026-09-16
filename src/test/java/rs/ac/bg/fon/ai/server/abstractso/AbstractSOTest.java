package rs.ac.bg.fon.ai.server.abstractso;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.communication.model.GenericEntity;
import rs.ac.bg.fon.ai.communication.model.Hall;
import rs.ac.bg.fon.ai.server.repository.DbRepository;

class AbstractSOTest {

    @Test
    void testExecutePozivaPreconditionOperacijuIKomit() throws Exception {
        TestSO so = new TestSO(false, false);

        so.execute(new Object());

        assertTrue(so.preconditionCalled);
        assertTrue(so.operationCalled);
        assertTrue(so.repository.connected);
        assertTrue(so.repository.committed);
        assertFalse(so.repository.rolledBack);
    }

    @Test
    void testExecuteRadiRollbackKadaPreconditionNeProdje() {
        TestSO so = new TestSO(true, false);

        Exception e = assertThrows(Exception.class, () -> so.execute(new Object()));

        assertEquals("Neispravan parametar", e.getMessage());
        assertTrue(so.preconditionCalled);
        assertFalse(so.operationCalled);
        assertTrue(so.repository.rolledBack);
    }

    @Test
    void testExecuteRadiRollbackKadaOperacijaNeProdje() {
        TestSO so = new TestSO(false, true);

        Exception e = assertThrows(Exception.class, () -> so.execute(new Object()));

        assertEquals("Greska pri izvrsavanju", e.getMessage());
        assertTrue(so.operationCalled);
        assertTrue(so.repository.rolledBack);
        assertFalse(so.repository.committed);
    }

    private static class TestSO extends AbstractSO {
        private final boolean failPrecondition;
        private final boolean failOperation;
        private boolean preconditionCalled;
        private boolean operationCalled;
        private final TestRepository repository = new TestRepository();

        TestSO(boolean failPrecondition, boolean failOperation) {
            this.failPrecondition = failPrecondition;
            this.failOperation = failOperation;
            super.repository = repository;
        }

        @Override protected void precondition(Object param) throws Exception {
            preconditionCalled = true;
            if (failPrecondition) throw new Exception("Neispravan parametar");
        }

        @Override protected void executeOperation(Object param) throws Exception {
            operationCalled = true;
            if (failOperation) throw new Exception("Greska pri izvrsavanju");
        }
    }

    private static class TestRepository implements DbRepository<GenericEntity> {
        private boolean connected;
        private boolean committed;
        private boolean rolledBack;
        @Override public void connect() { connected = true; }
        @Override public void commit() { committed = true; }
        @Override public void rollback() { rolledBack = true; }
        @Override public List<GenericEntity> getAll(GenericEntity entity) { return List.of(); }
        @Override public void add(GenericEntity entity) { }
        @Override public void edit(GenericEntity entity) { }
        @Override public void delete(GenericEntity entity) { }
        @Override public List<GenericEntity> getByQuery(GenericEntity entity, String query) { return List.of(); }
        @Override public GenericEntity addWithReturn(GenericEntity entity) { return entity; }
        @Override public GenericEntity getById(GenericEntity entity, Long id) { return new Hall(); }
    }
}

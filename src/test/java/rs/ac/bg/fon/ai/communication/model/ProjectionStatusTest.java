package rs.ac.bg.fon.ai.communication.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ProjectionStatusTest {

    @Test
    void testVrednostiStatusa() {
        assertArrayEquals(new ProjectionStatus[]{
            ProjectionStatus.ACTIVE, ProjectionStatus.PAST, ProjectionStatus.SOLD_OUT
        }, ProjectionStatus.values());
    }

    @Test
    void testValueOf() {
        assertEquals(ProjectionStatus.ACTIVE, ProjectionStatus.valueOf("ACTIVE"));
        assertEquals(ProjectionStatus.PAST, ProjectionStatus.valueOf("PAST"));
        assertEquals(ProjectionStatus.SOLD_OUT, ProjectionStatus.valueOf("SOLD_OUT"));
    }

    @Test
    void testNedefinisaneGenericEntityOperacije() {
        assertThrows(UnsupportedOperationException.class, () -> ProjectionStatus.ACTIVE.getTableName());
        assertThrows(UnsupportedOperationException.class, () -> ProjectionStatus.ACTIVE.getId());
    }
}

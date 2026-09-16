package rs.ac.bg.fon.ai.client.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import rs.ac.bg.fon.ai.communication.model.Hall;

/**
 * Omogucava izvoz bioskopskih sala u JSON fajl.
 *
 * U fajl se upisuju identifikator, naziv i kapacitet svake sale. JSON je
 * formatiran radi lakseg citanja.
 *
 * @author nkala
 * @version 1.0
 */
public final class HallJsonExportService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private HallJsonExportService() {
    }

    /**
     * Upisuje listu sala u JSON fajl na zadatoj putanji.
     *
     * @param halls sale koje treba izvesti
     * @param file putanja do JSON fajla
     * @throws IOException ako fajl ne moze da se kreira ili upise
     */
    public static void exportHalls(List<Hall> halls, Path file) throws IOException {
        Path parentDirectory = file.toAbsolutePath().getParent();
        if (parentDirectory != null) {
            Files.createDirectories(parentDirectory);
        }

        List<HallJson> jsonHalls = halls.stream()
                .map(hall -> new HallJson(hall.getId(), hall.getName(), hall.getCapacity()))
                .toList();

        OBJECT_MAPPER.writeValue(file.toFile(), jsonHalls);
    }

    /** JSON zapis jedne sale, namenjen samo za izvoz. */
    private record HallJson(Long id, String name, int capacity) {
    }
}

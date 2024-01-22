package io;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Класс для записи результата.
 *
 * @author Vadim Podogov
 * @since 2024.01.22
 */
public class DeviatedWriter {

    private static final String RESULT_FILENAME = "src\\result.txt";

    public void write(Map<LocalDate, Set<String>> dateToDeviatedSet) throws IOException {
        try (var bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(RESULT_FILENAME)))) {
            for (Map.Entry<LocalDate, Set<String>> entry : dateToDeviatedSet.entrySet()) {
                var sb = new StringBuilder();
                List<String> employees = new ArrayList<>(entry.getValue());
                for (int i = 0; i < employees.size() - 1; i++) {
                    sb.append(employees.get(i)).append(", ");
                }
                if (!employees.isEmpty()) {
                    sb.append(employees.get(employees.size() - 1));
                }
                bw.write(entry.getKey() + " - " + sb);
                bw.newLine();
            }
        }
    }
}

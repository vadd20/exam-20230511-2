package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import entity.Tea;
import entity.TeaBrewing;
import entity.TeaType;

/**
 * Класс для чтения данных из файлов.
 *
 * @author Vadim Podogov
 * @since 2024.01.22
 */
public class DataLoader {

    private static final String TEA_TYPE_FILENAME = "data\\tea_type.csv";
    private static final String TEA_FILENAME = "data\\tea.csv";
    private static final String TEA_BREWING_FILENAME = "data\\tea_brewing.csv";
    String SEPARATOR = ";";

    public List<TeaType> readTeaTypeData() throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(TEA_TYPE_FILENAME))) {
            return lines
                .map(line -> line.split(SEPARATOR))
                .map(data -> new TeaType(
                    Integer.parseInt(data[0]),
                    data[1],
                    Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]),
                    Integer.parseInt(data[5])))
                .collect(Collectors.toList());
        }
    }

    public List<Tea> readTeaData(List<TeaType> teaTypes) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(TEA_FILENAME))) {
            return lines
                .map(line -> line.split(SEPARATOR))
                .map(data -> new Tea(
                    Integer.parseInt(data[0]),
                    data[1],
                    findTeaTypeById(Integer.parseInt(data[2]), teaTypes).orElse(null))
                )
                .collect(Collectors.toList());
        }
    }

    private Optional<TeaType> findTeaTypeById(int id, List<TeaType> teaTypes) {
        return teaTypes.stream()
            .filter(teaType -> teaType.id() == id)
            .findFirst();
    }

    public List<TeaBrewing> readTeaBrewingData(List<Tea> teaList) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(TEA_BREWING_FILENAME))) {
            return lines
                .map(line -> line.split(SEPARATOR))
                .map(data -> new TeaBrewing(
                        data[0],
                        findTeaById(Integer.parseInt(data[1]), teaList).orElse(null),
                        LocalDateTime.parse(data[2]),
                        LocalDateTime.parse(data[3]),
                        (int) (Duration.between(LocalDateTime.parse(data[2]),
                            LocalDateTime.parse(data[3])).getSeconds()),
                        Integer.parseInt(data[4])
                    )
                )
                .collect(Collectors.toList());
        }
    }

    private Optional<Tea> findTeaById(int id, List<Tea> teaList) {
        return teaList.stream()
            .filter(teaType -> teaType.id() == id)
            .findFirst();
    }
}

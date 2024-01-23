import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import entity.TeaBrewing;

/**
 * Обработчик, ищущий людей, ни разу не отклонившихся от процесса заваривания.
 *
 * @author Vadim Podogov
 * @since 2024.01.22
 */
public class DeviationProcessor {

    public Map<LocalDate, Set<String>> findDateToNotDeviatedPersons(List<TeaBrewing> teaBrewingList) {
        var dateToEmployeeNames = new HashMap<LocalDate, Set<String>>();
        var dateToIncorrectBrewingEmployees = new HashMap<LocalDate, Set<String>>();
        teaBrewingList.forEach(teaBrewing -> {
            LocalDate brewingDate = teaBrewing.timeTo().toLocalDate();
            String brewingEmployee = teaBrewing.employeeName();
            if (isBrewingCorrect(teaBrewing)) {
                if (!dateToIncorrectBrewingEmployees
                    .getOrDefault(brewingDate, Collections.emptySet())
                    .contains(brewingEmployee)) {
                    dateToEmployeeNames
                        .computeIfAbsent(brewingDate, k -> new HashSet<>())
                        .add(brewingEmployee);
                }
            } else {
                dateToEmployeeNames.computeIfPresent(brewingDate, (k, v) -> {
                    v.remove(brewingEmployee);
                    return v;
                });
                dateToIncorrectBrewingEmployees
                    .computeIfAbsent(brewingDate, k -> new HashSet<>())
                    .add(brewingEmployee);
            }
        });
        return dateToEmployeeNames;
    }

    private boolean isBrewingCorrect(TeaBrewing brewing) {
        return !brewing.timeFrom().toLocalTime().isBefore(LocalTime.of(8, 0, 0)) &&
            !brewing.timeFrom().toLocalTime().isAfter(LocalTime.of(23, 0, 0)) &&
            brewing.brewingTime() >= brewing.tea().teaType().timeFrom() &&
            brewing.brewingTime() <= brewing.tea().teaType().timeTo() &&
            brewing.temperature() >= brewing.tea().teaType().temperatureFrom() &&
            brewing.temperature() <= brewing.tea().teaType().temperatureTo();
    }
}

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import entity.TeaBrewing;

/**
 * Обработчик, ищущий людей, отклонившихся от процесса заваривания.
 *
 * @author Vadim Podogov
 * @since 2024.01.22
 */
public class DeviationProcessor {

    public Map<LocalDate, Set<String>> findDateToDeviatedPersons(List<TeaBrewing> teaBrewingList) {
        var dateToEmployeeNames = new HashMap<LocalDate, Set<String>>();
        teaBrewingList.forEach(teaBrewing -> {
            if (isBrewingCorrect(teaBrewing)) {
                dateToEmployeeNames.computeIfAbsent(teaBrewing.timeTo().toLocalDate(),
                    k -> new HashSet<>(Set.of(teaBrewing.employeeName())));
            } else {
                dateToEmployeeNames.computeIfPresent(teaBrewing.timeTo().toLocalDate(), (k, v) -> {
                    v.remove(teaBrewing.employeeName());
                    return v;
                });
            }
        });
        return dateToEmployeeNames;
    }

    private boolean isBrewingCorrect(TeaBrewing brewing) {
        return brewing.timeFrom().toLocalTime().isAfter(LocalTime.of(8, 0)) &&
            brewing.timeFrom().toLocalTime().isBefore(LocalTime.of(23, 0)) &&
            brewing.brewingTime() >= brewing.tea().teaType().timeFrom() &&
            brewing.brewingTime() <= brewing.tea().teaType().timeTo() &&
            brewing.temperature() >= brewing.tea().teaType().temperatureFrom() &&
            brewing.temperature() <= brewing.tea().teaType().temperatureTo();
    }
}

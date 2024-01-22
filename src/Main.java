import java.io.IOException;
import java.util.List;
import entity.TeaBrewing;
import io.DataLoader;
import io.DeviatedWriter;

/**
 * Основной класс.
 *
 * @author Vadim Podogov
 * @since 2024.01.22
 */
public class Main {
    public static void main(String[] args) throws IOException {
        var dataLoader = new DataLoader();
        List<TeaBrewing> teaBrewingList =
            dataLoader.readTeaBrewingData(dataLoader.readTeaData(dataLoader.readTeaTypeData()));

        var deviationProcessor = new DeviationProcessor();
        var dateToDeviatedSet = deviationProcessor.findDateToDeviatedPersons(teaBrewingList);

        var deviatedWriter = new DeviatedWriter();
        deviatedWriter.write(dateToDeviatedSet);
    }

}
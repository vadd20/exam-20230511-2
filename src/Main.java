import java.io.IOException;
import java.util.List;
import entity.TeaBrewing;
import io.DataLoader;
import io.Writer;

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
        var dateToNotDeviatedSet = deviationProcessor.findDateToNotDeviatedPersons(teaBrewingList);

        var deviatedWriter = new Writer();
        deviatedWriter.write(dateToNotDeviatedSet);
    }

}
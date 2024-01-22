package entity;

/**
 * Модель типа чая.
 *
 * @author Vadim Podogov
 * @since 2024.01.22
 */
public record TeaType(int id, String name,
                      int timeFrom,
                      int timeTo,
                      int temperatureFrom,
                      int temperatureTo) {
}

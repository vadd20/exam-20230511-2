package entity;

import java.time.LocalDateTime;

/**
 * Модель заварки чая.
 *
 * @author Vadim Podogov
 * @since 2024.01.22
 */
public record TeaBrewing(String employeeName, Tea tea, LocalDateTime timeFrom, LocalDateTime timeTo, int brewingTime,
                         int temperature) {
}

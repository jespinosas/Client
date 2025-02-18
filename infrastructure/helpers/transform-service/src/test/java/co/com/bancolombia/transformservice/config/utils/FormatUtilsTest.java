package co.com.bancolombia.transformservice.config.utils;

import co.com.bancolombia.model.validation.exceptions.TechnicalException;
import org.junit.jupiter.api.Test;

import static co.com.bancolombia.transformservice.config.utils.FormatUtils.getNumberWithFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FormatUtilsTest {

    @Test
    void getNumberWithFormatTest() {
        String numberWithDot = getNumberWithFormat("10", "#.00", false);
        String numberWithoutDot = getNumberWithFormat("10", "#.00", true);

        assertEquals("10.00", numberWithDot);
        assertEquals("1000", numberWithoutDot);
    }

    @Test
    void getNumberWithFormatFailTest() {
        String expectedMessage = "Invalid number format";

        TechnicalException exception = assertThrows(TechnicalException.class, () ->
                getNumberWithFormat("ERROR10", "#.00", false)
        );

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getDateWithFormatTest() {
        String date = FormatUtils.getDateWithFormat("2024-11-27T10:15:30", "yyyy-MM-dd");

        assertEquals("2024-11-27", date);
    }

    @Test
    void getDateWithFormatFailTest() {
        String expectedMessage = "Invalid date format";

        TechnicalException exception = assertThrows(TechnicalException.class, () ->
                FormatUtils.getDateWithFormat("ERROR2024-11-27", "yyyy-MM-dd")
        );

        assertEquals(expectedMessage, exception.getMessage());
    }
}

package ir.fallahpoor.tempo.data.common

import com.google.common.truth.Truth
import org.junit.Test
import java.io.IOException

class ExceptionHumanizerTest {

    @Test
    fun getMessage_should_return_no_internet_connection_error_message() {

        // When
        val actualMessage: String = ExceptionHumanizer.getHumanizedErrorMessage(IOException())

        // Then
        val expectedMessage: String = ExceptionHumanizer.NO_INTERNET_CONNECTION
        Truth.assertThat(actualMessage).isEqualTo(expectedMessage)

    }

    @Test
    fun getMessage_should_return_default_error_message() {

        // When
        val actualMessage: String = ExceptionHumanizer.getHumanizedErrorMessage(Exception())

        // Then
        val expectedMessage: String = ExceptionHumanizer.SOMETHING_WENT_WRONG
        Truth.assertThat(actualMessage).isEqualTo(expectedMessage)

    }

}
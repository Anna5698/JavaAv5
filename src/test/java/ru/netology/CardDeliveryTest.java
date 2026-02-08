package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.conditions.Disabled;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    public String generationDate(int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldSubmittedSuccessfullyForm() {
        String date = generationDate(5, "dd.MM.yyyy");

        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE)
                .setValue(date);
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='phone'] input").setValue("+79992223344");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();

        $("[data-test-id='notification']")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Успешно!"))
                .should(Condition.text("Встреча успешно забронирована на " + date));
    }
}

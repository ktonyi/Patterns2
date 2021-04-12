package ru.netology;
import org.junit.jupiter.api.BeforeEach;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.*;

public class IBankTest {
    @BeforeEach
    void setup () {
        open("http://localhost:9999");
    }
    @Test
    void shouldValidPersonTest() {
        PersonInfo getValidPerson = ValidPerson();
        $("[data-test-id=login] .input__control").setValue(getValidPerson.getLogin());
        $("[data-test-id=password] .input__control").setValue(getValidPerson.getPassword());
        $("[data-test-id=action-login]").click();
        $(".heading").shouldHave(Condition.exactText("Личный кабинет"));
    }

    @Test
    void shouldInvalidLoginTest() {
        PersonInfo getPersonWithInvalidLogin = inValidPerson();
        $("[data-test-id=login] .input__control").setValue(getPersonWithInvalidLogin.getLogin());
        $("[data-test-id=password] .input__control").setValue(getPersonWithInvalidLogin.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.text("Ошибка!"));
    }

    @Test
    void shouldInvalidPasswordTest() {
        PersonInfo getPersonWithInvalidPassword = inValidPassword();
        $("[data-test-id=login] .input__control").setValue(getPersonWithInvalidPassword.getLogin());
        $("[data-test-id=password] .input__control").setValue(getPersonWithInvalidPassword.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.text("Ошибка!"));
    }

    @Test
    void shouldBlockedStatusTest() {
        PersonInfo getPersonWithBlockedStatus = PersonBlocked();
        $("[data-test-id=login] .input__control").setValue(getPersonWithBlockedStatus.getLogin());
        $("[data-test-id=password] .input__control").setValue(getPersonWithBlockedStatus.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.text("заблокирован"));
    }


    @Test
    void shouldWithoutPasswordTest() {
        PersonInfo getValidPerson = ValidPerson();
        $("[data-test-id=login] .input__control").setValue(getValidPerson.getLogin());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldWithoutLoginTest() {
        PersonInfo getValidPerson = ValidPerson();
        $("[data-test-id=password] .input__control").setValue(getValidPerson.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}
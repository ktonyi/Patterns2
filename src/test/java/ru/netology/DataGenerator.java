package ru.netology;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.Locale;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class DataGenerator {
    DataGenerator () {

    }
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    public static void sendRequest(PersonInfo person){

        given()
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(person) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK

    }


    public static  PersonInfo ValidPerson() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        String status = "active";
        PersonInfo person = new PersonInfo ( login, password, status);
        sendRequest(person);
        return person;
    }
    public static PersonInfo inValidPerson() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        String status = "active";
        PersonInfo person = new PersonInfo (login, password, status);
        sendRequest(person);
        return new PersonInfo("Petya", password, status);
    }
    public static PersonInfo inValidPassword() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        String status = "active";
        PersonInfo person = new PersonInfo (login, password, status);
        sendRequest(person);
        return new PersonInfo(login, "bla", status);
    }
    public static PersonInfo PersonBlocked() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        String status = "blocked";
        PersonInfo person = new PersonInfo (login, password, status);
        sendRequest(person);
        return new PersonInfo(login, password, "blocked");
    }


}
package tests;

import bodies.CreateBookingBody;
import bodies.UpdateBookingBody;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static java.sql.Date.valueOf;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;

public class RestAssureTest {

    public static String TOKEN_VALUE;
    public static final String TOKEN = "token";

    @BeforeClass
    public void generateToken() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

        JSONObject body = new JSONObject();
        body.put("password", "password123");
        body.put("username", "admin");

        Response response = RestAssured.given()
                .body(body.toString())
                .post("/auth");
        response.prettyPrint();
        TOKEN_VALUE = response.then().extract().jsonPath().get(TOKEN);
//        RestAssured.requestSpecification.cookie(TOKEN, TOKEN_VALUE);
    }

    @Test
    public void getAllBookingIds() {

        Response response = RestAssured.given().log().all()


                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .get("https://restful-booker.herokuapp.com/booking");
        response.then().statusCode(200);
        response.prettyPrint();
    }


    @Test

    public void createBooking() {


        CreateBookingBody body = new CreateBookingBody().builder()

                .firstname("Sam")
                .lastname("Black")
                .totalprice(100)
                .depositpaid(true)
                .bookingdates(new bookingdates(bookingdates.generateDate("2023-07-07"), bookingdates.generateDate("2023-10-10")))
                .additionalneeds("Breakfast")
                .build();

        Response response = RestAssured.given().log().all()

                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .body(body)
                .post("https://restful-booker.herokuapp.com/booking");
        response.prettyPrint();
    }

    @Test
    public void partialyUpdateBooking() {
        Integer pathId = findFirstBooking();

        JSONObject body = new JSONObject();
        body.put("totalprice", "250");

        Response response = RestAssured.given().log().all()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .cookie(TOKEN, TOKEN_VALUE)
                .body(body.toString())
                .patch("/booking/{id}", pathId);
        response.prettyPrint();
    }

    private Integer findFirstBooking() {
        Response getBookings = RestAssured.get("/booking");
        return getBookings.then().extract().jsonPath().get("bookingid[0]");
    }


    @Test
    public void UpdateBooking() {

        UpdateBookingBody body = new UpdateBookingBody().builder()

                .firstname("UpdatedName")
                .lastname("Black")
                .totalprice(100)
                .depositpaid(true)
                .bookingdates(new bookingdates(bookingdates.generateDate("2023-07-07"), bookingdates.generateDate("2023-10-10")))
                .additionalneeds("Dinner")
                .build();

        Response response = RestAssured.given().log().all()

                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .cookie(TOKEN, TOKEN_VALUE)
                .body(body)
                .put("/booking/394");
        response.prettyPrint();

    }

    @Test
    public void deleteBooking() {

//      JSONObject body = new JSONObject();

        Response response = RestAssured.given().log().all()


                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .delete("/booking/10008");


    }

    @Test
    public void getBookingById() {

        Response response = RestAssured.given().log().all()

                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .get("/booking/394");
        response.prettyPrint();

    }


}





package tests;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ResponseBooking {


    private int bookingid;
    private booking booking;
    private String additionalneeds;


}

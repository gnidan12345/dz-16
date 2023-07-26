package tests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class booking {

    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private bookingdates bookingdates;


}

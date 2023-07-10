package bodies;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookingBody {


        private String firstname;
        private String lastname;
        private int totalprice;
        private boolean depositpaid;
        private tests.bookingdates bookingdates;
        private String additionalneeds;


    }


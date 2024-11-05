package com.vetclinic.vetclinicapp.constants;

import java.time.format.DateTimeFormatter;

public interface Constants {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    String PHONE_NUMBER_REGEX = "^(?:\\+38)?(?:\\(044\\)[ .-]?[0-9]{3}[ .-]?[0-9]{2}[ .-]?[0-9]{2}|044[ .-]?[0-9]{3}[ .-]?[0-9]{2}[ .-]?[0-9]{2}|044[0-9]{7})$";

    String PHONE_NUMBER_FORMATS = "+380445371428, +38(044)5371428, +38(044)537 14 28, " +
            "+38(044)537-14-28, +38(044) 537.14.28, 044.537.14.28, 0445371428, " +
            "044-537-1428, (044)537-1428, 044 537-1428, +83(044)537 14 28, 088 537-1428";

}

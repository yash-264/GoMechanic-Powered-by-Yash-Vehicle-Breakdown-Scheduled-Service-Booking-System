package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Generator;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Component
public class RequestNumberGenerator {


    public static String generateRequestNumber() {

        String prefix = "REQ";


        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));


        String random = UUID.randomUUID().toString().substring(0, 5).toUpperCase();

        return prefix +  timeStamp  + random;
    }
}


package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Generator;

import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Enums.Role;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class CustomerIdGenerator {

    public String generateCustomerId(Role role) {
        String prefix;

        switch (role) {
            case SERVICEPROVIDER:
                prefix = "SERP";
                break;
            case ADMIN:
                prefix = "ADN";
                break;
            case USER:
            default:
                prefix = "USR";
                break;
        }

        return prefix + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

}

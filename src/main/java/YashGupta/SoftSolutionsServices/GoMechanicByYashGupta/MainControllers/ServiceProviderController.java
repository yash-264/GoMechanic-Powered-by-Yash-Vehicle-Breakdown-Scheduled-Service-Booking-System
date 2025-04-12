package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.MainControllers;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.Receipt;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ScheduleService;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceProvider;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceRequest;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Enums.Status;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.ReceiptRepository;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.ScheduledServiceRepository;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.ServiceRequestRepository;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.ServiceProviderServices;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.ServiceRequestServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/service/provider")
public class ServiceProviderController {


    @Autowired
    private ServiceRequestServices serviceRequestServices;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ServiceProviderServices serviceProviderServices;

    @Autowired
    private ScheduledServiceRepository scheduledServiceRepository;



    @GetMapping("/get/my/all/services")
    public ResponseEntity<?> getMyAllServices() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String spId = authentication.getName();

        List<ServiceRequest> onWayRequests = serviceRequestRepository.findServiceRequestByserviceproviderid(spId);
        List<ScheduleService> scheduledRequests = scheduledServiceRepository.findScheduledServiceByserviceproviderid(spId);

        if ((onWayRequests == null || onWayRequests.isEmpty()) && (scheduledRequests == null || scheduledRequests.isEmpty())) {
            return new ResponseEntity<>("There are NO services provided by you!", HttpStatus.NOT_FOUND);
        }

        Map<String, Object> combinedResponse = new HashMap<>();
        combinedResponse.put("onWayRequests", onWayRequests);
        combinedResponse.put("scheduledRequests", scheduledRequests);

        return new ResponseEntity<>(combinedResponse, HttpStatus.OK);

    }




    @PostMapping("/generate/service/receipt")
    public ResponseEntity<?> generateServiceReceipt(@RequestBody Receipt receipt) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }

        String serviceProviderId = authentication.getName();


        if (receiptRepository.findReceiptByrequestnumber(receipt.getRequestnumber()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Receipt already exists for this service request number: " + receipt.getRequestnumber());
        }


        ServiceRequest onWayRequest = serviceRequestRepository.findServiceRequestByrequestnumber(receipt.getRequestnumber());


        ScheduleService scheduledRequest = null;
        if (onWayRequest == null) {
            scheduledRequest = scheduledServiceRepository.findScheduledServiceByrequestnumber(receipt.getRequestnumber());
        }


        if (onWayRequest == null && scheduledRequest == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No service request found with request number: " + receipt.getRequestnumber());
        }


        if ((onWayRequest != null && onWayRequest.getStatus() == Status.CANCELLED) ||
                (scheduledRequest != null && scheduledRequest.getStatus() == Status.CANCELLED)) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Receipt generation failed: This service request was cancelled.");
        }


        String reqServiceProviderId;
        String userId;
        String vehicleNumber;
        String customerName;
        String serviceProviderName;
        LocalDateTime serviceDateTime;

        if (onWayRequest != null) {
            reqServiceProviderId = onWayRequest.getServiceproviderid();
            userId = onWayRequest.getUserid();
            vehicleNumber = onWayRequest.getVehiclenumber();
            customerName = onWayRequest.getCustomername();
            serviceDateTime = onWayRequest.getServicerequestdatetime();
        } else {
            reqServiceProviderId = scheduledRequest.getServiceproviderid();
            userId = scheduledRequest.getUserid();
            vehicleNumber = scheduledRequest.getVehiclenumber();
            customerName = scheduledRequest.getCustomername();
            serviceDateTime = LocalDateTime.of(scheduledRequest.getScheduleddate(), scheduledRequest.getScheduledtime());
        }


        if (!serviceProviderId.equals(reqServiceProviderId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Access denied: This service request does not belong to your account.");
        }


        receipt.setServiceproviderid(reqServiceProviderId);
        receipt.setUserid(userId);
        receipt.setVehiclenumber(vehicleNumber);
        receipt.setCustomername(customerName);
        receipt.setServicerequestdate(serviceDateTime);
        receipt.setServicecompletiondatetime(LocalDateTime.now());
        receipt.setStatus(Status.COMPLETED);

        receiptRepository.save(receipt);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Receipt generated successfully for service request number: " + receipt.getRequestnumber());
    }




    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();


    @PutMapping("/update/account")
    public ResponseEntity<?> updateServiceProvider(@RequestBody ServiceProvider updatedData) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInSpId = authentication.getName();

        ServiceProvider existingSp = serviceProviderServices.findServiceProviderByServiceProviderId(loggedInSpId);

        if (existingSp == null) {
            return new ResponseEntity<>("Service Provider not found", HttpStatus.NOT_FOUND);
        }

        if (updatedData.getBusinessname() != null && !updatedData.getBusinessname().trim().isEmpty()) {
            existingSp.setBusinessname(updatedData.getBusinessname().trim());
        }

        if (updatedData.getBusinessaddress() != null && !updatedData.getBusinessaddress().trim().isEmpty()) {
            existingSp.setBusinessaddress(updatedData.getBusinessaddress().trim());
        }

        if (updatedData.getArea() != null && !updatedData.getArea().trim().isEmpty()) {
            existingSp.setArea(updatedData.getArea().trim());
        }

        if (updatedData.getNearby() != null && !updatedData.getNearby().trim().isEmpty()) {
            existingSp.setNearby(updatedData.getNearby().trim());
        }

        if (updatedData.getUsername() != null && !updatedData.getUsername().trim().isEmpty()) {
            existingSp.setUsername(updatedData.getUsername().trim());
        }

        if (updatedData.getPassword() != null && !updatedData.getPassword().trim().isEmpty()) {

            existingSp.setPassword(passwordEncoder.encode(updatedData.getPassword().trim()));
        }

        if (updatedData.getGmail() != null && !updatedData.getGmail().trim().isEmpty()) {
            existingSp.setGmail(updatedData.getGmail().trim());
        }

        if (updatedData.getPhonenumber() != null && !updatedData.getPhonenumber().trim().isEmpty()) {
            existingSp.setPhonenumber(updatedData.getPhonenumber().trim());
        }

        serviceProviderServices.savePreviousServiceProvider(existingSp);

        return new ResponseEntity<>("Service Provider details updated successfully", HttpStatus.OK);
    }



    @DeleteMapping("/delete/my/account")
    public ResponseEntity<?> deleteServiceProviderAccount() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInServiceProviderId = authentication.getName();

        ServiceProvider existingSP = serviceProviderServices.findServiceProviderByServiceProviderId(loggedInServiceProviderId);

        if (existingSP == null) {
            return new ResponseEntity<>("Service Provider not found", HttpStatus.NOT_FOUND);
        }

        serviceProviderServices.deleteServiceProviderBySpId(loggedInServiceProviderId);

        return new ResponseEntity<>("Your Service Provider account has been successfully deleted.", HttpStatus.OK);
    }



}

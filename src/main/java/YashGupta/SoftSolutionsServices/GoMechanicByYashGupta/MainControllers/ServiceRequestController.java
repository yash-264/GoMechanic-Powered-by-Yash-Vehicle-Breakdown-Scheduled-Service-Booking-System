package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.MainControllers;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ScheduleService;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceProvider;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceRequest;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.User;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Enums.Status;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Generator.RequestNumberGenerator;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.ScheduledServiceRepository;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.EmailServices;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.ServiceProviderServices;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.ServiceRequestServices;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class ServiceRequestController {


    @Autowired
    private ServiceProviderServices serviceProviderServices;


    @Autowired
    private ServiceRequestServices serviceRequestServices;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private ScheduledServiceRepository scheduledServiceRepository;




    @PostMapping("/book/service")
    public ResponseEntity<?> bookService(@RequestBody ServiceRequest serviceRequest) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        String userId=authentication.getName();

        User user=userServices.findUserByUserId(userId);

        List<ServiceProvider> listOfSp = serviceProviderServices.findAllServiceProvider();

        ServiceProvider spByArea = null;

        for (ServiceProvider serviceProvider : listOfSp) {
            if (serviceRequest.getCurraddress().equalsIgnoreCase(serviceProvider.getBusinessaddress())
                    || serviceRequest.getArea().equalsIgnoreCase(serviceProvider.getArea())
                    || serviceRequest.getNearby().equalsIgnoreCase(serviceProvider.getNearby())) {
                spByArea = serviceProvider;
                break;
            }
        }

        if (spByArea == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(" No available service provider found for your location.");
        }

        String requestNumber =RequestNumberGenerator.generateRequestNumber();


        serviceRequest.setUserid(userId);

        serviceRequest.setServiceproviderid(spByArea.getServiceproviderid());


        serviceRequest.setServicerequestdatetime(LocalDateTime.now());

        serviceRequest.setRequestnumber(requestNumber);

        serviceRequest.setServiceprovidername(spByArea.getUsername());


         serviceRequest.setUsercontact(user.getPhonenumber());

        emailServices.sendServiceRequestEmailToServiceProvider(serviceRequest,spByArea.getGmail(),spByArea.getUsername());


        serviceRequestServices.saveServiceRequest(serviceRequest);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(" Service request booked successfully! Your request number is: " + requestNumber);
    }




    @PostMapping("/book/service/by/service/provider/id")
    public ResponseEntity<?> bookServiceBySpNumber(@RequestParam String id, @RequestBody ServiceRequest serviceRequest){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        String userId=authentication.getName();

        User user=userServices.findUserByUserId(userId);

        ServiceProvider serviceProvider=serviceProviderServices.findServiceProviderByServiceProviderId(id);

        if (serviceProvider == null){

            return new ResponseEntity<>("There Is No Service Provider With ServiceProviderId -> "+id,HttpStatus.NOT_FOUND);
        }


        String requestNumber =RequestNumberGenerator.generateRequestNumber();


        serviceRequest.setUserid(userId);
        serviceRequest.setServiceproviderid(serviceProvider.getServiceproviderid());
        serviceRequest.setRequestnumber(requestNumber);
        serviceRequest.setServiceprovidername(serviceProvider.getUsername());
        serviceRequest.setUsercontact(user.getPhonenumber());

        serviceRequest.setServicerequestdatetime(LocalDateTime.now());



        emailServices.sendServiceRequestEmailToServiceProvider(serviceRequest,serviceProvider.getGmail(),serviceProvider.getUsername());

        serviceRequestServices.saveServiceRequest(serviceRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(" Service request booked successfully! Your request number is: " + requestNumber);


    }


    @PostMapping("/schedule/service/by/service/provider/id")
    public ResponseEntity<?> scheduleServiceBySpNumber(@RequestParam String id, @RequestBody ScheduleService scheduleService) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(" Unauthorized: Please login to schedule a service.");
        }

        String userId = authentication.getName();

        User user = userServices.findUserByUserId(userId);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(" User not found.");
        }

        ServiceProvider serviceProvider = serviceProviderServices.findServiceProviderByServiceProviderId(id);

        if (serviceProvider == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(" No Service Provider found with ID: " + id);
        }


        if (scheduleService.getScheduleddate() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Scheduled date is required!");
        }

        if (scheduleService.getScheduledtime() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Scheduled time is required!");
        }

        String requestNumber = RequestNumberGenerator.generateRequestNumber();


        scheduleService.setRequestnumber(requestNumber);
        scheduleService.setUserid(userId);
        scheduleService.setUsercontact(user.getPhonenumber());
        scheduleService.setServiceproviderid(serviceProvider.getServiceproviderid());
        scheduleService.setServiceprovidername(serviceProvider.getUsername());
        scheduleService.setServicerequestdatetime(LocalDateTime.now());


        if (scheduleService.getCustomername() == null || scheduleService.getCustomername().isBlank()) {
            scheduleService.setCustomername(user.getUsername());
        }


        scheduledServiceRepository.save(scheduleService);


        emailServices.sendScheduledServiceEmailToServiceProvider(
                scheduleService,
                serviceProvider.getGmail(),
                serviceProvider.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Service scheduled successfully!\n  Request Number: " + requestNumber+
                        "\n  Scheduled Date Is: "+scheduleService.getScheduleddate()+
                        "\n  Scheduled Time Is: "+scheduleService.getScheduledtime());
    }




}

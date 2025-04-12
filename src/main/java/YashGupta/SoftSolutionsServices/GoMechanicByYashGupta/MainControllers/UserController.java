package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.MainControllers;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.*;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Enums.Status;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.ScheduledServiceRepository;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.ServiceRequestRepository;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.UserRepository;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.GeoServices;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.ServiceProviderServices;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.ServiceRequestServices;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ServiceProviderServices serviceProviderServices;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ServiceRequestServices serviceRequestServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GeoServices geoServices;

    @Autowired
    private ScheduledServiceRepository scheduledServiceRepository;



    @PostMapping("/get/service/providers/by/address/area/nearby")
    public ResponseEntity<?> getServiceProvidersByLocation(@RequestBody GetServiceProviderEntity serviceProvidesrrequst) {

        String area = serviceProvidesrrequst.getArea();
        String address = serviceProvidesrrequst.getAddress();
        String nearby = serviceProvidesrrequst.getNearby();

        List<ServiceProvider> allServiceProviders = serviceProviderServices.findAllServiceProvider();

        List<ServiceProvider> filteredProviders = allServiceProviders.stream()
                .filter(sp ->
                        (area != null && sp.getArea() != null && sp.getArea().toLowerCase().contains(area.toLowerCase())) ||
                                (address != null && sp.getBusinessaddress() != null && sp.getBusinessaddress().toLowerCase().contains(address.toLowerCase())) ||
                                (nearby != null && sp.getNearby() != null && sp.getNearby().toLowerCase().contains(nearby.toLowerCase()))
                )
                .toList();

        if (filteredProviders.isEmpty()) {
            return new ResponseEntity<>("No Service Providers found for the given address details.", HttpStatus.NOT_FOUND);
        }


        filteredProviders.forEach(sp -> {
            sp.setPhonenumber(null);
            sp.setGmail(null);
            sp.setPassword(null);
            sp.setLatitude(null);
            sp.setLongitude(null);
        });

        return new ResponseEntity<>(filteredProviders, HttpStatus.OK);
    }


    @PostMapping("/get/service/providers/by/live/location")
    public ResponseEntity<?> getNearbyServiceProviders(@RequestBody LocationRequest locationRequest) {

        double userLat = locationRequest.getLatitude();
        double userLon = locationRequest.getLongitude();
        double radius = locationRequest.getRadiusinkm();

        List<ServiceProvider> allProviders = serviceProviderServices.findAllServiceProvider();

        List<ServiceProvider> nearbyProviders = allProviders.stream()
                .filter(sp -> {
                    double distance = geoServices.distanceInKm(userLat, userLon, sp.getLatitude(), sp.getLongitude());

                    double roundedDistance = Math.round(distance * 100.0) / 100.0;
                    sp.setDistanceinkm(roundedDistance);

                    return distance <= radius;
                })
                .peek(sp -> {
                    sp.setPhonenumber(null);
                    sp.setGmail(null);
                    sp.setPassword(null);
                    sp.setLatitude(null);
                    sp.setLongitude(null);
                })
                .collect(Collectors.toList());

        if (nearbyProviders.isEmpty()) {
            return new ResponseEntity<>("No service providers found nearby your location.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(nearbyProviders, HttpStatus.OK);
    }




    @GetMapping("/get/my/all/service/requests")
    public ResponseEntity<?> getRequestsByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(" Unauthorized access.");
        }

        String userId = authentication.getName();

        List<ServiceRequest> serviceRequests = serviceRequestRepository.findServiceRequestByuserid(userId);
        List<ScheduleService> scheduleServices = scheduledServiceRepository.findScheduledServiceByuserid(userId);

        boolean noServiceRequests = (serviceRequests == null || serviceRequests.isEmpty());
        boolean noScheduledServices = (scheduleServices == null || scheduleServices.isEmpty());

        if (noServiceRequests && noScheduledServices) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(" No service requests or scheduled services found for your account: " + userId);
        }


        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("ON Way Requests", serviceRequests);
        responseMap.put("Scheduled Services", scheduleServices);

        return ResponseEntity.status(HttpStatus.OK).body("Your All Requests Are: \n");
    }


    @GetMapping("/get/my/onway/service/requests")
    public ResponseEntity<?> getOnWayServiceRequestsByUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(" Unauthorized access. Please login to continue.");
        }

        String userId = authentication.getName();
        List<ServiceRequest> onWayRequests = serviceRequestRepository.findServiceRequestByuserid(userId);

        if (onWayRequests == null || onWayRequests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(" No On-Way (breakdown) service requests found for your account: " + userId);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", " On-Way Service Requests fetched successfully.");
        response.put("onWayRequests", onWayRequests);

        return ResponseEntity.ok(response);
    }



    @GetMapping("/get/my/scheduled/service/requests")
    public ResponseEntity<?> getScheduledServiceRequestsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(" Unauthorized access. Please login to continue.");
        }

        String userId = authentication.getName();
        List<ScheduleService> scheduledRequests = scheduledServiceRepository.findScheduledServiceByuserid(userId);

        if (scheduledRequests == null || scheduledRequests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(" No Scheduled Service Requests found for your account: " + userId);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", " Scheduled Service Requests fetched successfully.");
        response.put("scheduledRequests", scheduledRequests);

        return ResponseEntity.ok(response);
    }





    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();


    @PutMapping("/update/account")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUserData) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String loggedInUserId = authentication.getName();

        User existingUser = userServices.findUserByUserId(loggedInUserId);

        if (existingUser == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }


        if (updatedUserData.getUsername() != null && !updatedUserData.getUsername().trim().isEmpty()) {
            existingUser.setUsername(updatedUserData.getUsername().trim());
        }

        if (updatedUserData.getPassword() != null && !updatedUserData.getPassword().trim().isEmpty()) {

            existingUser.setPassword(passwordEncoder.encode(updatedUserData.getPassword().trim()));
        }


        if (updatedUserData.getGmail() != null && !updatedUserData.getGmail().trim().isEmpty()) {
            existingUser.setGmail(updatedUserData.getGmail().trim());
        }


        if (updatedUserData.getPhonenumber() != null && !updatedUserData.getPhonenumber().trim().isEmpty()) {
            existingUser.setPhonenumber(updatedUserData.getPhonenumber().trim());
        }



        userServices.savePrevious(existingUser);


        return new ResponseEntity<>("User details updated successfully", HttpStatus.OK);
    }



    @DeleteMapping("/delete/my/account")
    public ResponseEntity<?> deleteUserAccount() {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserId = authentication.getName();

        User existingUser = userServices.findUserByUserId(loggedInUserId);

        if (existingUser == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        userRepository.deleteUserByuserid(loggedInUserId);


        return new ResponseEntity<>("Your account has been successfully deleted.", HttpStatus.OK);
    }


    @PutMapping("/cancel/service/request/by/request/id")
    public ResponseEntity<?> cancelServiceRequest(@RequestParam String id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized access.");
        }

        String userId = authentication.getName();


        ServiceRequest onWayRequest = serviceRequestRepository.findServiceRequestByrequestnumber(id);

        if (onWayRequest != null) {
            if (!onWayRequest.getUserid().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not allowed to cancel this on-way request.");
            }

            Status currentStatus = onWayRequest.getStatus();

            if (currentStatus == Status.COMPLETED || currentStatus == Status.CANCELLED) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Cannot cancel a completed or already cancelled on-way request.");
            }

            onWayRequest.setStatus(Status.CANCELLED);
            serviceRequestRepository.save(onWayRequest);

            return ResponseEntity.ok("Your on-way service request with ID " + id + " has been cancelled successfully.");
        }


        ScheduleService scheduledRequest = scheduledServiceRepository.findScheduledServiceByrequestnumber(id);

        if (scheduledRequest != null) {
            if (!scheduledRequest.getUserid().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not allowed to cancel this scheduled request.");
            }

            Status currentStatus = scheduledRequest.getStatus();

            if (currentStatus == Status.COMPLETED || currentStatus == Status.CANCELLED) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Cannot cancel a completed or already cancelled scheduled request.");
            }

            scheduledRequest.setStatus(Status.CANCELLED);
            scheduledServiceRepository.save(scheduledRequest);

            return ResponseEntity.ok("Your scheduled service request with ID " + id + " has been cancelled successfully.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No service request found with ID: " + id);
    }








}


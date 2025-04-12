package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.MainControllers;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.Admin;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceProvider;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceRequest;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.User;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.AdminRepository;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.ServiceProviderRepository;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.ServiceRequestRepository;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.UserRepository;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.AdminServices;
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

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private ServiceProviderServices serviceProviderServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private AdminServices adminServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ServiceRequestServices serviceRequestServices;


    @GetMapping("/get/all/service/providers")
    public ResponseEntity<?> getAllServiceProvider(){

        List<ServiceProvider> allServiceProvider=serviceProviderServices.findAllServiceProvider();

        return new ResponseEntity<>("All Service Providers Are -> \n"+allServiceProvider, HttpStatus.OK);
    }


    @GetMapping("/get/all/users")
    public ResponseEntity<?> getAllUsers(){

        List<User> listOfAllUsers=userServices.getAllUsers();

      if (listOfAllUsers == null){

          return new ResponseEntity<>("There Is No User Available !",HttpStatus.NOT_FOUND);
      }

        return new ResponseEntity<>("Number Of Total Users Are -> "+listOfAllUsers.size() +
                "\n All Users Are -> "+listOfAllUsers,HttpStatus.OK);
    }


    @GetMapping("/get/all/admins")
    public ResponseEntity<?> getAllAdmins(){

        List<Admin> listOfAllAdmins=adminServices.getAllAdmins();

        if (listOfAllAdmins == null){

            return new ResponseEntity<>("There Is No Admin Available !",HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Number Of Total Admins Are -> "+listOfAllAdmins.size() +
                "\n All Admins Are -> "+listOfAllAdmins,HttpStatus.OK);
    }

    @DeleteMapping("/delete/service/provider/by/serviceproviderid")
    public ResponseEntity<?> deleteServiceProvider(@RequestParam String serviceproviderid) {

        ServiceProvider serviceProvider = serviceProviderServices.findServiceProviderByServiceProviderId(serviceproviderid);

        if (serviceProvider == null) {
            return new ResponseEntity<>(" Service Provider not found with ID: " + serviceproviderid, HttpStatus.NOT_FOUND);
        }

//        List<ServiceRequest> serviceRequests = serviceRequestRepository.findServiceRequestByserviceproviderid(serviceproviderid);

//        serviceRequestRepository.deleteAll(serviceRequests);

        return new ResponseEntity<>(
                " Service Provider with ID '" + serviceproviderid + "' has been successfully deleted.\n" +
                        " All associated service requests linked to this provider have also been removed.",
                HttpStatus.ACCEPTED
        );
    }



    @DeleteMapping("/delete/user/by/userid")
    public ResponseEntity<?> deleteUser(@RequestParam String userid) {

        User user = userServices.findUserByUserId(userid);

        if (user == null) {
            return new ResponseEntity<>(" User not found with ID: " + userid, HttpStatus.NOT_FOUND);
        }

        List<ServiceRequest> userRequests = serviceRequestRepository.findServiceRequestByuserid(userid);

        serviceRequestRepository.deleteAll(userRequests);
        userRepository.findUserByuserid(userid);

        return new ResponseEntity<>(
                " User with ID '" + userid + "' has been successfully deleted.\n" +
                        " All associated service requests linked to this user have also been removed.",
                HttpStatus.ACCEPTED
        );
    }



    @DeleteMapping("/delete/admin/by/adminid")
    public ResponseEntity<?> deleteAdmin(@RequestParam String adminid) {

        Admin admin = adminServices.findAdminByAdminId(adminid);

        if (admin == null) {
            return new ResponseEntity<>(" Admin not found with ID: " + adminid, HttpStatus.NOT_FOUND);
        }


        adminServices.deleteAdminByAdminId(adminid);

        return new ResponseEntity<>(
                " Admin with ID '" + adminid + "' has been successfully deleted.\n" +
                        " All associated service requests (if any) linked to this admin have also been removed.",
                HttpStatus.ACCEPTED
        );
    }


    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();


    @PutMapping("/update/account")
    public ResponseEntity<?> updateAdmin(@RequestBody Admin updatedAdminData) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInAdminId = authentication.getName();

        Admin existingAdmin = adminServices.findAdminByAdminId(loggedInAdminId);

        if (existingAdmin == null) {
            return new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
        }

        if (updatedAdminData.getUsername() != null && !updatedAdminData.getUsername().trim().isEmpty()) {
            existingAdmin.setUsername(updatedAdminData.getUsername().trim());
        }

        if (updatedAdminData.getPassword() != null && !updatedAdminData.getPassword().trim().isEmpty()) {

            existingAdmin.setPassword(passwordEncoder.encode(updatedAdminData.getPassword().trim()));

        }

        if (updatedAdminData.getGmail() != null && !updatedAdminData.getGmail().trim().isEmpty()) {
            existingAdmin.setGmail(updatedAdminData.getGmail().trim());
        }

        if (updatedAdminData.getPhonenumber() != null && !updatedAdminData.getPhonenumber().trim().isEmpty()) {
            existingAdmin.setPhonenumber(updatedAdminData.getPhonenumber().trim());
        }

        adminServices.savePreviousAdmin(existingAdmin);


        return new ResponseEntity<>("Admin details updated successfully", HttpStatus.OK);
    }



    @DeleteMapping("/delete/my/account")
    public ResponseEntity<?> deleteAdminAccount() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInAdminId = authentication.getName();

        Admin existingAdmin = adminServices.findAdminByAdminId(loggedInAdminId);

        if (existingAdmin == null) {
            return new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
        }

        adminServices.deleteAdminByAdminId(loggedInAdminId);

        return new ResponseEntity<>("Your admin account has been successfully deleted.", HttpStatus.OK);
    }



}

package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.MainControllers;

import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.AdminServices;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.ServiceProviderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Config.JwtUtil;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.AuthenticationRequest;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.Admin;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceProvider;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.User;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.UserServices;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/go/mechanic/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServices userServices;

    @Autowired
    private AdminServices adminService;

    @Autowired
    private ServiceProviderServices serviceProviderService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/user")
    public ResponseEntity<?> userLogin(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword())
        );

        User user = userServices.findUserByUserId(request.getId());
        String token = jwtUtil.generateToken(user.getUserid(), "user");

        Map<String, String> response = new LinkedHashMap<>();

        response.put("message", " User login successful!");
        response.put("userId", user.getUserid());
        response.put("UserName", user.getUsername());
        response.put("role", "user");
        response.put("token", token);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/admin")
    public ResponseEntity<?> adminLogin(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword())
        );

        Admin admin = adminService.findAdminByAdminId(request.getId());
        String token = jwtUtil.generateToken(admin.getAdminid(), "admin");

        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", " Admin login successful!");
        response.put("adminId", admin.getAdminid());
        response.put("UserName", admin.getUsername());
        response.put("role", "admin");
        response.put("token", token);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/service/provider")
    public ResponseEntity<?> serviceProviderLogin(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword())
        );

        ServiceProvider sp = serviceProviderService.findServiceProviderByServiceProviderId(request.getId());
        String token = jwtUtil.generateToken(sp.getServiceproviderid(), "serviceprovider");

        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", " Service Provider login successful!");
        response.put("serviceProviderId", sp.getServiceproviderid());
        response.put("UserName", sp.getUsername());
        response.put("role", "serviceprovider");
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}

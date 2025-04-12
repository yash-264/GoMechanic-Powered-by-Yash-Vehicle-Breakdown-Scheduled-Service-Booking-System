package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services;

import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.Admin;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceProvider;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserServices userServices;

    @Autowired
    private AdminServices adminServices;

    @Autowired
    private ServiceProviderServices serviceProviderServices;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        User user = userServices.findUserByUserId(id);

        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserid())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();
        }


        Admin admin = adminServices.findAdminByAdminId(id);
        if (admin != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(admin.getAdminid())
                    .password(admin.getPassword())
                    .roles(admin.getRole().name())
                    .build();
        }


        ServiceProvider sp = serviceProviderServices.findServiceProviderByServiceProviderId(id);
        if (sp != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(sp.getServiceproviderid())
                    .password(sp.getPassword())
                    .roles(sp.getRole().name())
                    .build();
        }

        throw new UsernameNotFoundException("No user found with id: " + id);
    }

}


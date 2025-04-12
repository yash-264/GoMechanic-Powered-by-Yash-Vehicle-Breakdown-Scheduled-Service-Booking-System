package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.Admin;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServices {

    @Autowired
    private AdminRepository adminRepository;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();


    public void saveAdmin(Admin admin){

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        adminRepository.save(admin);

    }

    public void savePreviousAdmin(Admin admin){

        adminRepository.save(admin);

    }

    public Admin findAdminByAdminId(String id){

        return adminRepository.findAdminByadminid(id);
    }


    public List<Admin> getAllAdmins(){

        return adminRepository.findAll();
    }

    public void deleteAdminByAdminId(String adminid){

        adminRepository.deleteAdminByadminid(adminid);
    }
}

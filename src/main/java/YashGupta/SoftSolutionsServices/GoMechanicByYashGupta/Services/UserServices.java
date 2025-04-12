package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.User;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    @Autowired
    public UserRepository userRepository;


    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();



    public void saveUser(User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    public void savePrevious(User user){

        userRepository.save(user);
    }


    public User findUserByUserId(String id){

        return userRepository.findUserByuserid(id);
    }

    public List<User> getAllUsers(){

        return userRepository.findAll();
    }


}

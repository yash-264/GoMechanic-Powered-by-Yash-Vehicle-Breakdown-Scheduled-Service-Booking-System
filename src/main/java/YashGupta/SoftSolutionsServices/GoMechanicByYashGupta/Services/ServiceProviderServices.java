package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceProvider;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceProviderServices {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;


    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();



    public void saveServiceProvider(ServiceProvider serviceprovider){

        serviceprovider.setPassword(passwordEncoder.encode(serviceprovider.getPassword()));

        serviceProviderRepository.save(serviceprovider);

    }

    public void savePreviousServiceProvider(ServiceProvider serviceprovider){

        serviceProviderRepository.save(serviceprovider);

    }


    public List<ServiceProvider> findAllServiceProvider(){

        return serviceProviderRepository.findAll();
    }


    public ServiceProvider findServiceProviderByServiceProviderId(String spId){


        return serviceProviderRepository.findServiceProviderByserviceproviderid(spId);
    }



    public void deleteServiceProviderBySpId(String spId){

        serviceProviderRepository.deleteServiceProvideByserviceproviderid(spId);
    }


}

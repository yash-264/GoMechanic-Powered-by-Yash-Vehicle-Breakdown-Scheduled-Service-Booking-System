package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceRequest;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceRequestServices {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;



    public void saveServiceRequest(ServiceRequest servicerequest){

        serviceRequestRepository.save(servicerequest);
    }



}

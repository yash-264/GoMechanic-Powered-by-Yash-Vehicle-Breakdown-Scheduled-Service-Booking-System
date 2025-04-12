package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories;

import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceRequest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ServiceRequestRepository extends MongoRepository<ServiceRequest, ObjectId> {


    List<ServiceRequest> findServiceRequestByuserid(String userid);

    List<ServiceRequest> findServiceRequestByserviceproviderid(String spid);


    ServiceRequest findServiceRequestByrequestnumber(String requestnumber);
}

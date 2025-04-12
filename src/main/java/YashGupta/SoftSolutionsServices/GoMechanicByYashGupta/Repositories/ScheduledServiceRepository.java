package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories;

import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ScheduleService;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceRequest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScheduledServiceRepository extends MongoRepository<ScheduleService, ObjectId> {



    List<ScheduleService> findScheduledServiceByuserid(String userid);


    List<ScheduleService> findScheduledServiceByserviceproviderid(String spid);


    ScheduleService findScheduledServiceByrequestnumber(String requestnumber);
}

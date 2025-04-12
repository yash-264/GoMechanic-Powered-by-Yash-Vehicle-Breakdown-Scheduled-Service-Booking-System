package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Repositories;

import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.Admin;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, ObjectId> {


    Admin findAdminByadminid(String adminid);

    void deleteAdminByadminid(String adminid);
}

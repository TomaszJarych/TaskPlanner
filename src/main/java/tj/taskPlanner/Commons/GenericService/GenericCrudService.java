package tj.taskPlanner.Commons.GenericService;

import java.io.Serializable;
import java.util.List;

public interface GenericCrudService <D, I extends Serializable> {
	
	D getById(I id);
	
	D saveToDB(D dto);
	
	Boolean deleteById(I id);
	
	List<D> getAllFromDB();

}

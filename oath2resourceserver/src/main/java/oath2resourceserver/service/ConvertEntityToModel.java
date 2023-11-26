package oath2resourceserver.service;

public interface ConvertEntityToModel{

	public<F,D> D convertEntityToModel(F clazFrom, D clazDest);
	
}

package oauth2ResourcesServer.websecurity.service;

public interface ConvertEntityToModel{

	public<F,D> D convertEntityToModel(F clazFrom, D clazDest);
	
}

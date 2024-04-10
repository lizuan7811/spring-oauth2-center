package oauth2AuthorizeServer.service;

public interface ConvertEntityToModel{

	public<F,D> D convertEntityToModel(F clazFrom, D clazDest);
	
}

//package spring.boot.oath2.oath2.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//
//import org.keycloak.common.ClientConnection;
//import org.keycloak.services.filters.AbstractRequestFilter;
//
//public class EmbeddedKeycloakRequestFilter extends AbstractRequestFilter implements Filter {
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		request.setCharacterEncoding("UTF=8");
//		ClientConnection clientConnection = createConnection((HttpServletRequest) request);
//		filter(clientConnection, (session) -> {
//			try {
//				chain.doFilter(request, response);
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//		});
//	}
//
//	private ClientConnection createConnection(HttpServletRequest request) {
//	
//		return new ClientConnection() {
//				
//				@Override
//			   public String getRemoteAddr(){
//					return request.getRemoteAddr();
//				}
//				
//				@Override
//				public String getRemoteHost(){
//					return request.getRemoteHost();
//				}
//				
//				@Override
//				public int getRemotePort(){
//					return request.getRemotePort();
//				}
//				
//				@Override
//				public String getLocalAddr(){
//					return request.getLocalAddr();
//				}
//				
//				@Override
//				public int getLocalPort(){
//					return request.getLocalPort();
//				}
//		};
//		
//	}
//
//}

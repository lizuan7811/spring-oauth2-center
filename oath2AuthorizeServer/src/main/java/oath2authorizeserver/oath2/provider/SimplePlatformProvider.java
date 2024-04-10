//package oath2authorizeserver.oath2.provider;
//
//import java.io.File;
//
//import org.keycloak.Config.Scope;
//import org.keycloak.platform.PlatformProvider;
//import org.keycloak.services.ServicesLogger;
//
//public class SimplePlatformProvider implements PlatformProvider{
//
//	Runnable shutdownHook;
//	
//	@Override
//	public void onStartup(Runnable startupHook) {
//		startupHook.run();
//	}
//	
//	@Override
//	public void onShutdown(Runnable shutdownHook) {
//		this.shutdownHook=shutdownHook;
//	}
//	
//	@Override
//	public void exit(Throwable cause) {
//		ServicesLogger.LOGGER.fatal(cause);
//	}
//	
//	private void exit(int status) {
//		new Thread() {
//			@Override
//			public void run() {
//				System.exit(status);
//			}
//		}.start();
//	}
//	
//	@Override
//	public File getTmpDirectory() {
//		return new File(System.getProperty("java.io.tmpdir"));
//	}
//	
//	@Override
//	public ClassLoader getScriptEngineClassLoader(Scope sciptProvider) {
//		return null;
//	}
//}

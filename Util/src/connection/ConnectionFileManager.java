package connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;

import javax.naming.directory.InvalidAttributesException;

public class ConnectionFileManager<V> {

	private static String CONFIG_DIR;

	private static final String PROPERTIES_KEY_POOL_DRIVER = "pool.driver";
	private static final String PROPERTIES_KEY_POOL_URL = "pool.url";
	private static final String PROPERTIES_KEY_POOL_USER = "pool.user";
	private static final String PROPERTIES_KEY_POOL_PASSWORD = "pool.password";

	public ConnectionFileManager() throws InvalidAttributesException{
		try {
		String config = System.getenv("CONFIG_BD_DIR");		
		if(config == null){
			throw new RuntimeException("Variavel de ambiente CONFIG_BD_DIR nao encontrada. Favor criar a variavel e indicar para um diretorio com uma pool de conexao valida conforme arquivo ExemploPOOL.dpbool nos documentos do projeto.");
		}
		// deixa o caminho no formato D:/Billing/CONFIG_DIR/		
		config = config.replaceAll("\\\\", "/").trim();
		if(!config.endsWith("/")){
			config += "/";
		}
		CONFIG_DIR = config;
		}catch (Exception e) {
			CONFIG_DIR = "";
			throw new InvalidAttributesException(this.getClass()+" - Nao foi possivel efetuar a inicializacao da variavel ambiente CONFIG_BD_DIR.");
		}
	}
	
	public ConnectionParams getConnectionParams(V connName) throws FileNotFoundException {
		ConnectionParams cp = null;
		String [] files = getFilesNames(connName);
		cp = getValuesFromFiles(files);
		return cp;
	}

	private String[] getFilesNames(V connName) throws FileNotFoundException {

		String[] fileNames = null;

		try {
			File dir = new File(CONFIG_DIR);
			ConnectionFileNameFilter<V> filter = new ConnectionFileNameFilter<V>(connName);
			fileNames = dir.list(filter);
		} catch (Exception e) {
			throw new FileNotFoundException("Nao foi possivel ler os arquivos do diretorio: " + CONFIG_DIR);
		}
		return fileNames;
	}

	private ConnectionParams getValuesFromFiles(String[] files) throws InvalidParameterException {

		ConnectionParams cp = null;
		String url = null;
		String driver = null;
		String user = null;
		String password = null;
		File file = null;
		BufferedReader br = null;
		for(String f : files) {
			file = new File(CONFIG_DIR + f);
			if (file.exists() && file.isFile()) {
				try {
					br = new BufferedReader(new FileReader(file));
					String st;
					while ((st = br.readLine()) != null) {
						if (st.startsWith(PROPERTIES_KEY_POOL_DRIVER)) {
							driver=st.substring(st.indexOf("=")+1).replaceAll("\\s","");
						}
						if (st.startsWith(PROPERTIES_KEY_POOL_URL)) {
							url=st.substring(st.indexOf("=")+1).replaceAll("\\s","");
						}
						if (st.startsWith(PROPERTIES_KEY_POOL_USER)) {
							user=st.substring(st.indexOf("=")+1).replaceAll("\\s","");
						}
						if (st.startsWith(PROPERTIES_KEY_POOL_PASSWORD)) {
							password=st.substring(st.indexOf("=")+1).replaceAll("\\s","");
						}
					}
					cp = new ConnectionParams(driver, url, user, password);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {
						if(br != null)
							br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	
			}

		}
		return cp;
	}

}

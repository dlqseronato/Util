package connection;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.directory.InvalidAttributesException;



public abstract class ConnectionFactory<V> {

	public Connection getConnection(V connName) throws ClassNotFoundException, InvalidAttributesException {
		Connection conn = null;
		try {
			ConnectionFileManager<V> cfm = new ConnectionFileManager<V>();
			ConnectionParams cp = cfm.getConnectionParams(connName);
			if(cp != null) {
				Class.forName(cp.getDriver());
				conn = DriverManager.getConnection(cp.getUrl(), cp.getUser(), cp.getPassword());
				conn.setAutoCommit(false);
			}else {
				throw new FileNotFoundException(this.getClass()+" - Nao foi encontrado um arquivo de DBPOOL com o nome "+connName+ ". Favor verificar se o arquivo existe na sua pasta relacionada a variavel ambiente CONFIG_BD_DIR");
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public Connection getConnectionCustom(V connName) throws ClassNotFoundException {
		Connection conn = null;
		try {
			ConnectionParams cp = new ConnectionParams("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/POLYGON_OWNER?useSSL=false", "root", "Kindor*123");
			if(cp != null) {
				//Class.forName(cp.getDriver());
				conn = DriverManager.getConnection(cp.getUrl(), cp.getUser(), cp.getPassword());
				conn.setAutoCommit(false);
			}


		} catch (SQLException e) {
			throw new RuntimeException(e);
		} 
		return conn;
	}
	


}
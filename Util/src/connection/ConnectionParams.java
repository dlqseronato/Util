package connection;

import java.security.InvalidParameterException;

public class ConnectionParams {
	private String driver = new String();
	private String url = new String();
	private String user = new String();
	private String password = new String();

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ConnectionParams(String driver, String url, String user, String password) {
		super();
		if (driver != null)
			this.driver = driver;
		else
			throw new InvalidParameterException(this.getClass()
					+ " - O driver de conexao n�o pode ser nulo na constru��o da classe. Necess�rio checar se o arquivo DBPOLL cont�m um valor dbpool.driver v�lido.");

		if (url != null)
			this.url = url;
		else
			throw new InvalidParameterException(this.getClass()
					+ " - A URL de conexao n�o pode ser nulo na constru��o da classe. Necess�rio checar se o arquivo DBPOLL cont�m um valor dbpool.url v�lido.");

		if (user != null)
			this.user = user;
		else
			throw new InvalidParameterException(this.getClass()
					+ " - O driver de conexao n�o pode ser nulo na constru��o da classe. Necess�rio checar se o arquivo DBPOLL cont�m um valor dbpool.user v�lido.");

		if (password != null)
			this.password = password;
		else
			throw new InvalidParameterException(this.getClass()
					+ " - O driver de conexao n�o pode ser nulo na constru��o da classe. Necess�rio checar se o arquivo DBPOLL cont�m um valor dbpool.password v�lido.");

	}

}

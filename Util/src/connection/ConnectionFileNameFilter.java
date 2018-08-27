package connection;

import java.io.File;
import java.io.FilenameFilter;

public class ConnectionFileNameFilter<V> implements FilenameFilter {
	String fileFilterName;
	
	public ConnectionFileNameFilter(V connName){
		this.fileFilterName = (String) connName;
	}
	
	public boolean accept(File dir, String file)
	{
		return file.endsWith(".dbpool") && file.startsWith(fileFilterName);
	}

}

package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Log {
	private static PatternLayout layout = new PatternLayout("[%d] %p : %m%n");
	private static String config;
	/*
	static {
		config = System.getenv("CONFIG_LOG_DIR");		
		if(config == null){
			throw new RuntimeException("Variavel de ambiente CONFIG_LOG_DIR nao encontrada. Favor criar a variavel e indicar para um diretorio que sera utilizado para os logs da aplicacao.");
		}
	}
	*/

	private static Logger logger;

	public static void info(String text) {
		if(logger == null){
			init();
		}
		logger.info(text);
	}
	
	@SuppressWarnings("rawtypes")
	public static void infoSql(Class c,String sql) {
		if(logger == null){
			init();
		}
		String newText = "Classe: "+c.getName()+" - Executando a query: "+sql;
		logger.info(newText);
	}

	public static void error(String text, Throwable e) {
		if(logger == null){
			init();
		}
		logger.error(text, e);
	}

	private synchronized static void init() {
		logger = Logger.getLogger("Aplicacao - ");
		adicionaAppenderConsole();
		//adicionaAppenderFile();
		//compactarArquivosLog();
	}

	private static void adicionaAppenderConsole() {
		ConsoleAppender console = new ConsoleAppender(layout);
		console.setThreshold(Level.ALL);
		console.activateOptions();
		logger.addAppender(console);
	}

	private static void adicionaAppenderFile() {
		File log = new File(config);
		System.out.println("diretorio "+log);
		if (!log.exists() || !log.isDirectory()) {
			System.out.println("Criado diretorio "+log);
			log.mkdir();
		}
		DailyRollingFileAppender file = null;
		
		try {
			file = new DailyRollingFileAppender(layout, "app.log","'.'yyyy-MM-dd");
			file.setAppend(true);
			file.setThreshold(Level.ALL);
			file.activateOptions();
			logger.addAppender(file);
		} catch (IOException e1) {
			Log.error("Erro ao criar appender de Arquivo", e1);
		}
	}


	private static void compactarArquivosLog() {
		File log = new File(config);

		if (log.exists()) {
			FilenameFilter filtro = new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (name.matches("^app.log.[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
						return true;
					}

					return false;
				}
			};

			if (log.isDirectory()) {
				List<File> arquivos = Arrays.asList(log.listFiles(filtro));

				for (File arquivo : arquivos) {
					compactarArquivo(arquivo, true);
				}
			}
		}
	}

	public static synchronized void compactarArquivo(File arquivo, boolean removerArquivoOriginal) {
		byte[] buffer = new byte[1024];
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		FileInputStream in = null;
		try {
			fos = new FileOutputStream(arquivo.getAbsolutePath() + ".zip");
			zos = new ZipOutputStream(fos);
			ZipEntry ze = new ZipEntry(arquivo.getName());
			zos.putNextEntry(ze);
			in = new FileInputStream(arquivo.getAbsolutePath());
			int len;

			while ((len = in.read(buffer)) > 0) {
				zos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			Log.error("Falha ao gerar arquivo ZIP. O Arquivo não foi encontrado. Arquivo: " + arquivo.getName(), e);
		} catch (IOException e) {
			Log.error("Falha ao gerar arquivo ZIP. O Arquivo não pode ser escrito. Arquivo: " + arquivo.getName(), e);
		} finally {
			try {
				in.close();
				zos.closeEntry();
				zos.close();

				if (removerArquivoOriginal) {
					arquivo.delete();
				}
			} catch (IOException e) {
				Log.error("Falha ao fechar arquivo ZIP. Arquivo: " + arquivo.getName(), e);
			}
		}
	}
}

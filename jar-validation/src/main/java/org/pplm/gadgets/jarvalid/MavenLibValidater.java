package org.pplm.gadgets.jarvalid;

import java.io.File;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MavenLibValidater {
	private static Logger logger = LoggerFactory.getLogger(MavenLibValidater.class);
	
	public static void clearCorruptJars(Collection<String> corruptJars) {
		if (corruptJars == null || corruptJars.size() == 0) {
			return;
		}
		corruptJars.stream().forEach(file -> {
			logger.info("jar file [" + file + "] deletes");
			new File(file).delete();
		});
	}
	
	public static void validate(File file, boolean clear) {
		Collection<String> corruptJars = JarValidater.validateJars(file);
		if (clear) {
			logger.info("clear corrupt jars");
			clearCorruptJars(corruptJars);
			System.out.println("clear finish, you can execute 'mvn update' to update project maven lib");
		} else {
			corruptJars.stream().forEach(f -> System.out.println(f.toString() + " is corrupt"));
		}
	}
	
	public static void main(String[] args) {
		if(args.length < 1) {
			System.err.println("Usage: java -jar maven-lib-validation.jar <maven lib path> [-d]");
			System.exit(1);
		}
		File file = new File(args[0]);
		if (!file.isDirectory()) {
			System.err.println("[" + args[0] + "] is not a directory");
			System.exit(2);
		}
		boolean clear = false;
		if (args.length > 1) {
			if ("-d".equals(args[1])) {
				clear = true;
			}
			
		}
		validate(file, clear);
	}
	
}

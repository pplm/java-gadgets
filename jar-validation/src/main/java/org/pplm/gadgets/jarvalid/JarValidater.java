package org.pplm.gadgets.jarvalid;

import java.io.File;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

public class JarValidater {

	public static Collection<String> validateJars(String path) {
		return validateJars(new File(path));
	}
	
	public static Collection<String> validateJars(File path) {
		if (path == null || !path.isDirectory()) {
			return null;
		}
		return FileUtils.listFiles(path, new String[] { "jar" }, true).stream()
				.filter(jarFile -> !validate(jarFile))
				.map(jarFile -> jarFile.toString())
				.collect(Collectors.toList());
	}

	public static boolean validate(File jarFile) {
		return ZipValidater.validate(jarFile);
	}

	public static boolean validate(String jarFile) {
		return ZipValidater.validate(jarFile);
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Usage: java -jar jar-validation.jar <file | path>");
			System.exit(1);
		}
		File file = new File(args[0]);
		if (!file.exists()) {
			System.err.println("file [" + args + "] is not exists");
			System.exit(2);
		}
		if (file.isFile()) {
			validate(file);
		} else {
			validateJars(file).stream().forEach(f -> System.out.println(f.toString() + " is corrupt"));
		}
		System.exit(0);
	}
	
}

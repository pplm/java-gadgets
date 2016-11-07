package org.pplm.gadgets.jarvalid;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipValidater {
	
	private static Logger logger = LoggerFactory.getLogger(ZipValidater.class);
	
	public static boolean validate(String file) {
		return validate(new File(file));
	}
	
	/**
	 * validate a zip file
	 * @param file
	 * @return
	 */
	public static boolean validate (File file) {
		if (file == null) {
			return false;
		}
		if (!file.exists() || file.isDirectory()) {
			return false;
		}
		ZipFile zipFile = null;
		InputStream inputStream = null;
		List<? extends ZipEntry> zipEntryList = null;
		byte[] buffer = new byte[8192];
		try {
			zipFile = new ZipFile(file, ZipFile.OPEN_READ);
			zipEntryList =  Collections.list(zipFile.entries());
			for (ZipEntry zipEntry : zipEntryList) {
				if (!zipEntry.isDirectory()) {
					inputStream = zipFile.getInputStream(zipEntry);
					while(inputStream.read(buffer) == 8192);
				}
			}
		} catch (IOException e) {
			logger.warn("[" + file.toString() + "] is corrupt");
			return false;
		} finally {
			try {
				zipFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("[" + file.toString() + "] is ok");
		return true;
	}
}

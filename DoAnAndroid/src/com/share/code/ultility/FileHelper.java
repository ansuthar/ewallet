/*
 * Author: ittran90
 * Create Date: 31/03/2012
 * Description: Helper for access to file system
 */
package com.share.code.ultility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

public class FileHelper {

	public static int chmod(File path, int mode) throws Exception
	{
		Class fileUtils = Class.forName("android.os.FileUtils");
		Method setPermissions = fileUtils.getMethod("setPermissions",
				String.class, int.class, int.class, int.class);
		return (Integer) setPermissions.invoke(null, path.getAbsolutePath(),
				mode, -1, -1);
	}
	
	public static void SplitFile(String filePath, String extension)
			throws Exception {
		String base = filePath;
		String ext = extension;
		int split = 1024 * 1024;
		byte[] buf = new byte[1024];
		int chunkNo = 1;
		File inFile = new File(base + ext);
		FileInputStream fis = new FileInputStream(inFile);
		while (true) {
			FileOutputStream fos = new FileOutputStream(new File(base + chunkNo
					+ ext));
			for (int i = 0; i < split / buf.length; i++) {
				int read = fis.read(buf);
				fos.write(buf, 0, read);
				if (read < buf.length) {
					fis.close();
					fos.close();
					return;
				}
			}
			fos.close();
			chunkNo++;
		}
	}
}

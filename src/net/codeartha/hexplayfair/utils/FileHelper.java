package net.codeartha.hexplayfair.utils;

import java.io.File;
import java.text.SimpleDateFormat;

public class FileHelper {

	/**
	 * Create a copy of a file in same directory but can rename and change it's
	 * extension
	 * 
	 * @param oldPath
	 * @param newExtension
	 * @param newFileName
	 * @return String newPath
	 */
	public static String filePathGen(String oldPath, String newExtension,
			String newFileName) {
		if (newFileName.contains(".")) {
			newFileName = newFileName
					.substring(0, newFileName.lastIndexOf('.'));
		}

		if (newExtension.charAt(0) != '.') {
			newExtension = "." + newExtension;
		}

		String newPath = "";
		if (newFileName != null) {
			newPath = oldPath.substring(0, oldPath.lastIndexOf('\\') + 1)
					+ newFileName + newExtension;
		} else {
			newPath = oldPath.substring(0, oldPath.lastIndexOf('.'))
					+ newExtension;
		}

		return newPath;
	}

	/**
	 * Generates unencrypted headers to append at the start of files to keep
	 * track of original file name, author, creation date,...
	 */
	public static String[] generateFileHeader(String path, String password,
			String salt) {
		String[] result = new String[5];
		File srcFile = new File(path);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		// UserPrincipal owner = srcFile.getOwner(path);

		result[0] = path;
		result[1] = srcFile.getAbsolutePath().substring(0,
				srcFile.getAbsolutePath().indexOf('.')); // file name
		result[2] = srcFile.getAbsolutePath().substring(
				srcFile.getAbsolutePath().indexOf('.'),
				srcFile.getAbsolutePath().length()); // file extension
		result[3] = sdf.format(srcFile.lastModified()); // last modification
														// date
		// result[4] = owner.getName();

		try {
			// result[4] = new MD5().getMD5Hash(); //password hash, "salted",
			// used to primarily verify the password and avoid corrupted files
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}

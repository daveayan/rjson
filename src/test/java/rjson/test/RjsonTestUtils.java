package rjson.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import rjson.Rjson;
import rjson.utils.RjsonUtil;

public class RjsonTestUtils {
	public static String folderLocation = "./src/test/java/DATA-testrj.domain.MethodToTest/";

	public static String serialize(Object object) {
		return RjsonUtil.completeSerializer().toJson(object);
	}
	
	public static Object deserialize(String filepathandname) throws IOException {
		return Rjson.newInstance().andRecordAllModifiers().toObject(
				fileAsString(folderLocation + filepathandname));
	}

	public static String fileAsString(String file) throws IOException {
		return FileUtils.readFileToString(new File(file));
	}
}
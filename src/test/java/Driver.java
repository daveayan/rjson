import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;


public class Driver {
	public static void main(String[] args) throws IOException, JSONException {
		String json = FileUtils.readFileToString(new File("/Users/adave/development/studios.json"));
		JSONObject jo = new JSONObject(json);
		System.out.println(jo);
	}
}
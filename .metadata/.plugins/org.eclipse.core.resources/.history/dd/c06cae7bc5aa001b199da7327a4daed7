import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONException;
import org.json.JSONObject;


public class RequestClient {
	Map<String, String> resultMap;
	String key;
	HttpURLConnection con;


	public RequestClient(String link, String key, String type) {
		this.key = key;

		//Authorization for the HTTP Request
		try {
			con = (HttpURLConnection) new URL(link).openConnection();
			con.setRequestProperty("X-API-Key", key);
			//type of request
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.setDoInput(true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public JSONObject getObject(String query) {
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			JSONObject r = new JSONObject(response.toString());
			this.createMap(r);
			return new JSONObject(response.toString());
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
   private void createMap(JSONObject r) {
	   resultMap = new TreeMap<String, String>();
	   
	   
	   
   }
}

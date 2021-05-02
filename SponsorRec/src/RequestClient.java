import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestClient {
	Map<String, Set<String>> resultMap;
	String key;
	String type;

	public RequestClient(String key, String type) {
		this.key = key;
		this.type = type;

	}

	public JSONObject getObject(String link) {

		try {
			HttpURLConnection con = (HttpURLConnection) new URL(link).openConnection();
			con.setRequestProperty("X-API-Key", key);
			con.setRequestMethod(type);
			con.setDoOutput(true);
			con.setDoInput(true);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return new JSONObject(response.toString());
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, Set<String>> createMap(JSONObject r) {
		resultMap = new TreeMap<String, Set<String>>();

		JSONArray resul;
		try {
			resul = new JSONArray(r.get("results").toString());
			JSONArray bills = new JSONArray(resul.getJSONObject(0).get("bills").toString());

			for (int i = 0; i < bills.length(); i++) {
				JSONObject bill = bills.getJSONObject(i);
				String[] billID = bill.get("bill_id").toString().split("-");
				resultMap.put(billID[0], getSponsors(billID));

				JSONObject output = getRelated(billID);
				JSONArray resul2 = new JSONArray(output.get("results").toString());
				JSONObject result = new JSONObject(resul2.get(0).toString());
				JSONArray relatedBills = new JSONArray(result.get("related_bills").toString());
				for (int j = 0; j < relatedBills.length(); j++) {
					JSONObject relatedBill = relatedBills.getJSONObject(j);
					String[] relatedBillID = relatedBill.get("bill_id").toString().split("-");
					resultMap.put(relatedBillID[0], getSponsors(relatedBillID));
				}
			}
			return resultMap;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	private Set<String> getSponsors(String[] billID) {
		Set<String> listSpons = new HashSet<String>();
		String newLink = "https://api.propublica.org/congress/v1/" + billID[1] + "/bills/" + billID[0]
				+ "/cosponsors.json";
		JSONObject obj = getObject(newLink);

		try {

			JSONArray results = new JSONArray(obj.get("results").toString());
			JSONObject cosponsors = new JSONObject(results.get(0).toString());
			String sponsor = cosponsors.getString("sponsor_name");
			JSONArray arr = new JSONArray(cosponsors.get("cosponsors").toString());

			listSpons.add(sponsor);

			if (arr.length() > 0) {
				for (int i = 0; i < arr.length(); i++) {
					listSpons.add(arr.getJSONObject(i).getString("name"));
				}
			}

			return listSpons;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	private JSONObject getRelated(String[] billID) {
		String newLink = "https://api.propublica.org/congress/v1/" + billID[1] + "/bills/" + billID[0]
				+ "/related.json";
		JSONObject obj = getObject(newLink);
		return obj;
	}
}

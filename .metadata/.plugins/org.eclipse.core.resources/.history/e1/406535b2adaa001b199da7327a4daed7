import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class RecM {

	public static void main(String[] args) {
		//authentication and HTTP request
		
		String link = "https://api.propublica.org/congress/v1/bills/search.json?query=";
		String key = "oy8bhWLG92Y9880Vxs2wK5nBjAkgDyWbbDhgl5wX";
		
		Scanner sc = new Scanner(System.in);
		
		//information to send
		
		System.out.println("What is your query? : ");
		
		String query = sc.nextLine();
		
		System.out.println("Who is your senator? : ");
		
		String sen = sc.nextLine();
		
		sc.close();
		
		//client to get data
		
		RequestClient r = new RequestClient(link, key, "GET");
		JSONObject obj  = r.getObject(query);

		try {
			System.out.println("Indented Output : " + obj.toString(2));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}

import java.util.Map;
import java.util.Scanner;
import java.util.Set;
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
		
		RequestClient r = new RequestClient(key, "GET");
		JSONObject obj  = r.getObject(link + query);
		
		Map<String, Set<String>> resultMap = r.createMap(obj);
		
		System.out.println(resultMap.toString());
		System.out.println(resultMap.size());
		
	}

}

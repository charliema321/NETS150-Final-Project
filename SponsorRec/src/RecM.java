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
		
		System.out.println("What is the Minimum Number of Bills That This Senator Must Sponser? : ");
		
		int num = sc.nextInt();
		
		sc.close();
		
		//client to get data
		
		RequestClient r = new RequestClient(key, "GET");
		JSONObject obj  = r.getObject(link + query);
		
		Map<String, Set<String>> resultMap = r.createMap(obj);
		
		Recommendation rec = new Recommendation(resultMap);
		
		if(rec.enoughSupport(num, sen)) {
			Set<String> output = rec.mostCommonSenator();
			output.remove(sen);
			System.out.println("Senator Recomendations:");
			System.out.println(rec.tooMuchInCommon(sen, output));
		} else {
			System.out.println(sen + " does not sponser enough bills in this query. "
					+ "Either choose a new senator or lower the minimum number of bills needed"
					+ " to be sponsered");
		}
	}

}

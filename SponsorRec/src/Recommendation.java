import java.util.*;
import java.util.Map.Entry;

public class Recommendation {
	
	Map<String, Set<String>> resultMap;
	
	public Recommendation(Map<String, Set<String>> resultMap) {
		this.resultMap = resultMap;
	}
	
	//Finds the Senator(s) Who Sponsors the Most Bills
	public Set<String> mostCommonSenator() {
		
		Set<String> senators = new HashSet<>();
		
		Map<String, Integer> freqMap = new HashMap<>();
		
		//Determine the Frequency of Senators
		for(Set<String> set : resultMap.values()) {
			Iterator<String> iter = set.iterator();
			while(iter.hasNext()){
				String str = iter.next(); 
				Integer count = freqMap.get(str);
				if(count == null) {
					count = new Integer(0);
				}
				count++;
				freqMap.put(str, count);
			}
		}
		
		//Determine Most Frequent Senator
		int maxValue = (Collections.max(freqMap.values()));
		for(Entry<String, Integer> entry : freqMap.entrySet()) {
			if (entry.getValue() == maxValue) {
                senators.add(entry.getKey());
            }
		}
		
		return senators; 
	} 
	
	//Determines if the Inputed Senator has Sponsored Enough Bills
	public boolean enoughSupport(int minAppearance, String inputSenator) {
		
		int count = 0;
		
		for(Set<String> set : resultMap.values()) {
			Iterator<String> iter = set.iterator();
			while(iter.hasNext()){
				String str = iter.next();
				if(str.equals(inputSenator)) {
					count++;
				}
			}
		}
		return count >= minAppearance;
	}
	
	//Removes Any Senator that cosponsors too Many Bills with the Input Senator
	public Set<String> tooMuchInCommon(String inputSenator, Set<String> senators) {
		
		Set<String> senatorsToRemove = new HashSet<String>();
		
		for(String str : senators) {
			int count = 0;
			for(Set<String> set : resultMap.values()) {
				if(set.contains(str) && set.contains(inputSenator)) {
					count++;
				}
			}
			if(count / resultMap.size() > 0.05) {
				senatorsToRemove.add(str);
			}
		}
		senators.removeAll(senatorsToRemove);
		return senators;
		
	}
}


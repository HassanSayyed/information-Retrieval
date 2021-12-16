import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class StringStopWordsMethods {
	
	/*	EMAILREGEX matches most of email
	 *  NOTWEIRDChars removes any possible weird character especially those generated by image-text AI
	 *  DOTENDING matches words ending with 1 or more .'s
	 */	
	private static final String EMAILREGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	private static final String NOTWEIRDCHARS = "[^a-zA-Z0-9₀-₉⁰-⁹⁺⁽⁾ @.#%^&*()><:?!]";
	private static final String DOTENDING = ".*(.)+";
	
	
	
	// Method that remove stopList words from sentence 
    public static String sentenceWithoutStopListWords(String sentence) {
    	

    	
    	if(sentence==null || sentence.isEmpty())
    		return "";
    	
    	Set<String> stopSet = STOPLIST.STOPLISTSET;
    	
    	StringBuilder sentenceBuilderWithoutStopSetWords = new StringBuilder();
    	for (String word : sentence.split("[\\n\\s+]")) {
    		if(!stopSet.contains(word.toLowerCase()))
    			sentenceBuilderWithoutStopSetWords.append(word.toLowerCase()+" ");
    	}
    	
    	String sentenceWithoutStopSetWords = !sentenceBuilderWithoutStopSetWords.isEmpty() ? sentenceBuilderWithoutStopSetWords.toString() : "";
    	sentenceWithoutStopSetWords.replaceAll(NOTWEIRDCHARS, "").trim();//remove all weird special characters
    	sentenceWithoutStopSetWords = sybolsStopWordsCheck(sentenceWithoutStopSetWords);
    	sentenceWithoutStopSetWords.replaceAll("\\s+", System.getProperty("line.separator")  )
                .trim();//fix first line spacing
        
        return sentenceWithoutStopSetWords;
    }
    
    private static String sybolsStopWordsCheck(String words) {
    	List<String> list = Arrays.asList(words.split("[\\n\\s+]"));
    	
    	
    	for (int i=0;i<list.size();i++) {
    		if(list.get(i).contains("@")) {
    			list.set(i, emailatStopWordVerification(list.get(i)));
    		}
    		if(list.get(i).contains(".")) {
    			list.set(i, dotStopWordVerification(list.get(i)));
    		}
    		if(list.get(i).contains(",")) {
    			list.set(i, commaStopWordVerification(list.get(i)));
    		}
    		
    	}
    	
    	StringBuilder stringBuilder = new StringBuilder();
    	
    	for(String word: list) {
    		stringBuilder.append(word);
    		stringBuilder.append(" ");
    	}
    	
    	
    	
    	return !stringBuilder.isEmpty() ? stringBuilder.toString() : "";
    }
    
    
    
    private static String emailatStopWordVerification(String word) {
    
    	return Pattern.matches(EMAILREGEX, word) ? word : ""; 
    
    }
    
    private static String dotStopWordVerification(String word) {
    	
    	if(Pattern.matches(DOTENDING, word)) {
    		while (!word.isEmpty() && word.endsWith(".")) {
    			word = word.substring(0, word.length()-1);
    		}
    	}
    	
    	
		return word;
    

     }
    
    private static String commaStopWordVerification(String word) {
    	
    	if(Pattern.matches(DOTENDING, word)) {
    		while (!word.isEmpty() && word.endsWith(",")) {
    			word = word.substring(0, word.length()-1);
    		}
    	}
    	
    	
		return word;
    

     }
    
    public static int stringCountInLinkedList(String word,LinkedList<String> linkedList) {
    	int count = 0;
    	
    	for (String s: linkedList) {
    		if(s == word) count++;
    	}
    	
		return count;
	}
}
	



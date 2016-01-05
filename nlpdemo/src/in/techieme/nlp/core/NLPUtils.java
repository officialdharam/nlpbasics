package in.techieme.nlp.core;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class NLPUtils {
	public static final String REGEX_SPLCHARS = new String("[~`!@#$%^&*()\\-=_+\\[\\]{};':\"<>,./?|]");

	public static JsonNode readJSONFromRequest(HttpServletRequest req) {
		String line = null;
		StringBuilder jb = new StringBuilder();
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			System.out.println("Error occurred while fetching JSON from request.");
		}

		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = null;
		try {
			actualObj = mapper.readTree(jb.toString());

		} catch (Exception e) {
			System.out.println("Exception occurred while creating JSON Node.");
		}

		return actualObj;
	}
}

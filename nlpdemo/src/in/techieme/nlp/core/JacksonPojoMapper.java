package in.techieme.nlp.core;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class JacksonPojoMapper {

	private static ObjectMapper m = new ObjectMapper();
	private static JsonFactory jf = new JsonFactory();

	public static <T> T fromJson(String jsonAsString, Class<T> pojoClass)
			throws JsonMappingException, JsonParseException, IOException {
		return m.readValue(jsonAsString, pojoClass);
	}

	public static <T> T fromJsonWithTypeReference(String jsonAsString,
			TypeReference<T> typeRef) throws JsonParseException,
			JsonMappingException, IOException {
		return m.readValue(jsonAsString, typeRef);
	}

	public static <T> T fromJson(FileReader fr, Class<T> pojoClass)
			throws JsonParseException, IOException {
		return m.readValue(fr, pojoClass);
	}

	public static String toJson(Object pojo, boolean prettyPrint)
			throws JsonMappingException, JsonGenerationException, IOException {
		StringWriter sw = new StringWriter();
		JsonGenerator jg = jf.createJsonGenerator(sw);
		if (prettyPrint) {
			jg.useDefaultPrettyPrinter();
		}
		m.writeValue(jg, pojo);
		return sw.toString();
	}

	public static void toJson(Object pojo, FileWriter fw, boolean prettyPrint)
			throws JsonMappingException, JsonGenerationException, IOException {
		JsonGenerator jg = jf.createJsonGenerator(fw);
		if (prettyPrint) {
			jg.useDefaultPrettyPrinter();
		}
		m.writeValue(jg, pojo);
	}
}

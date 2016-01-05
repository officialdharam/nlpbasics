package in.techieme.servlets;

import in.techieme.nlp.core.JacksonPojoMapper;
import in.techieme.nlp.core.NLPUtils;
import in.techieme.nlp.lm.spellings.ErrorDetection;
import in.techieme.servlets.response.Response;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;

public class AddToDictionaryServlet extends HttpServlet {

	private static final long serialVersionUID = 7734303810548998075L;
	
	private ErrorDetection ED = new ErrorDetection();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JsonNode jsonNode = NLPUtils.readJSONFromRequest(req);
		String wordToAdd = jsonNode.get("wordToAdd").asText();
		ED.addWordToDictionary(wordToAdd);
		Response results = new Response(Response.SUCCESS, "Added successfully");
		String json = JacksonPojoMapper.toJson(results, false);
		resp.getWriter().write(json);
	}

}

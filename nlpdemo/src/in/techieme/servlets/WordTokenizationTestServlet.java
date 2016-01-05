package in.techieme.servlets;

import in.techieme.nlp.core.JacksonPojoMapper;
import in.techieme.nlp.core.NLPUtils;
import in.techieme.nlp.textprocessing.core.WordTokenizer;
import in.techieme.servlets.response.Response;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.JsonNode;

public class WordTokenizationTestServlet extends HttpServlet {

	private static final long serialVersionUID = 801598528481567210L;
	private WordTokenizer WT = new WordTokenizer();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		JsonNode jsonNode = NLPUtils.readJSONFromRequest(req);
		String stringToTokenize = jsonNode.get("textToTokenize").asText();
		String tokenizationMethod = jsonNode.get("tokenizationMethod").asText();
		String[] tokens = null;
		if (tokenizationMethod != null && "splchars".equals(tokenizationMethod)) {
			tokens = WT.tokenizeByNormalizingAllSplChars(stringToTokenize);
		} else {
			tokens = WT.tokenizeBySpace(stringToTokenize);
		}

		Response results = new Response(Response.SUCCESS, tokens);
		String json = JacksonPojoMapper.toJson(results, false);
		resp.getWriter().write(json);
	}

}

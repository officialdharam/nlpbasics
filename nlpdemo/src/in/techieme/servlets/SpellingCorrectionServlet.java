package in.techieme.servlets;

import in.techieme.nlp.core.JacksonPojoMapper;
import in.techieme.nlp.core.NLPUtils;
import in.techieme.nlp.core.SpellErrors;
import in.techieme.nlp.lm.spellings.ErrorCorrection;
import in.techieme.nlp.lm.spellings.ErrorDetection;
import in.techieme.servlets.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;

public class SpellingCorrectionServlet extends HttpServlet {

	private static final long serialVersionUID = 5871347836989142135L;
	private ErrorCorrection EC = new ErrorCorrection();
	private ErrorDetection ED = new ErrorDetection();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JsonNode jsonNode = NLPUtils.readJSONFromRequest(req);
		String docToCorrect = jsonNode.get("docToCorrect").asText();
		Map<String, int[]> nonSpellingErrors = ED.nonSpellingErrors(docToCorrect);
		List<SpellErrors> spellErrors = new ArrayList<SpellErrors>();

		for (Entry<String, int[]> e : nonSpellingErrors.entrySet()) {
			String errorStr = e.getKey() + "(" + e.getValue()[0] + ", " + e.getValue()[1] + ")";
			Set<String> suggestions = EC.fetchSuggestions(e.getKey());
			String[] corrections = suggestions.toArray(new String[0]);
			SpellErrors spelError = new SpellErrors(errorStr, corrections);
			spellErrors.add(spelError);

		}
		Response results = new Response(Response.SUCCESS, spellErrors);
		String json = JacksonPojoMapper.toJson(results, false);
		resp.getWriter().write(json);
	}
}

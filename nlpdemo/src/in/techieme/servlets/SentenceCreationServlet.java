package in.techieme.servlets;

import in.techieme.nlp.core.JacksonPojoMapper;
import in.techieme.nlp.core.NLPUtils;
import in.techieme.nlp.sentencecreation.core.SentenceCreationClient;
import in.techieme.servlets.response.Response;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;

public class SentenceCreationServlet extends HttpServlet {

	private static final long serialVersionUID = -940524230826249573L;

	private SentenceCreationClient SC = new SentenceCreationClient();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[][] sentence = new String[3][2];

		sentence[0][0] = "UNIGRAM";
		sentence[0][1] = SC.randomSentenceUnigrams();

		sentence[1][0] = "BIGRAM";
		sentence[1][1] = SC.randomSentenceBigrams();

		sentence[2][0] = "TRIGRAM";
		sentence[2][1] = SC.randomSentenceTrigrams();

		Response results = new Response(Response.SUCCESS, sentence);
		String json = JacksonPojoMapper.toJson(results, false);
		resp.getWriter().write(json);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JsonNode jsonNode = NLPUtils.readJSONFromRequest(req);
		String words = jsonNode.get("words").asText();

		String[][] sentence = new String[3][2];
		sentence[0][0] = "UNIGRAM";
		sentence[0][1] = SC.sentenceFromPhraseUnigrams(words);
		
		sentence[1][0] = "BIGRAM";
		sentence[1][1] = SC.sentenceFromPhraseBigrams(words);
		
		Response results = new Response(Response.SUCCESS, sentence);
		String json = JacksonPojoMapper.toJson(results, false);
		resp.getWriter().write(json);

	}

}

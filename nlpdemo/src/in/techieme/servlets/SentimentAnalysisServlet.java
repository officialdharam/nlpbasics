package in.techieme.servlets;

import in.techieme.nlp.core.JacksonPojoMapper;
import in.techieme.nlp.core.NLPUtils;
import in.techieme.nlp.sentimentanalysis.AsianNewsHeadlineClassification;
import in.techieme.nlp.sentimentanalysis.MovieReviewClassification;
import in.techieme.servlets.response.Response;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;

public class SentimentAnalysisServlet extends HttpServlet {

	private static final long serialVersionUID = 6887158856090098071L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JsonNode jsonNode = NLPUtils.readJSONFromRequest(req);
		String review = jsonNode.get("review").asText();
		String type = jsonNode.get("type").asText();
		String[] result = null;
		if ("movie".equals(type)) {
			result = MovieReviewClassification.test(review);
		} else if ("news".equals(type)) {
			result = AsianNewsHeadlineClassification.test(review);
		} else {

		}

		Response results = new Response(Response.SUCCESS, result);
		String json = JacksonPojoMapper.toJson(results, false);
		resp.getWriter().write(json);
	}

}

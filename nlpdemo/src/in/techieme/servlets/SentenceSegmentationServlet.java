package in.techieme.servlets;

import in.techieme.nlp.core.JacksonPojoMapper;
import in.techieme.nlp.core.NLPUtils;
import in.techieme.nlp.segmentation.core.DecisionTreeSentSeg;
import in.techieme.nlp.segmentation.core.EOSSentSeg;
import in.techieme.nlp.segmentation.core.SentSegment;
import in.techieme.servlets.response.Response;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;

public class SentenceSegmentationServlet extends HttpServlet {

	private static final long serialVersionUID = -2667338789110395393L;

	private SentSegment segmEOS = new EOSSentSeg();
	private SentSegment segmDT = new DecisionTreeSentSeg();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JsonNode jsonNode = NLPUtils.readJSONFromRequest(req);
		String docToSegment = jsonNode.get("docToSegment").asText();
		String segmentationMethod = jsonNode.get("segmentationMethod").asText();
		String[] sentences = null;
		if (segmentationMethod != null && "decisionTree".equals(segmentationMethod)) {
			sentences = segmDT.sentSegment(docToSegment);
		} else {
			sentences = segmEOS.sentSegment(docToSegment);
		}

		Response results = new Response(Response.SUCCESS, sentences);
		String json = JacksonPojoMapper.toJson(results, false);
		resp.getWriter().write(json);
	}

}

package in.techieme.nlp.lm.core;

public class TestSentenceCompletion {

	public static void main(String[] args) {
		UnigramModel model = new UnigramModel();
		model.train();
		int sentenceLength = 20;
		StringBuilder sb = new StringBuilder("<s> ");
		for (int i = 1; i < sentenceLength - 1; i++) {
			sb.append(model.predictNextWord("")).append(" ");
		}
		sb.append("</s>");
		System.out.println(sb);
		
		BiGramModel bModel = new BiGramModel();
		bModel.train();
		StringBuilder sbBGram = new StringBuilder("<s> ");
		for (int i = 1; i < sentenceLength - 1; i++) {
			String nextToken = bModel.predictNextWord(sbBGram.toString());
			if(Model.END_SENT.equals(nextToken)){
				sbBGram.append(nextToken).append(" ");
				sbBGram.append(Model.BEGIN_SENT).append(" ");
			}else{
				sbBGram.append(nextToken).append(" ");
			}
		}
		sbBGram.append("</s>");
		System.out.println(sbBGram);
		
		TriGramModel tModel = new TriGramModel();
		tModel.train();
		StringBuilder sbTGram = new StringBuilder("<s> i ");
		for (int i = 1; i < sentenceLength - 1; i++) {
			String nextToken = tModel.predictNextWord(sbTGram.toString());
			if(Model.END_SENT.equals(nextToken)){
				sbTGram.append(nextToken).append(" ");
				sbTGram.append(Model.BEGIN_SENT).append(" ");
				sbTGram.append("i ");
			}else{
				sbTGram.append(nextToken).append(" ");
			}
		}
		System.out.println(sbTGram);
	}

}

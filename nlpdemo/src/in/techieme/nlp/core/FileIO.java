package in.techieme.nlp.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIO {

	public static String readFile(String fileName) {
		BufferedReader br;
		StringBuilder sb = new StringBuilder();
		try {
			br = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.ISO_8859_1);
			for (String line = null; (line = br.readLine()) != null;) {
				sb.append(line);
				sb.append("\n");
				line = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}


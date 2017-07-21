package feature;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ins.Sentence;
import utils.NLPTools;


public class LexicalFeatureExtractor implements FeatureExtractorInterface {


	private List<String> stop_words;
	
	@Override
	public void extract(Sentence ins, List<Feature> features) {
		// TODO Auto-generated method stub
		
		List<String> segmented_comment = ins.getSegmented_content();
		HashSet<String> word_set = new HashSet<String>(segmented_comment);
		for (String word : word_set) 
		{
			if(!stop_words.contains(word))
			     features.add(new Feature(word, 1));
			//for tf feature
			//features.add(new Feature(word, Collections.frequency(segmented_comment,word)));
		}

		return;
	}
	
	
	public LexicalFeatureExtractor()
	{
		stop_words = new ArrayList<String>();
		
		try {
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(new FileInputStream(
							"stopwords.txt"), "UTF-8"), 512);
			String line = "";
			while ((line = buffer.readLine()) != null) {
				stop_words.add(line);
	//			System.out.println(line);
			}
			buffer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
} // end class LexicalFeatureExtractor

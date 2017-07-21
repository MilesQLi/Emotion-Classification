package feature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ins.Sentence;
import utils.NLPTools;
import edu.stanford.nlp.ling.TaggedWord;

public class BigramFeatureExtractor  implements FeatureExtractorInterface{
	
	
	@Override
	public void extract(Sentence ins, List<Feature> features) {
		// TODO Auto-generated method stub
		List<String> segmented_content = ins.getSegmented_content();
	//	List<TaggedWord> poses = ins.getPoses();
		
		for(int i = 0; i < segmented_content.size() - 1; i++)
		{
			features.add(new Feature(segmented_content.get(i) + "_" + segmented_content.get(i + 1), 1));
		}
		/*
		for (int i = 0; i < poses.size() - 1; ++i) {
			features.add(new Feature(poses.get(i).tag() + "_" + poses.get(i + 1).tag(), 1));
		}
		*/
		return;
	}
	
}

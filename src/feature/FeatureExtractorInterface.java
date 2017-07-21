package feature;

import java.util.List;

import ins.Sentence;

public interface FeatureExtractorInterface {
	
	/**
	 * 
	 * @param ins
	 * @return a list of certain type of features
	 */
	public void extract(Sentence ins, List<Feature> features);

}

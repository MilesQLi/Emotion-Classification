package feature;

import java.util.List;

import ins.Sentence;

public class LengthFeatureExtractor implements FeatureExtractorInterface {

	@Override
	public void extract(Sentence ins, List<Feature> features) {
		// TODO Auto-generated method stub		
//		a simple example
		features.add(new Feature("COM_LEN", ins.getContent().length()));
		return;
	}

} // end class LengthFeatureExtractor

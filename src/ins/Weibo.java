package ins;

import java.util.ArrayList;
import java.util.List;

import feature.FeatureMap;
import edu.stanford.nlp.ling.TaggedWord;
import feature.Feature;
import feature.FeatureExtractor;
import types.EmotionType;
import types.OpinionatedType;
import utils.NLPTools;

public class Weibo{
	private String id;
	private OpinionatedType opinionated;
	private EmotionType emotion_1_type;
	private EmotionType emotion_2_type;
	private ArrayList<Sentence> sentences;
	private List<Feature> features;
	private String content;
	private List<String> segmented_content;
//	private List<TaggedWord> poses;
	
	
	
	public Weibo(String id, String opinionated, String emotion_1_type,
			String emotion_2_type, ArrayList<Sentence> sentences) {
		super();
		this.features = new ArrayList<Feature>();
		this.id = id;
		this.opinionated = OpinionatedType.getType(opinionated);
		this.emotion_1_type = EmotionType.getType(emotion_1_type);
		this.emotion_2_type = EmotionType.getType(emotion_2_type);
		this.sentences = sentences;
		this.content = "";
		for(Sentence sentence: sentences)
		{
			this.content += sentence.getContent();
		}
		this.segmented_content = NLPTools.segment(this.content);
//		this.poses = NLPTools.postag(this.segmented_content);
		
	}


	@Override
	public String toString() {
		String result;
		
		result = id + " "+ this.opinionated.toString() + " " + this.emotion_1_type.toString() + " " + this.emotion_2_type.toString();
		
		for (Feature f : this.features)
			if(FeatureMap.getFeatureFrequency(f) > 9)
			{
			  result += " " + f;
			}
		return result;
		/*
		return "Weibo [id=" + id + ", opinionated=" + opinionated
				+ ", emotion_1_type=" + emotion_1_type + ", emotion_2_type="
				+ emotion_2_type + ", sentences=" + sentences + "]";
				*/
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public OpinionatedType getOpinionated() {
		return opinionated;
	}


	public void setOpinionated(OpinionatedType opinionated) {
		this.opinionated = opinionated;
	}


	public EmotionType getEmotion_1_type() {
		return emotion_1_type;
	}


	public void setEmotion_1_type(EmotionType emotion_1_type) {
		this.emotion_1_type = emotion_1_type;
	}


	public EmotionType getEmotion_2_type() {
		return emotion_2_type;
	}


	public void setEmotion_2_type(EmotionType emotion_2_type) {
		this.emotion_2_type = emotion_2_type;
	}


	public ArrayList<Sentence> getSentences() {
		return sentences;
	}


	public void setSentences(ArrayList<Sentence> sentences) {
		this.sentences = sentences;
	}


	public List<Feature> getFeatures() {
		return features;
	}


	public void setFeatures(List<Feature> features) {
		this.features = features;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public List<String> getSegmented_content() {
		return segmented_content;
	}


	public void setSegmented_content(List<String> segmented_content) {
		this.segmented_content = segmented_content;
	}

	/*
	public List<TaggedWord> getPoses() {
		return poses;
	}


	public void setPoses(List<TaggedWord> poses) {
		this.poses = poses;
	}

*/
	public void setFeatures()
	{
		for(Sentence sentence: sentences)
		{
			sentence.setFeatures(FeatureExtractor.extract(sentence));
			this.features.addAll(sentence.getFeatures());
		}
	//	this.setFeatures(FeatureExtractor.extract(this));
		
	}
	
}

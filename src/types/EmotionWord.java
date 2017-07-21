package types;

import java.util.ArrayList;

public class EmotionWord {

	private String name;
	private ArrayList<EmotionType> type;
	private ArrayList<Integer> intensity;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public ArrayList<EmotionType> getType() {
		return type;
	}
	public void setType(ArrayList<EmotionType> type) {
		this.type = type;
	}
	public ArrayList<Integer> getIntensity() {
		return intensity;
	}
	public void setIntensity(ArrayList<Integer> intensity) {
		this.intensity = intensity;
	}
	public void addEmotion(EmotionType type, int intensity)
	{
		this.type.add(type);
		this.intensity.add(intensity);
	}
	
	public EmotionWord(String name, EmotionType type, int intensity) {
		super();
		this.name = name;
		this.type = new ArrayList<EmotionType>();
		this.intensity = new ArrayList<Integer>();
		this.type.add(type);
		this.intensity.add(intensity);
	}
	
}

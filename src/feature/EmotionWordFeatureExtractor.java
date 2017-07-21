package feature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import ins.Sentence;
import types.EmotionType;
import types.EmotionWord;
import utils.NLPTools;

public class EmotionWordFeatureExtractor implements FeatureExtractorInterface {

	private HashMap<String, EmotionWord> word_map;
	private HashMap<String, EmotionType> emotion_map;
	
	@Override
	public void extract(Sentence ins, List<Feature> features) {
		// TODO Auto-generated method stub
		
		List<String> segmented_content = ins.getSegmented_content();
		int emotions[] = {0,0,0,0,0,0,0,0};
		for (String word : segmented_content) {
			if(word_map.containsKey(word))
			{
				EmotionWord temp_word = word_map.get(word);
				ArrayList<EmotionType> types = temp_word.getType();
				ArrayList<Integer> intensity = temp_word.getIntensity();
				for(int i = 0; i < types.size(); i++)
				{
					emotions[types.get(i).getnCode()] += intensity.get(i);
					System.out.println(word + " " + types.get(i).name() + " " + intensity.get(i));
				}
				
			}
		}
		
		EmotionType[] allTypes = EmotionType.values();
		
		for(EmotionType type: allTypes)
		{
			if(type.getnCode() == 0)
				continue;
			features.add(new Feature(type.name(), emotions[type.getnCode()]));
		}
		
		return;
	}

	public EmotionWordFeatureExtractor() {
		
		word_map = new HashMap<String, EmotionWord>();
		emotion_map = new HashMap<String, EmotionType>();
		
		emotion_map.put("NA", EmotionType.ANGER);
		emotion_map.put("NI", EmotionType.FEAR);
		emotion_map.put("NC", EmotionType.FEAR);
		emotion_map.put("NG", EmotionType.FEAR);
		emotion_map.put("NE", EmotionType.DISGUST);
		emotion_map.put("ND", EmotionType.DISGUST);
		emotion_map.put("NN", EmotionType.DISGUST);
		emotion_map.put("NK", EmotionType.DISGUST);
		emotion_map.put("NL", EmotionType.DISGUST);
		emotion_map.put("PA", EmotionType.HAPPINESS);
		emotion_map.put("PE", EmotionType.HAPPINESS);
		emotion_map.put("PD", EmotionType.LIKE);
		emotion_map.put("PH", EmotionType.LIKE);
		emotion_map.put("PG", EmotionType.LIKE);
		emotion_map.put("PB", EmotionType.LIKE);
		emotion_map.put("PK", EmotionType.LIKE);
		emotion_map.put("NB", EmotionType.SADNESS);
		emotion_map.put("NJ", EmotionType.SADNESS);
		emotion_map.put("NH", EmotionType.SADNESS);
		emotion_map.put("PF", EmotionType.SADNESS);
		emotion_map.put("PC", EmotionType.SURPRISE);

		String line = "";
		try {
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(new FileInputStream(
							"emotionreality.txt"), "UTF-8"), 512);
			while ((line = buffer.readLine()) != null) {
				String[] content = line.split("\t");
				for(int i = 1; i < content.length; i += 2)
				{
				//	System.out.println(content[0]+" "+content[i]+" "+content[i+1]);
					if(i == 1)
					{
						word_map.put(content[0], new EmotionWord(content[0], emotion_map.get(content[i]), Integer.parseInt(content[i+1])));
					}
					else
					{
						EmotionWord word = word_map.get(content[0]);
						word.addEmotion(emotion_map.get(content[i]), Integer.parseInt(content[i+1]));
					}
				}
			}
			buffer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(line);
		}
	}

}

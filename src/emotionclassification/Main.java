package emotionclassification;

import ins.Weibo;
import ins.Sentence;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import feature.FeatureExtractor;
import feature.FeatureMap;
import utils.NLPTools;
import types.EmotionType;
import types.OpinionatedType;
import utils.XMLUtils;




public class Main {

	public static void main(String[] args) throws Exception {
		
		initialize();
		
		
		
		List<Weibo> trainingWeibos = XMLUtils.readTrainWeiboXML(new File("data/Training data for Emotion Classification.xml"));
		int i = 1;
		for (Weibo ins : trainingWeibos) {			
			ins.setFeatures();
			System.out.println("train feature:" + i++);
		}
		
		FileWriter writer;
		writer = new FileWriter(new File("weibo_train_xy.txt"));
		for (Weibo ins : trainingWeibos) {
			writer.write(ins + "\n");			
		}
		writer.flush();
		writer.close();
		
		writer = new FileWriter(new File("weibo_train_sentence_xy.txt"));
		for (Weibo ins : trainingWeibos) {
			for(Sentence sent : ins.getSentences())
			      writer.write(ins + "\n");			
		}
		writer.flush();
		writer.close();
		
		
		
		List<Weibo> testWeibos = XMLUtils.readTrainWeiboXML(new File("data/Tesing data for Emotion Classification.xml"));
		i = 1;
		for (Weibo ins : testWeibos) {			
			ins.setFeatures();
			System.out.println("test feature:" + i++);
		}
		
		writer = new FileWriter(new File("weibo_test_xy.txt"));
		for (Weibo ins : testWeibos) {
			writer.write(ins + "\n");			
		}
		writer.flush();
		writer.close();
		
		writer = new FileWriter(new File("weibo_test_sentence_xy.txt"));
		for (Weibo ins : testWeibos) {
			for(Sentence sent : ins.getSentences())
			      writer.write(ins + "\n");			
		}
		writer.flush();
		writer.close();
		
		
		
		writer = new FileWriter(new File("features.txt"));
		HashMap<String, Integer> map = FeatureMap.getMap();
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    writer.write((String)entry.getKey() + " " + entry.getValue() + "\n");	
		} 
		
		writer.flush();
		writer.close();

	}


	static private void initialize()
	{
		EmotionType.setup();
		OpinionatedType.setup();
		try {
			NLPTools.initalize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}

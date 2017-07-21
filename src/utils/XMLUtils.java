package utils;

import ins.Weibo;
import ins.Sentence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLUtils {
	
	private static DocumentBuilderFactory dbf;
    private static DocumentBuilder db;
    private static Document doc;
    private static Element root;

    /**
     * 
     * @param file
     * @return list of movies
     */
	public static List<Weibo> readTrainWeiboXML(File file) {
		if (!file.exists()) return null;
        readXMLAux(file);
        NodeList nodeList = root.getElementsByTagName("weibo");
        ArrayList<Weibo> result = new ArrayList<Weibo>();        
        for (int i = 0; i < nodeList.getLength(); ++i) {
        	System.out.println("read train xml:"+i);
        	Element element = (Element) nodeList.item(i);
        	String id = element.getAttributes().getNamedItem("id").getNodeValue();
        	String emotion1 = element.getAttributes().getNamedItem("emotion-type1").getNodeValue();
        	String emotion2 = element.getAttributes().getNamedItem("emotion-type2").getNodeValue();
        	String opinionated;
        	if(emotion1.equals("none"))
        	{
        		opinionated = "N";
        	}else
        	{
        		opinionated = "Y";
        	}
        	NodeList sentencesList = element.getElementsByTagName("sentence");
        	ArrayList<Sentence> sentences = new ArrayList<Sentence>();  
        	for(int j = 0; j < sentencesList.getLength(); ++j)
        	{
        		Element sentenceElement = (Element) sentencesList.item(j);
        		String content = sentenceElement.getTextContent();
        		String sentenceId = sentenceElement.getAttributes().getNamedItem("id").getNodeValue();
        		String sentenceOpinionated = sentenceElement.getAttributes().getNamedItem("opinionated").getNodeValue();
        		String emotion_1_type;
        		String emotion_2_type;
        		if(sentenceOpinionated.equals("Y"))
        		{
        			emotion_1_type = sentenceElement.getAttributes().getNamedItem("emotion-1-type").getNodeValue();
        			emotion_2_type = sentenceElement.getAttributes().getNamedItem("emotion-2-type").getNodeValue();
        		}
        		else
        		{
        			emotion_1_type = "none";
        			emotion_2_type = "none";
        		}
        		sentences.add(new Sentence(content, sentenceId, sentenceOpinionated, emotion_1_type, emotion_2_type));
        	}
        	result.add(new Weibo(id, opinionated, emotion1,emotion2, sentences));
        }
        return result;
    } // end method readWeiboXML
	
	
    /**
     * 
     * @param file
     * @return list of movies
     */
	public static List<Weibo> readTestWeiboXML(File file) {
		if (!file.exists()) return null;
        readXMLAux(file);
        NodeList nodeList = root.getElementsByTagName("weibo");
        ArrayList<Weibo> result = new ArrayList<Weibo>();        
        for (int i = 0; i < nodeList.getLength(); ++i) {
        	System.out.println("read test xml:"+i);
        	Element element = (Element) nodeList.item(i);
        	String id = element.getAttributes().getNamedItem("id").getNodeValue();
        	String emotion1 = "none";
        	String emotion2 = "none";
        	String opinionated = "N";
        	NodeList sentencesList = element.getElementsByTagName("sentence");
        	ArrayList<Sentence> sentences = new ArrayList<Sentence>();  
        	for(int j = 0; j < sentencesList.getLength(); ++j)
        	{
        		Element sentenceElement = (Element) sentencesList.item(j);
        		String content = sentenceElement.getTextContent();
        		String sentenceId = sentenceElement.getAttributes().getNamedItem("id").getNodeValue();
        		String sentenceOpinionated = "none";
        		String emotion_1_type = "none";
        		String emotion_2_type = "none";
        		sentences.add(new Sentence(content, sentenceId, sentenceOpinionated, emotion_1_type, emotion_2_type));
        	}
        	result.add(new Weibo(id, opinionated, emotion1,emotion2, sentences));
        }
        return result;
    } // end method readWeiboXML
	
	
	
    private static void readXMLAux(File file) {
        dbf = DocumentBuilderFactory.newInstance();
        db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();            
        }
        doc = null;
        try {
            doc = db.parse(file);
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();            
        }
        root = doc.getDocumentElement();  
    }  // End method readXML()
    
} // end class XMLUtils

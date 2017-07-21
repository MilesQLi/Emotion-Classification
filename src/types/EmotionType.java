package types;

import java.util.HashMap;

public enum EmotionType {
NONE(0), ANGER(1), DISGUST(2), FEAR(3), HAPPINESS(4), LIKE(5), SADNESS(6), SURPRISE(7);
private int nCode;

static private HashMap<String, EmotionType> currEnumMap = new HashMap<String, EmotionType>();


private EmotionType(int _nCode) {
    this.nCode = _nCode;
}

static public void setup()
{
	currEnumMap.put("none", EmotionType.NONE);
	currEnumMap.put("anger", EmotionType.ANGER);
	currEnumMap.put("disgust", EmotionType.DISGUST);
	currEnumMap.put("fear", EmotionType.FEAR);
	currEnumMap.put("happiness", EmotionType.HAPPINESS);
	currEnumMap.put("like", EmotionType.LIKE);
	currEnumMap.put("sadness", EmotionType.SADNESS);
	currEnumMap.put("surprise", EmotionType.SURPRISE);
}

@Override
public String toString() {

    return String.valueOf(this.nCode);

}



public int getnCode() {
	return nCode;
}

public void setnCode(int nCode) {
	this.nCode = nCode;
}

static public EmotionType getType(String name)
{
	return currEnumMap.get(name);
}


}

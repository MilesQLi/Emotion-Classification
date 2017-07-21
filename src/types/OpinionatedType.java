package types;

import java.util.HashMap;

public enum OpinionatedType {
NO(0),YES(1);
private int nCode;

static private HashMap<String, OpinionatedType> currEnumMap = new HashMap<String, OpinionatedType>();


private OpinionatedType(int _nCode) {
    this.nCode = _nCode;
}

static public void setup()
{
	currEnumMap.put("N", OpinionatedType.NO);
	currEnumMap.put("Y", OpinionatedType.YES);
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

static public OpinionatedType getType(String name)
{
	return currEnumMap.get(name);
}


}

package feature;

/**
 * 
 * @author intfloat@pku.edu.cn, stormier@126.com
 *
 */
public class Feature {
	
//	String description for a feature
	private String desc;
	private double value;
	private int index;
	
	/**
	 * 
	 * @param desc
	 * 
	 * suitable for categorical features such as bag of words,
	 * default value is 1.0
	 */
	public Feature(String desc) {
		this.setDesc(desc);
		this.setValue(1.0);
		this.index = FeatureMap.getIndex(this);
	}
	
	/**
	 * 
	 * @param desc
	 * @param value
	 * 
	 * suitable for numerical features such as length.
	 */
	public Feature(String desc, double value) {
		this.setDesc(desc);
		this.setValue(value);
		this.index = FeatureMap.getIndex(this);		
	}
	
	@Override
	public String toString() {
//		<description, value> pair
		return this.desc + ":" + this.value;
//		return this.index + ":" + this.value;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
//		avoid string splitting ambiguity issue
		desc = desc.replace(' ', '_');
		desc = desc.replace('\t', '_');
		this.desc = desc;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

} // end class Feature

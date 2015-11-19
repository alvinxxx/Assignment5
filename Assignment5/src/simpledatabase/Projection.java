package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;
	
	private Tuple tuple;
	private int i=0;
	private String name;
	private Type type;
	private Object value;
	Attribute attribute;
	
	public Projection(Operator child, String attributePredicate){
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
		
	}
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
		tuple = child.next();
		
		if(tuple == null){
			return null;
		}		
		
		// find index of attribute name		
		for(i=0;i<tuple.getAttributeList().size();i++){
			name = tuple.getAttributeName(i);
			if(name.equals(attributePredicate)){
				break;
			}
		}		
		
		// keep only value of specific name
		type = tuple.getAttributeType(i);
		value = tuple.getAttributeValue(i);			
		
		newAttributeList = new ArrayList<Attribute>();
		attribute = new Attribute();	
		attribute.attributeName = name;
		attribute.attributeType = type;
		attribute.attributeValue = value;
		newAttributeList.add(attribute);
		
		tuple = new Tuple(newAttributeList);			
		return tuple;		
	}
		

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}
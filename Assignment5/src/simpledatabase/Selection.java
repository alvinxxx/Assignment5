package simpledatabase;
import java.util.ArrayList;

public class Selection extends Operator{
	
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;

	private Tuple tuple;
	private int i=0;
	private String name;
	private Object value;
	private boolean condition = false;
	private int iindex;
	
	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate, String whereValuePredicate) {
		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
		attributeList = new ArrayList<Attribute>();

	}
	
	
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next(){
		//call for tuple
		tuple = child.next();
		
		//return null if no more tuples
		if(tuple == null){
			return null;
		}		
		
		//find the index of name
		outer:
		while(tuple != null && i<tuple.getAttributeList().size()){			
			for(i=0;i<tuple.getAttributeList().size();i++){
				name = tuple.getAttributeName(i);

				if(name.equals(whereAttributePredicate)){
					iindex = i;
					condition = true;
					break outer;
				}
				else{
					condition = false;
				}
			}	
		}	
	
		//see if match
		while(tuple != null && condition == true){	
			value = tuple.getAttributeValue(iindex);		
			if(value.equals(whereValuePredicate)){
				return tuple;
			}
			else{
				tuple = child.next();
			}	
		}
		
		//return null if no more tuples after matching
		if(tuple == null){
			return null;
		}	
		
		// return the tuple if no selection for that table 
		return tuple;
			
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){	
		return(child.getAttributeList());
	}

	
}
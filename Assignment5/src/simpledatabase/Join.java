package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;

	private Tuple lefttuple;
	private Tuple righttuple;
	private Tuple tuple;
	
	private int i, j, k, l; 
	private String rightname, leftname;
	private Object rightvalue, leftvalue;
	private int iindex, kindex;
	Attribute attribute;
	
	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		//add all tuples in left table to an arraylist
		do{
			lefttuple = leftChild.next();
			if(lefttuple!= null){
				tuples1.add(lefttuple);		
			}
		}while(lefttuple != null);
		
		//get a tuple in right table
		righttuple = rightChild.next();		
		
		//return null if no more tuples in right table
		if(righttuple == null){
			return null;
		}
		
		//find the common attribute name in both tuples
		//iindex = index of right name, j = index of left name
		int size = righttuple.getAttributeList().size();

		outer:
		for(i=0;i<size;i++){			
			rightname = righttuple.getAttributeName(i);
			
			for(j=0;j<tuples1.size();j++){
				leftname = tuples1.get(0).getAttributeName(j);
				
				if(rightname.equals(leftname)){
					iindex = i;
					break outer;	
				}
			}	
		}	

		//find the same value of that common attribute name in left tuple
		//kindex = index of tuple that have attribute at j location 
		//		   as same as attribute at iindex location of every tuple 
		rightvalue = righttuple.getAttributeValue(iindex);
		
		for(k=0;k<tuples1.size();k++){
			leftvalue = tuples1.get(k).getAttributeValue(j);
			if(leftvalue.equals(rightvalue)){
				kindex = k;
				break;
			}
		}		

			
		//add the right tuple into Attribute List
		newAttributeList = new ArrayList<Attribute>();
		
		for(i=0;i<righttuple.getAttributeList().size();i++){
			attribute = new Attribute();	
			attribute.attributeName = righttuple.getAttributeName(i);
			attribute.attributeType = righttuple.getAttributeType(i);
			attribute.attributeValue = righttuple.getAttributeValue(i);
			newAttributeList.add(attribute);
		}

		
		//add attributes other than common attribute to the attributes list
		for(l=0;l<tuples1.size();l++){
			if(l!=j){
				attribute = new Attribute();	
				attribute.attributeName = tuples1.get(kindex).getAttributeName(l);
				attribute.attributeType = tuples1.get(kindex).getAttributeType(l);
				attribute.attributeValue = tuples1.get(kindex).getAttributeValue(l);
				newAttributeList.add(attribute);
			}
		}
		
		//return the merged tuple
		tuple = new Tuple(newAttributeList);
		return tuple;
	}
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}
package simpledatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;

	private Tuple tuple;
	private int k=0, l=0; 
	private int kindex;
	private String name;		
	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;				
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();		
	}
	
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		//store all tuples into arraylist
		do{
			tuple = child.next();
			if(tuple != null){
				tuplesResult.add(tuple);
			}
		}while(tuple != null);
		
		//search the index of order attribute
		for(k=0;k<tuplesResult.get(0).getAttributeList().size();k++){
			name = tuplesResult.get(0).getAttributeName(k);		
			if(name.equals(orderPredicate)){
				kindex = k;
				break;
			}
		}
			
		//sort the arraylist
		Collections.sort(tuplesResult, new Comparator<Tuple>() {
	        public int compare(Tuple  tuple1, Tuple  tuple2){    

	        	if(kindex == 2){
	        		return  ((Integer) tuple1.getAttributeValue(kindex)).compareTo((Integer)tuple2.getAttributeValue(kindex));
	        	}
	        	return  ((String) tuple1.getAttributeValue(kindex)).compareTo((String) tuple2.getAttributeValue(kindex));
	        }
	    });
		
		//get tuple from the sorted arraylist
		if(l<tuplesResult.size()){
			tuple = tuplesResult.get(l);
			l++;
		}
		//return null if no more tuple in arraylist
		else{
			return null;
		}
		
		//return the tuple
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
package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Table extends Operator{
	private BufferedReader br = null;
	private Tuple tuple;
	
	private Tuple record;
	private int i = 0;
	private String name = null;
	private String type = null;
	private String curLine = null;
	
	public Table(String from){
		this.from = from;
		
		//Create buffer reader
		try{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	
	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	public Tuple next(){
		try{
			//read table
			while(i < 2){
				curLine = br.readLine();
				
				//store name
				if(i == 0){
					name = curLine;
					i++;
				}
				//store type
				else if(i == 1){
					type = curLine;
					i++;
				}			
			}

			curLine = br.readLine();
			
			if(curLine == null){
				return null;
			}
			
			//set up tuple
			record = new Tuple(name, type, curLine);
			
			record.setAttributeName();
			record.setAttributeType();
			record.setAttributeValue();
			
			if(curLine != null){
				return record;
			}			      
		}catch(Exception e){
			e.printStackTrace();
		}   
		return null;
	}
	

	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return tuple.getAttributeList();
	}
	
}
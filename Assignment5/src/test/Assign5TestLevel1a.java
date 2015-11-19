package test;
import simpledatabase.FormRAtree;
import simpledatabase.Operator;
import simpledatabase.Tuple;
import junit.framework.TestCase;

public class Assign5TestLevel1a extends TestCase {
    private String selectText;
    private String fromText;
    private String joinText;
    private String whereText;
    private String orderText;
    FormRAtree tree;
    Operator root;
    Tuple tuple;
    boolean next=true;
    
	/*SQL Statement: SELECT Name 
	 * 				 FROM CourseEnroll 
	 * 				 WHERE CourseEnroll.courseID="COMP2021";*/
	
	public void testLevel1a(){
		 int cnt = 0;
		 
		selectText = "Name";
        fromText = "Student";
        joinText = "";
        whereText = "Student.Programme=\"61032\"";
        orderText = "";
        
        tree = new FormRAtree(selectText,fromText,joinText,whereText,orderText);
        root = tree.structureTree();
        
        tuple = root.next();
        while(tuple != null){
        	if(next == false)
        		tuple = root.next();
			
			if(cnt == 0){
				assertTrue(tuple.getAttributeValue(0).equals("\"Chris\""));
				cnt++;
			}
			else if(cnt == 1){
				assertTrue(tuple.getAttributeValue(0).equals("\"Julian\""));
				cnt++;
			}

			next = false;
        } 
        
        assertTrue(cnt==2);
	}
}
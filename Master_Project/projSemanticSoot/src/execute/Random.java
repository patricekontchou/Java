package execute;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import soot.SootClass;
import soot.SootMethod;
import soot.SootField;

public class Random {
	
	
	public static int random(int min, int max){
		int x = 0;
		boolean correctChoice = true;
		x = (int)(Math.random() * (max-min+1)) + min;
		if(x<min || x > max){
					
			do{
		
				x = (int)(Math.random() * (max-min+1)) + min;
		
				//System.out.println("min is "+ min+ " X is "+ x+ " Max is " +max);	
				if((x>=min || x <= max))
				correctChoice = false;
	}while(correctChoice == true);}
		 
    	return x;
    }
	
	public static SootClass randomClass(List<SootClass> classes){
		
		SootClass res;
		int x=random(0,classes.size()-1);
		if(classes.contains(x))
			res = classes.get(x);
		else res = classes.get(1);
		
		return res;
	}
	
	//Choose a random class from the list application classes
	public static SootClass randomClass(Set<SootClass> classes){
		List<SootClass> list = new ArrayList<SootClass>();
		list.addAll(classes);
		
		return randomClass(list);
	}
	
	//Choose randomly a package from application packages
	public static packageDescription randomPackage(List<packageDescription> packages){

			packageDescription res;
			int x=random(0,packages.size()-1);
			res = packages.get(x);
			
			return res;
		}
	
	public static SootField randomField(classDescription cl){
		int x=random(0,cl.fields.size()-1);
						
		return cl.fields.get(x);
	}
	
	public static SootMethod randomMethod(classDescription cl){
		int x=random(0,cl.methods.size()-1);
		
		return cl.methods.get(x);
	}
	
	public static SootClass randomSubClass(classDescription cl){
		int x=random(0, cl.nbSubClasses-1);
		
		return cl.subClasses.get(x);
	}
	
	public static SootClass randomSuperClass(classDescription cl){
		int x=random(0, cl.nbSubClasses-1);
		
		return cl.superClasses.get(x);
	}
	
}

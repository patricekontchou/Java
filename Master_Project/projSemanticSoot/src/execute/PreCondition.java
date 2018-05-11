package execute;

import soot.*;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;


public class PreCondition {
	
	public static boolean hasMethod(HashMap<SootClass, classDescription> classes, SootClass cl){
		
		return (classes.get(cl).methods).isEmpty();
	}
	
	public static boolean hasField(HashMap<SootClass, classDescription> classes, SootClass cl){
		return (classes.get(cl).fields).isEmpty();
	}
	
	public static boolean isEqual(HashMap<SootClass, classDescription> classes, SootClass class1, SootClass class2){
		
		return class1.equals(class2) ;
	}
	
	public static boolean shareInheritanceHierarchie(HashMap<SootClass, classDescription> classes, SootClass class1, SootClass class2){
				
		return ((classDescription)classes.get(class1)).superClasses.contains(class2) || ((classDescription)classes.get(class1)).subClasses.contains(class2);
	}
	
	public static boolean isAlreadyInlined(HashMap<SootClass, classDescription> classes, SootClass cl){
		return !classes.containsKey(cl) ;
	}
	
		public static boolean isSuperClass(HashMap<SootClass, classDescription> classes, SootClass sourceClass, SootClass targetClass){
			return ((classDescription)classes.get(sourceClass)).superClasses.contains(targetClass);
		}
	
	public static boolean isSubClass(HashMap<SootClass, classDescription> classes, SootClass sourceClass, SootClass targetClass){
		return ((classDescription)classes.get(sourceClass)).subClasses.contains(targetClass);
	}
	
	public static boolean extractClass(HashMap<SootClass, classDescription> classes, SootClass cl){
		//return ((classes.get(cl).methods).isEmpty() || classes.get(cl).methods.size()>10 || classes.get(cl).fields.size()>2) ;
		return ((classes.get(cl).methods).isEmpty());
	}
	
	public static boolean hasSuperClass(HashMap<SootClass, classDescription> classes, SootClass sourceClass){
		return !((classDescription)classes.get(sourceClass)).superClasses.isEmpty();
	}
	
	public static boolean hasSubClass(HashMap<SootClass, classDescription> classes, SootClass sourceClass){
		return !((classDescription)classes.get(sourceClass)).subClasses.isEmpty();
	}
	
}

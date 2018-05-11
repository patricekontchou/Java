package execute;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import soot.*;
import soot.jimple.spark.SparkTransformer;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.CallGraphBuilder;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.OneCFAContextManager;
import soot.jimple.toolkits.callgraph.Targets;
import soot.options.Options;
import soot.util.Chain;
import soot.util.IterableSet;
import soot.util.queue.QueueReader;



public class Prog {

	protected static CallGraph cg; 
	public static ArrayList<SootClass> applicationClasses;
	public static ArrayList<SootClass> librairyClasses;
	private static ArrayList<SootMethod> methodM;
	public static ArrayList<packageDescription> packageD = new ArrayList <packageDescription>();
	public static ArrayList<String> pNameList = new ArrayList<String>();
	public static List<classDescription> classesD=new ArrayList<classDescription>();
	public  HashMap<String, packageDescription> packageDescMap = new HashMap<String, packageDescription>(); 
	protected static HashMap<SootClass,classDescription> classesDD= new HashMap<SootClass,classDescription>();
		
	public ArrayList<SootClass> getClassM ()
	
	{return applicationClasses;}
	
	
	public Prog (CallGraph cg, ArrayList<String> classes)
	
	{
		
		this.cg = cg;
				
		applicationClasses = new ArrayList<SootClass>();
		methodM = new ArrayList<SootMethod>();
		
		/*
		 * The below for loop goes trop the graph of source code represented in graph using callGraph and get all classes
		 * with name = to the found in array classes. This will load this classes in the applications classes 
		 * which will be used for the rest of analysis
		 */
		classDescription cl;
		for (SootClass c : Scene.v().getClasses()) {
			if (classes.contains(c.getName())){
				applicationClasses.add(c);	
			}
			
		}
		
		
		QueueReader<Edge> reader = cg.listener();
	        while(reader.hasNext()) {
	            Edge e = (Edge) reader.next();
		    if(!(e.toString().contains("forName(")  || e.toString().contains("newInstance()"))) {
	           	SootMethod m1 = e.src();
	            SootMethod m2 = e.tgt();
	        	if (!methodM.contains(m1) && applicationClasses.contains(m1.getDeclaringClass()))
	        		methodM.add(m1);
	            	if (!methodM.contains(m2) && applicationClasses.contains(m2.getDeclaringClass()))
	            		methodM.add(m2);
		    }
		    else{
		    	//System.out.println("remove "+e.toString());
		    	cg.removeEdge(e);
		    }
			
	        }
	        
	        int i=0;
	        //System.out.println("ClassM maxSize= "+applicationClasses.size()+" methodM maxSize = "+methodM.size());
	        
	        //Get name of packages in the system
	        for (SootClass s: applicationClasses){
	        	if (!pNameList.contains(s.getJavaPackageName()))
					pNameList.add(s.getJavaPackageName());
	        }
	        
	       
	    
	        
			// parcourir ClassM classe par classe et instancier Classe c.
			Iterator<SootClass> it=applicationClasses.iterator();
			while (it.hasNext()){
				SootClass s=(SootClass)it.next();
				//System.out.println("____________________________________________");			
				
				cl=new classDescription(s);
				
				//System.out.println("done");
				
				classesD.add(cl);
				classesDD.put(s, cl);
				
				//System.out.println(i+" "+cl.name);
				///System.out.println("nbMethods: "+cl.nbMethods+" nbFields: "+cl.nbFields);
				
				
				
				
				//System.out.print("methods: ");
				//iter=cl.methods.iterator();
				//while(iter.hasNext()){
				//	System.out.print(((SootMethod)iter.next()).getName()+" ");
				//}
				
				
				//System.out.println("");
				///System.out.print("Vocabulary: ");
				//iter=cl.vocabulary.iterator();
				//while(iter.hasNext()){
					//System.out.print(((String)iter.next())+" ");
				//}
				
				//System.out.println("");								
				//System.out.print("subClasses: ");
				//iter=cl.subClasses.iterator();
				//while(iter.hasNext()){
				//	System.out.print(((SootClass)iter.next()).getName()+" ");
				//}
				
				
				//System.out.println("");
				//System.out.print("superClasses: ");
				//iter=cl.superClasses.iterator();
				//while(iter.hasNext()){
				//	System.out.print(((SootClass)iter.next()).getName()+" ");
				//}
				
				
				//System.out.println("");
				//System.out.print("Associations: ");
				//iter=cl.Assoc.iterator();
				//while(iter.hasNext()){
					//System.out.print(((SootClass)iter.next()).getName()+" ");
				//}
				//System.out.println("");
				
				//System.out.print("cohesion: ");
				//iter=cl.cohesion.iterator();
				//while(iter.hasNext()){
					//System.out.print(((SootMethod)iter.next()).getName()+" ");
				//}
				//System.out.println("");
				
				//System.out.print("Attribute RW: ");
				//iter=cl.attributRW.iterator();
				//while(iter.hasNext()){
					//System.out.print(((SootClass)iter.next()).getName()+" ");
				//}
				//System.out.println("");
				
				//System.out.print("callOut: ");
				//Set<?> set=cl.callOut.keySet();
				//iter=set.iterator();
				//System.out.println(cl.callOut.size());
				//while(iter.hasNext()){
					//SootMethod m=(SootMethod)iter.next();
					//System.out.println("\t  "+m+" --------  "+cl.callOut.get(m));
				//}
				//System.out.println("");
				
				//System.out.print("callIn: ");
				//set=cl.callIn.keySet();
				//iter=set.iterator();
				//System.out.println(cl.callIn.size());
				//while(iter.hasNext()){
					//SootMethod m=(SootMethod)iter.next();
					//System.out.println("\t  "+m+" --------  "+cl.callIn.get(m));
				//}
				//System.out.println("");
				
				
				i++;
				//System.out.println("nbSupserclasse: "+cl.nbsuperClasses+" nbSubClasses: "+cl.nbSubClasses+" nbAssoc: "+cl.nbAssoc+" cohesion: "+cl.cohesion.size());				
			} 
			
			Set<SootClass> liste=classesDD.keySet();
			
			for(SootClass c : liste){
				classesDD.get(c).semantic=semanticFitnessFunction.getSematicSimilarityList(classesDD, c);
			}
			
			
			/*Create a list of Package Description using name of all package in the system.
			 * Create a HashMap table where each package name is mapped to his package description
			 */
			for (String s : pNameList){
				packageDescription pd = new packageDescription(s); // call constructor of package description and pass parameter
				packageD.add(pd);
				packageDescMap.put(s, pd);
			}
			
	}
	
	/*public void printP(){
		int j = 0;
		
		//for (String s : pNameList)
			//System.out.println(s);
		
		System.out.println("__________________________");
		System.out.println("The size of package Name is:" + pNameList.size());
		System.out.println("__________________________");
	
		 for (packageDescription s : packageD){
			
			System.out.println("package name  " + s.packageName);
			//System.out.println(FitnessFunction.));
			int i = 0;
			 for(SootClass c: s.pClasses){
			    	 String d = c.getName();
			    	 System.out.println("List of class of this package: " + d + "i = "+ " " + i);
			    	 i++;
			     }
			System.out.println("Class size: " + s.pClasses.size());
			j = j + s.pClasses.size();
			System.out.println("Number of Method: " + s.pMethods.size());
			System.out.println("Number of call out:" + s.pCallOut.size());
			System.out.println("Number of callin: " + s.pCallIn.size());
			System.out.println("Number of fields: " + s.nbFields);
			System.out.println("number of attribute RW: " + s.pAttributRW.size());
			System.out.println("Number of cohesion: " + s.pCohesion.size());
			System.out.println("Number of vocabulary: " + s.vocabulary.size());
			for(String f: s.vocabulary){
				System.out.println(f);
			}
			System.out.println("ClassM maxSize= "+applicationClasses.size()+" methodM maxSize = "+methodM.size());
		}
		System.out.println("total class :" + j + "Against total app classes: " + applicationClasses.size());
		int t = 0;
		
	}*/
	 public static List<SootClass> superClasses(SootClass classe) {
		 List<SootClass> sc = new ArrayList<SootClass>();
         if(!classe.isInterface()){
             for (SootClass  c: Scene.v().getActiveHierarchy().getSuperclassesOf(classe))
                 if(applicationClasses.contains(c))
                     sc.add(c);}
         return sc;
     }
	
	public static List<SootClass> subClasses(SootClass classe) {
		List<SootClass> sc = new ArrayList<SootClass>();
		if(!classe.isInterface())
			for (SootClass  c: Scene.v().getActiveHierarchy().getSubclassesOf(classe)) 
				if(applicationClasses.contains(c))
					sc.add(c);
		return sc;
	}
	
	public static List<SootMethod> callOut(SootMethod method) {
		List<SootMethod> couple = new ArrayList<SootMethod>();

		Iterator<?> targets = new Targets(cg.edgesOutOf(method));
		while (targets.hasNext()) {		
		//	Edge e = (Edge)targets.next();
		//	SootMethod tgtMethod = e.getTgt().method();
			SootMethod tgt = (SootMethod)targets.next();
			if (methodM.contains(tgt) && !tgt.getDeclaringClass().equals(method.getDeclaringClass()))
				couple.add(tgt); 
		}
		return couple;
	}
	// ensemble des methodes qui appel method
	public  static List<SootMethod> callIn(SootMethod method) {
		List<SootMethod> couple = new ArrayList<SootMethod>();

		Iterator<?> targets = cg.edgesInto(method);
		while (targets.hasNext()) {		
			Edge e = (Edge)targets.next();
			SootMethod tgt = e.getSrc().method();
			//SootMethod tgt = (SootMethod)targets.next();
			if (methodM.contains(tgt) && !tgt.getDeclaringClass().equals(method.getDeclaringClass()))
			couple.add(tgt);
		}
		return couple;
	}
	
	public static HashMap<SootMethod, List<SootMethod>> callOut(classDescription cl){
		
		HashMap<SootMethod, List<SootMethod>> r=new HashMap<SootMethod, List<SootMethod>>();
		for(SootMethod m : cl.methods){
			if(!callOut(m).isEmpty())
			r.put(m, callOut(m));
		}
		return r;	
	}
	
	public static HashMap<SootMethod, List<SootMethod>> callIn(classDescription cl){
		
		HashMap<SootMethod, List<SootMethod>> r=new HashMap<SootMethod, List<SootMethod>>();
		for(SootMethod m : cl.methods){
			if(!callIn(m).isEmpty())
			r.put(m, callIn(m));
		}
		return r;
	}
	
	public static  boolean call(SootMethod source, SootMethod target) 
	{
		Iterator<?> targets = new Targets(cg.edgesOutOf(source));
		while (targets.hasNext()) {		
			SootMethod tgt = (SootMethod)targets.next();
			if(target.equals(tgt) && target.getDeclaringClass().equals(source.getDeclaringClass()))
				return true;
		}
		return false;	
	}
	
	public static List<SootClass> getAssoc(classDescription classe)
	
	{
		List<SootClass> list = new ArrayList<SootClass>();
		for (SootField field : classe.fields) {
			try {
			SootClass tmp = Scene.v().getSootClass(field.getType().toString().split(" ")[0]);
			if(applicationClasses.contains(tmp))
				list.add(tmp);
			}
			catch(Exception o) { }
			
		} 
		return list;		
	}
	
	public static List<SootClass> getAggreg(SootClass classe){
		
		List<SootClass> list = new ArrayList<SootClass>();
		for (SootMethod meth : methodM) {
			if(meth.toString().contains("<init>"))
				//System.out.println(meth);
				for (Object tmp : meth.getParameterTypes()) {
					Type type = (Type)tmp;
					//System.out.println("  type: "+type);
					try {
						SootClass tmp2 = Scene.v().getSootClass(type.toString().split(" ")[0]);
						if(classe.toString().compareTo(tmp2.toString()) == 0){
							//System.out.println("ajout de "+meth.getDeclaringClass());
							list.add(meth.getDeclaringClass());
						}
						}
						catch(Exception o) { }
				}
		}
		return list;
	}
	
	
	public  int nbinvoke(SootClass source, SootClass target) 
	{
		
		int nb = 0;
		for (SootMethod method : source.getMethods()) {
			Iterator<?> targets = new Targets(cg.edgesOutOf(method));
			while (targets.hasNext()) {		
				SootMethod tgt = (SootMethod)targets.next();
				if(target.getMethods().contains(tgt))
					nb++;
			}
	}	
		return nb;	
	}
	
	
	public static List<SootMethod> cohesion(classDescription source){
		
		List<SootMethod> res=new ArrayList<SootMethod>(); 
		for (SootMethod method : source.methods) {
		     
			Iterator<?> targets = new Targets(cg.edgesOutOf(method));
			while (targets.hasNext()) {		
				SootMethod tgt = (SootMethod)targets.next();
				if(source.equals(tgt.getDeclaringClass())){
					//res.add(tgt.getDeclaringClass());
					res.add(tgt);
				}
			}
		}	
		return res;	
	}
	//Package Methods that I just implemented. Could be moved elsewhere or modified to stay here
	//__________________________________________________________________________________________
	
	// Get all call in of a specific method 
	public  static List<SootMethod> callInMethod(SootMethod method) {
	    			
            List<SootMethod> couple = new ArrayList<SootMethod>();
			Iterator<?> targets = cg.edgesInto(method);
				while (targets.hasNext()) {		
					Edge e = (Edge)targets.next();
					SootMethod tgt = e.getSrc().method();
					if (methodM.contains(tgt) && !tgt.getDeclaringClass().getPackageName().equals(method.getDeclaringClass().getPackageName()))
						couple.add(tgt);
				}
		
		return couple;
	}
	
	
	// call in of methods in a package
public static HashMap<SootMethod, List<SootMethod>> pCallIn(packageDescription source){
		
		HashMap<SootMethod, List<SootMethod>> r=new HashMap<SootMethod, List<SootMethod>>();
		for(SootMethod m : source.pMethods){
			if(!callInMethod(m).isEmpty()) // iterate through all methods of a package and get their call in. put them in a hashtabel
			r.put(m, callIn(m));
		}
		return r;
	}

/* this function will get all the call out of a specific method from a package
and return to pCallOut which sum up for all call out for a given package*/

public static List<SootMethod> callOutMethod(SootMethod method) {
	List<SootMethod> couple = new ArrayList<SootMethod>();

	Iterator<?> targets = new Targets(cg.edgesOutOf(method));
	while (targets.hasNext()) {		
		SootMethod tgt = (SootMethod)targets.next();
		if (methodM.contains(tgt) && !tgt.getDeclaringClass().getPackageName().equals(method.getDeclaringClass().getPackageName()))
			couple.add(tgt); 
	}
	return couple;
}

/* this function will get all the call out of a specific method from a package
and return to pCallOut which sum up for all call out for a given package*/

public static HashMap<SootMethod, List<SootMethod>> pCallOut(packageDescription source){
	
	HashMap<SootMethod, List<SootMethod>> r=new HashMap<SootMethod, List<SootMethod>>();
	for(SootMethod m : source.pMethods){
		if(!callOutMethod(m).isEmpty())
		r.put(m, callOut(m));
	}
	return r;	
}
	
//Get cohesion for a given package: all Methods that call other methods within the same package
public static List<SootMethod> pCohesion(packageDescription source){
		
		List<SootMethod> res=new ArrayList<SootMethod>(); 
		for (SootMethod method : source.pMethods) {
		     
			Iterator<?> targets = new Targets(cg.edgesOutOf(method));
			while (targets.hasNext()) {		
				SootMethod tgt = (SootMethod)targets.next();
				SootClass c = tgt.getDeclaringClass();
				if(source.packageName.equals(c.getPackageName())){
					res.add(tgt);
				}
			}
		}	
		return res;	
	}

/* Get attribute read and write of method of a given package 
 * 
 */
public static List<SootClass> pAttributRW(packageDescription source){
	List<SootClass> att = new ArrayList<SootClass>();
	for(SootMethod method : source.pMethods){
		if (method.hasActiveBody()) {
			for (Unit ut : method.getActiveBody().getUnits())
				for (ValueBox vb : ut.getUseBoxes())
					if (vb.getValue() instanceof soot.jimple.FieldRef){
						SootClass cc = ((soot.jimple.FieldRef)vb.getValue()).getField().getDeclaringClass();
						//System.out.println(" attributRW "+cc+" --------- method--------  "+method.getName()+"  ---  "+(((soot.jimple.FieldRef)vb.getValue())));
						if(applicationClasses.contains(cc)){
							att.add(cc);
						}
							
					}
		}
	}
	return att;
}

	
	protected static List<SootClass> attributRW(SootMethod method){
		List<SootClass> att = new ArrayList<SootClass>();
		if (method.hasActiveBody()) {
			for (Unit ut : method.getActiveBody().getUnits())
				for (ValueBox vb : ut.getUseBoxes())
					if (vb.getValue() instanceof soot.jimple.FieldRef){
						SootClass cc = ((soot.jimple.FieldRef)vb.getValue()).getField().getDeclaringClass();
						if(applicationClasses.contains(cc))
							att.add(cc);
					}
		}
		return att;
	}
	
	public static List<SootClass> _attributRW(classDescription classe){
		List<SootClass> att = new ArrayList<SootClass>();
		for(SootMethod method : classe.methods){
			if (method.hasActiveBody()) {
				for (Unit ut : method.getActiveBody().getUnits())
					for (ValueBox vb : ut.getUseBoxes())
						if (vb.getValue() instanceof soot.jimple.FieldRef){
							SootClass cc = ((soot.jimple.FieldRef)vb.getValue()).getField().getDeclaringClass();
							//System.out.println(" attributRW "+cc+" --------- method--------  "+method.getName()+"  ---  "+(((soot.jimple.FieldRef)vb.getValue())));
							if(applicationClasses.contains(cc)){
								att.add(cc);
							}
								
						}
			}
		}
		return att;
	}
	
	protected static boolean attributRW(SootClass classe, SootMethod method, SootField field){
		boolean output = false;
		if (method.hasActiveBody()) {
			for (Unit ut : method.getActiveBody().getUnits())
				for (ValueBox vb : ut.getUseBoxes())
					if (vb.getValue() instanceof soot.jimple.FieldRef){
						//SootClass cc = ((soot.jimple.FieldRef)vb.getValue()).getField().getDeclaringClass();
						SootField ff = ((soot.jimple.FieldRef)vb.getValue()).getField();
						if(ff.equals(field))
							return true;
					}
		}
		return output;
	}
	
	private static List<SootClass> getApplicationClasses(){
		List<SootClass> output = new ArrayList<SootClass>();
		output.addAll(Scene.v().getApplicationClasses());
		return output;
		
	}
	
	private static List<SootClass> getLibrairyClasses(){
		List<SootClass> output = new ArrayList<SootClass>();
		output.addAll(Scene.v().getLibraryClasses());
		return output;
	}
	/*
	 * Calculate cosine similarity between two packages. Get name of package 1 and package 2 and calculate their similarity based on their vocabulary
	 */
	public static double pCosine_similarity(packageDescription package1, packageDescription package2){
		double res=0;
		if(package1.packageName.equals(package2.packageName))
			res=1;
		else{
			Map<String, Double> p1=new HashMap<String, Double>();
			Map<String, Double> p2=new HashMap<String, Double>();
			
			//calculate Term Frequency tf for each package
			Iterator<String> it=package1.vocabulary.iterator();
			while(it.hasNext()){
				String s=(String)it.next();
				if(!p1.containsKey(s))
					p1.put(s, 1.0);
				else
					p1.put(s, p1.get(s)+1);
			}
			
			it=package2.vocabulary.iterator();
			while(it.hasNext()){
				String s=(String)it.next();
				if(!p2.containsKey(s))
					p2.put(s, 1.0);
				else
					p2.put(s, p2.get(s)+1);
			}	
			res = FitnessFunction.round((compute_cosine(p1, p2)+compute_cosine(p2, p1))/2, 3);
		}
		return res;
	}
	public static double compute_cosine(Map<String, Double> v1, Map<String, Double> v2) {
        Map<String, Double> v3=new HashMap<String, Double>();
        Iterator it=v1.keySet().iterator();
        while(it.hasNext()){
        	String s=(String)it.next();
        	if(v2.containsKey(s))
        		v3.put(s, v2.get(s));
        	else
        		v3.put(s, 0.0);
        }
        double sclar = 0, norm1 = 0, norm2 = 0;
        for (String k : v1.keySet()){
        	sclar += v1.get(k) * v3.get(k);
        	norm1 += v1.get(k) * v1.get(k);
        	norm2 += v3.get(k) * v3.get(k);
        }
        if(sclar!=0 && norm1*norm2!=0)
        	
        	//sclar = v1*V2; norm1 = ||V1|| and norm2 = ||V2||
        	return FitnessFunction.round(sclar / Math.sqrt(norm1 * norm2), 3);
        else
        	return 0;
	}
}

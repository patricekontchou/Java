package execute;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.Targets;
import soot.util.Chain;

public class packageDescription {
	
	private List<SootClass> appClasses = Prog.applicationClasses;
	public ArrayList <SootClass> pClasses;
	public String packageName;
	public List<SootField> pFields;
	public List<SootMethod> pMethods;
	public int pCoupling;
	public List<SootClass> pAttributRW;
	public List<String> vocabulary;
	public HashMap<SootMethod, List<SootMethod>> pCallOut;
	public HashMap<SootMethod, List<SootMethod>> pCallIn;
	public HashMap<SootClass, Double> semantic;
	
	public int nbMethods;
	public int nbFields;
	public int nbSubClasses;
	public int nbsuperClasses;
	public int nbAssoc;
	public List<SootClass> Assoc;
	public List<SootMethod> pCohesion;
	
	public packageDescription(String packageName){
		
		this.packageName = packageName;
		this.pClasses = getPackageCLass(packageName);
		this.pMethods = getPackageMethod();
		this.nbFields= getNumberOfField();
		this.pFields = getPackageField(this);
		this.pCohesion=Prog.pCohesion(this);
		this.pAttributRW=Prog.pAttributRW(this);
		this.pCallOut=Prog.pCallOut(this);
		this.pCallIn=Prog.pCallIn(this);
		this.vocabulary = generateVocabulary(this);
		this.pCoupling = this.getCoupling();
			
		//System.out.println(" okkk ");
	}
	
	//Get all packages belonging to an instance of a package
	public ArrayList<SootClass> getPackageCLass(String source){
		
		ArrayList<SootClass> pClass = new ArrayList<SootClass>();
		Iterator<SootClass> it=appClasses.iterator();
		while (it.hasNext()){
			SootClass s=(SootClass)it.next();
			
			if(source.equals(s.getPackageName()))
				pClass.add(s);
		}
		
		return pClass;
	}
	
	//Get all methods belonging to classes of an instance of a package description
	public List<SootMethod> getPackageMethod() {
		
		List<SootMethod> pMethod = new ArrayList<SootMethod>();
		Iterator<SootClass> it=this.pClasses.iterator();
		while (it.hasNext()){
			SootClass s=(SootClass)it.next();
			pMethod.addAll(s.getMethods()); 
				
		}
		
		return pMethod;
	}
	
	//get number of fields belonging to a package
public int getNumberOfField() {
		
		int fieldCount = 0; 
		Iterator<SootClass> it=this.pClasses.iterator();
		while (it.hasNext()){
			SootClass s=(SootClass)it.next();
			int i = s.getFieldCount();
			fieldCount = fieldCount + i;
				
		}
		
		return fieldCount;
	}
	
	public ArrayList<SootField> getPackageField(packageDescription source){
		ArrayList<SootField> res = new ArrayList<SootField>();
		for(SootClass s: source.pClasses){
			res.addAll(s.getFields());
		}
		return res;
	}

public int getCoupling(){
	
	return(this.pCallIn.size() + this.pCallOut.size());
}

	public List<String> generateVocabulary(packageDescription source){
		
		ArrayList<String> res=new ArrayList<String>();
		res.add(source.packageName);
		for(SootClass s: source.pClasses){
			res.add(s.getName());
			
			ArrayList<SootField> temp = new ArrayList<SootField>();
			temp.addAll(s.getFields());
			
			for(SootField f : temp){
				res.add(f.getName());
			}
			
			for(SootMethod m : s.getMethods()){
				res.add(m.getName());
				for(Object p : m.getParameterTypes()){
					if(!res.contains(p) && !p.toString().equalsIgnoreCase("<init>"))
					res.add(p.toString());
				}
			}
		}
		
		return res;
	}
	
}
package execute;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.util.Chain;

public class classDescription{
	
	public SootClass classe;
	public String packageName;
	public String name;
	public List<SootField> fields;
	public List<SootMethod> methods = new ArrayList<SootMethod>();
	public List<SootClass> superClasses;
	public List<SootClass> subClasses;
	public List<SootClass> attributRW;
	public List<String> vocabulary;
	public HashMap<SootMethod, List<SootMethod>> callOut;
	public HashMap<SootMethod, List<SootMethod>> callIn;
	public HashMap<SootClass, Double> semantic;
	
	public int nbMethods;
	public int nbFields;
	public int nbSubClasses;
	public int nbsuperClasses;
	public int nbAssoc;
	public List<SootClass> Assoc;
	public List<SootMethod> cohesion;
	
	public classDescription(SootClass classe ){
		
		this.classe=classe;
		this.packageName = classe.getPackageName();
		this.name=classe.getName();
		this.nbFields= classe.getFieldCount();
		this.nbMethods= classe.getMethodCount();
		
		this.fields=new ArrayList<SootField>();
		Chain ch=classe.getFields();
		fields.addAll(ch);		
		this.methods=classe.getMethods();
				
		this.superClasses=Prog.superClasses(classe);
		this.subClasses=Prog.subClasses(classe);
		
		this.nbSubClasses=subClasses.size();
		this.nbsuperClasses=superClasses.size();
		
		this.Assoc=Prog.getAssoc(this);	
		this.nbAssoc=Assoc.size();
		
		this.cohesion=Prog.cohesion(this);
		this.attributRW=Prog._attributRW(this);
		this.callOut=Prog.callOut(this);
		this.callIn=Prog.callIn(this);
		
		vocabulary=generateVocabulary(classe);
		
		//System.out.println(" okkk ");
	}
	
	public List<String> generateVocabulary(SootClass classe){
		
		ArrayList<String> res=new ArrayList<String>();
		res.add(classe.getName());
			
		for(SootMethod m : this.methods){
			res.add(m.getName());
			for(Object p : m.getParameterTypes()){
				if(!res.contains(p) && !p.toString().equalsIgnoreCase("<init>"))
					res.add(p.toString());
			}
		}
		
		for(SootField f : this.fields){
			res.add(f.getName());
			//res.add(f.getType().toString());
		}
		
		Iterator it=subClasses.iterator();
		while(it.hasNext()){
			res.add(((SootClass)it.next()).getName());
		}
		
		it=superClasses.iterator();
		while(it.hasNext()){
			res.add(((SootClass)it.next()).getName());
		}		
		
		
		return res;
	}
	
}
package execute;

/*
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import soot.SootClass;
import soot.SootMethod;
import soot.SootField;


public class Refactoring {
	
	
	public static HashMap<SootClass, classDescription> moveField(HashMap<SootClass, classDescription> classes, SootClass sourceClass, SootClass targetClass, SootField field ){
		HashMap<SootClass, classDescription> res = new HashMap<SootClass, classDescription>();
		res.putAll(classes);
		
		//field.getSignature(targetClass, field.getName(), field.getType());
		
		res.get(sourceClass).fields.remove(field);
		res.get(targetClass).fields.add(field);
		
		res.get(sourceClass).nbFields = res.get(sourceClass).fields.size();
		res.get(targetClass).nbFields = res.get(targetClass).fields.size();
		
		res.get(sourceClass).attributRW=Prog._attributRW(res.get(sourceClass));
		res.get(targetClass).attributRW=Prog._attributRW(res.get(targetClass));
		
		res.get(sourceClass).Assoc=Prog.getAssoc(res.get(sourceClass));
		res.get(targetClass).Assoc=Prog.getAssoc(res.get(targetClass));
		
		res.get(sourceClass).nbAssoc = res.get(sourceClass).Assoc.size();
		res.get(targetClass).nbAssoc = res.get(targetClass).Assoc.size();
		
		return res;
	}
	
	public static HashMap<SootClass, classDescription> moveMethod(HashMap<SootClass, classDescription> classes, SootClass sourceClass, SootClass targetClass, SootMethod method){
		
		HashMap<SootClass, classDescription> res = new HashMap<SootClass, classDescription>();
		res.putAll(classes);
		
		method.setDeclaringClass(targetClass);
		SootClass c = sourceClass;
	
		res.get(sourceClass).methods.remove(method);
		res.get(targetClass).methods.add(method);
		
		res.get(sourceClass).nbMethods=res.get(sourceClass).methods.size();
		res.get(targetClass).nbMethods=res.get(targetClass).methods.size();
		
		res.get(sourceClass).callIn = Prog.callIn(res.get(sourceClass));
		res.get(targetClass).callIn = Prog.callIn(res.get(targetClass));
		
		res.get(sourceClass).callOut = Prog.callOut(res.get(sourceClass));
		res.get(targetClass).callOut = Prog.callOut(res.get(targetClass));
		
		res.get(sourceClass).cohesion=Prog.cohesion(res.get(sourceClass));
		res.get(targetClass).cohesion=Prog.cohesion(res.get(targetClass));
		
		return res;
	}
	
	public static HashMap<SootClass, classDescription> inlineClass(HashMap<SootClass, classDescription> classes, SootClass class1, SootClass class2){
		
		HashMap<SootClass, classDescription> res = new HashMap<SootClass, classDescription>();
		res.putAll(classes);
		classDescription sc=res.get(class1);
		classDescription tc=res.get(class2);
		
		//Patrice Commented it Prog.applicationClasses.remove(class2);
		
		sc.fields.addAll(tc.fields);
		sc.methods.addAll(tc.methods);
		sc.nbFields=sc.fields.size();
		sc.nbMethods=sc.methods.size();
		sc.callIn.putAll(tc.callIn);
		sc.callOut.putAll(tc.callOut);
		sc.attributRW.addAll(tc.attributRW);
		sc.Assoc.addAll(tc.Assoc);
		sc.nbAssoc=sc.Assoc.size();
		sc.subClasses.addAll(tc.subClasses);
		sc.superClasses.addAll(tc.superClasses);
		sc.vocabulary.addAll(tc.vocabulary);
		sc.cohesion.addAll(tc.cohesion);
		
		res.remove(class2);
		res.put(class1, sc);	
		
		return res;
	}
	
	public static HashMap<SootClass, classDescription> extractClass(HashMap<SootClass, classDescription> classes, SootClass class1, SootClass class2){
		
		HashMap<SootClass, classDescription> res=classes;
		
		return res;
	}
	
	public static HashMap<SootClass, classDescription> pullUpField(HashMap<SootClass, classDescription> classes, SootClass sourceClass, SootClass targetClass, SootField field ){
		HashMap<SootClass, classDescription> res = new HashMap<SootClass, classDescription>();
		res.putAll(classes);
		
		//field.getSignature(targetClass, field.getName(), field.getType());
		
		res.get(sourceClass).fields.remove(field);
		res.get(targetClass).fields.add(field);
		
		res.get(sourceClass).nbFields = res.get(sourceClass).fields.size();
		res.get(targetClass).nbFields = res.get(targetClass).fields.size();
		
		res.get(sourceClass).attributRW=Prog._attributRW(res.get(sourceClass));
		res.get(targetClass).attributRW=Prog._attributRW(res.get(targetClass));
		
		res.get(sourceClass).Assoc=Prog.getAssoc(res.get(sourceClass));
		res.get(targetClass).Assoc=Prog.getAssoc(res.get(targetClass));
		
		res.get(sourceClass).nbAssoc = res.get(sourceClass).Assoc.size();
		res.get(targetClass).nbAssoc = res.get(targetClass).Assoc.size();
		
		return res;
	}
	
	public static HashMap<SootClass, classDescription> pushDownField(HashMap<SootClass, classDescription> classes, SootClass sourceClass, SootClass targetClass, SootField field ){
		HashMap<SootClass, classDescription> res = new HashMap<SootClass, classDescription>();
		res.putAll(classes);
		
		//field.getSignature(targetClass, field.getName(), field.getType());
		
		res.get(sourceClass).fields.remove(field);
		res.get(targetClass).fields.add(field);
		
		res.get(sourceClass).nbFields = res.get(sourceClass).fields.size();
		res.get(targetClass).nbFields = res.get(targetClass).fields.size();
		
		res.get(sourceClass).attributRW=Prog._attributRW(res.get(sourceClass));
		res.get(targetClass).attributRW=Prog._attributRW(res.get(targetClass));
		
		res.get(sourceClass).Assoc=Prog.getAssoc(res.get(sourceClass));
		res.get(targetClass).Assoc=Prog.getAssoc(res.get(targetClass));
		
		res.get(sourceClass).nbAssoc = res.get(sourceClass).Assoc.size();
		res.get(targetClass).nbAssoc = res.get(targetClass).Assoc.size();
		
		return res;
	}
	
	public static HashMap<SootClass, classDescription> pullUpMethod(HashMap<SootClass, classDescription> classes, SootClass sourceClass, SootClass targetClass, SootMethod method)throws java.lang.NullPointerException
	{
		
		HashMap<SootClass, classDescription> res = new HashMap<SootClass, classDescription>();
		res.putAll(classes);
		
		method.setDeclaringClass(targetClass);
		
		res.get(sourceClass).methods.remove(method);

		res.get(targetClass).methods.add(method);
				
		res.get(sourceClass).nbMethods=res.get(sourceClass).methods.size();
		res.get(targetClass).nbMethods=res.get(targetClass).methods.size();
		
		res.get(sourceClass).callIn = Prog.callIn(res.get(sourceClass));
		res.get(targetClass).callIn = Prog.callIn(res.get(targetClass));
		
		res.get(sourceClass).callOut = Prog.callOut(res.get(sourceClass));
		res.get(targetClass).callOut = Prog.callOut(res.get(targetClass));
		
		res.get(sourceClass).cohesion=Prog.cohesion(res.get(sourceClass));
		res.get(targetClass).cohesion=Prog.cohesion(res.get(targetClass));
		
		return res;
	}

		public static HashMap<SootClass, classDescription> pushDownMethod(HashMap<SootClass, classDescription> classes, SootClass sourceClass, SootClass targetClass, SootMethod method){
		
		HashMap<SootClass, classDescription> res = new HashMap<SootClass, classDescription>();
		res.putAll(classes);
		
		method.setDeclaringClass(targetClass);
		
		res.get(sourceClass).methods.remove(method);
		res.get(targetClass).methods.add(method);
		
		res.get(sourceClass).nbMethods=res.get(sourceClass).methods.size();
		res.get(targetClass).nbMethods=res.get(targetClass).methods.size();
		
		res.get(sourceClass).callIn = Prog.callIn(res.get(sourceClass));
		res.get(targetClass).callIn = Prog.callIn(res.get(targetClass));
		
		res.get(sourceClass).callOut = Prog.callOut(res.get(sourceClass));
		res.get(targetClass).callOut = Prog.callOut(res.get(targetClass));
		
		res.get(sourceClass).cohesion=Prog.cohesion(res.get(sourceClass));
		res.get(targetClass).cohesion=Prog.cohesion(res.get(targetClass));
		
		return res;
	}
}

class ExtractClass{
	static String refDescription;
	static HashMap<SootClass, classDescription> output;
	
	public ExtractClass(HashMap<SootClass, classDescription> classes, classDescription cDesc){
		HashMap<SootMethod, HashMap<SootField, Double>> matrixMF = new HashMap<SootMethod, HashMap<SootField, Double>>();
		HashMap<SootMethod, HashMap<SootMethod, Double>> matrixMM = new HashMap<SootMethod, HashMap<SootMethod, Double>>();
		/** to save similarity values method-field*/
		/*HashMap<SootField, Double> tmp = new HashMap<SootField, Double>();
		/** to save similarity values method-method*/
		//HashMap<SootMethod, Double> tmp2 = new HashMap<SootMethod, Double>();
		
		// Patrice Comment it out:System.out.println("extract class : "+cDesc.name+" nbFields: "+cDesc.fields.size()+" nbMethods "+cDesc.methods.size());
	/*	for(SootMethod mSource : cDesc.methods){
			//System.out.println("-----------------------------------------------------------------------");
			//System.out.println("<<<<< "+mSource.getName());
			
			tmp = new HashMap<SootField, Double>();
			for(SootField f : cDesc.fields){
				if(Prog.attributRW(cDesc.classe, mSource, f)){
					tmp.put(f, 1.0);
					//System.out.println("field : yes : "+f.getName()+" method : "+mSource.getName()+" class : 1. "+mSource.getDeclaringClass().getName()+"\t 2. "+f.getDeclaringClass().getName());
					
				}
				else{
					tmp.put(f, 0.0);
					//System.out.println("field : no : "+f.getName()+" method : "+mSource.getName());
				}	
			}
			//System.out.println("field tmp maxSize: "+tmp.size());
			matrixMF.put(mSource, tmp);
			
			tmp2 = new HashMap<SootMethod, Double>();
			Iterator it = cDesc.methods.iterator();
			//for(SootMethod mTarget : cDesc.methods){
			while(it.hasNext()){
				SootMethod mTarget=(SootMethod)it.next();
				if(Prog.call(mSource, mTarget) || Prog.call(mTarget, mSource)){
					tmp2.put(mTarget, 1.0);
					//System.out.println("method : yes : "+mTarget.getName()+" method : "+mSource.getName()+" class : 1. "+mSource.getDeclaringClass().getName()+"\t 2. "+mTarget.getDeclaringClass().getName());
				}
				else{
					tmp2.put(mTarget, 0.0);
				}
			}
			//System.out.println("method tmp2 maxSize: "+tmp2.size());
			matrixMM.put(mSource, tmp2);
		}
		//chercher la ligne maximale dans la martrice
		double max=0;
		SootMethod maxLine=cDesc.methods.get(0);
		for(SootMethod mSource : cDesc.methods){
			HashMap<SootField, Double> listF = matrixMF.get(mSource);
			HashMap<SootMethod, Double> listM = matrixMM.get(mSource);
						
			double overageF=0;
			double overageM=0;
			//System.out.println(mSource.getName());
			for(Double d : listF.values()){
				overageF+=d;
			}
			overageF=overageF/listF.values().size();
			
			for(Double d : listM.values()){
				overageM+=d;
			}
			
			overageM=overageM/listM.values().size();
			
			if(((overageF+overageM)/2)>max){
				max=(overageF+overageM)/2;
				maxLine=mSource;
			}
		}
		//System.out.println("max_line: "+maxLine.getName()+"\t max_overage: "+max);
		
		String s="-------------------------------------------------------------------\n";
		s+="extract Class: \n";
		s+="\tsource class: "+cDesc.name+"\n";
		
		SootClass c = new SootClass("extracted"+cDesc.name);
		s+="\tfields to move: ";
		for(SootField f : matrixMF.get(maxLine).keySet()){
			if(matrixMF.get(maxLine).get(f)>0.5)
			s+=f.getName()+" ";
		}
		s+="\n\tmethods to move: ";
		s+=maxLine.getName()+" ";
		for(SootMethod m : matrixMM.get(maxLine).keySet()){
			if(matrixMM.get(maxLine).get(m)>0.5)
			s+=m.getName()+" ";
		}
		s+="\n\tsimilarity/cohesion: "+max;
		

		//HashMap<SootClass, classDescription> output = new HashMap<SootClass, classDescription>();
		for(SootField f : matrixMF.get(maxLine).keySet()){
			classes.get(cDesc.classe).fields.remove(f);
			classes.get(cDesc.classe).nbFields = classes.get(cDesc.classe).fields.size();
			classes.get(cDesc.classe).attributRW=Prog._attributRW(classes.get(cDesc.classe));
			classes.get(cDesc.classe).Assoc=Prog.getAssoc(classes.get(cDesc.classe));			
			classes.get(cDesc.classe).nbAssoc = classes.get(cDesc.classe).Assoc.size();
		}
		for(SootMethod m : matrixMM.get(maxLine).keySet()){
			classes.get(cDesc.classe).methods.remove(m);
			classes.get(cDesc.classe).nbMethods=classes.get(cDesc.classe).methods.size();
			classes.get(cDesc.classe).callIn = Prog.callIn(classes.get(cDesc.classe));
			classes.get(cDesc.classe).callOut = Prog.callOut(classes.get(cDesc.classe));
			classes.get(cDesc.classe).cohesion=Prog.cohesion(classes.get(cDesc.classe));
		}
		refDescription=s;
		output=classes;

		//System.out.println(s);
	}
	
	}*/

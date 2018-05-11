package execute;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;

import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;



public class FitnessFunction {
	public static List<SootClass> exampleBase = new ArrayList<SootClass>();
	
	public static double semantic(HashMap<SootClass, classDescription> classes, SootClass class1, SootClass class2){
		return semanticFitnessFunction.semantic(classes, class1, class2);
	}
	
	public static double quality(HashMap<SootClass, classDescription> classes){
		return qualityFitnessFunction.quality(classes);
	}
	
	public static <T> List<T> intersection(List<T> list1, List<T> list2) {
	    List<T> list = new ArrayList<T>();
	
	    for (T t : list1) {
	        if(list2.contains(t)) 
	            list.add(t);
	    }
	    return list;
	}
	
	//What does this function does?
	public static double round(double val, int n) {
		return (double)( (int)(val * Math.pow(10,n) + 0.5) ) / Math.pow(10,n);
	}
}

class qualityFitnessFunction{
		public static List<String> ruleBlob=new ArrayList<String>();
		public static List<String> ruleSC=new ArrayList<String>();
		public static List<String> ruleFD=new ArrayList<String>();
		
		public static List<String> _exampleBase= new ArrayList<String>();
		
		public static void init(){
			classDescription cDesc;
			Set<SootClass> classes=Prog.classesDD.keySet();
			
			_exampleBase.add("org.apache.xerces.impl.xs.traversers.XSDHandler");
			_exampleBase.add("org.apache.xerces.impl.XMLEntityManager");
			_exampleBase.add("org.apache.xerces.dom.DOMNormalizer");
			_exampleBase.add("org.apache.xerces.xni.grammars.Grammar");
			_exampleBase.add("org.apache.xerces.parsers.XML11NonValidatingConfiguration");
			_exampleBase.add("org.apache.xerces.impl.dtd.XMLDTDValidator");
			_exampleBase.add("org.apache.xerces.parsers.XML11DTDConfiguration");
			_exampleBase.add("org.apache.xerces.xinclude.XIncludeHandler");
			_exampleBase.add("org.apache.xerces.parsers.NonValidatingConfiguration");
			_exampleBase.add("org.apache.xerces.impl.xs.XMLSchemaValidator");
			_exampleBase.add("org.apache.xerces.parsers.DTDConfiguration");
			_exampleBase.add("org.apache.xerces.dom.CoreDocumentImpl");
			_exampleBase.add("org.apache.xerces.impl.xs.traversers.XSAttributeChecker");
			_exampleBase.add("org.apache.xerces.impl.dv.XSFacets");
			_exampleBase.add("org.apache.xerces.impl.XMLVersionDetector");
			_exampleBase.add("org.apache.xerces.impl.xs.traversers.XSDHandler");
			_exampleBase.add("org.apache.xerces.impl.xs.traversers.XSDAbstractTraverser");
			_exampleBase.add("org.apache.xerces.impl.dtd.XML11DTDValidator");
			_exampleBase.add("org.apache.xerces.impl.dtd.XMLNSDTDValidator");
			_exampleBase.add("org.apache.xerces.util.ParserConfigurationSettings");
			_exampleBase.add("org.apache.xerces.parsers.SAXParser");
			_exampleBase.add("org.apache.xerces.impl.xs.opti.SchemaDOM");
			_exampleBase.add("org.apache.xerces.impl.xpath.regex.RegexParser");
			_exampleBase.add("org.apache.xerces.impl.dv.XSFacets");
			_exampleBase.add("org.apache.xerces.xpointer.ElementSchemePointer");
			_exampleBase.add("org.apache.xerces.parsers.XMLParser");
			_exampleBase.add("org.apache.xerces.impl.dtd.models.DFAContentModel");
			_exampleBase.add("org.apache.xml.serialize.XMLSerializer");
			_exampleBase.add("org.apache.xerces.impl.XML11EntityScanner");
			_exampleBase.add("org.apache.xerces.impl.xpath.regex.Token");
			_exampleBase.add("org.apache.xerces.impl.XMLEntityScanner");
			_exampleBase.add("org.apache.xerces.impl.xs.XSAttributeGroupDecl");
			_exampleBase.add("org.apache.xerces.parsers.AbstractDOMParser");
			_exampleBase.add("org.apache.xerces.impl.xs.traversers.XSDAttributeTraverser");
			_exampleBase.add("org.apache.xerces.xinclude.ObjectFactory");
			_exampleBase.add("org.apache.xerces.impl.xs.models.XSDFACM");
			_exampleBase.add("org.apache.xerces.dom.CoreDocumentImpl");
			_exampleBase.add("org.apache.xerces.parsers.XML11Configuration");
			_exampleBase.add("org.apache.xml.serialize.DOMSerializerImpl");
			_exampleBase.add("org.apache.xerces.impl.xs.identity.XPathMatcher");
			
			for(SootClass cl : classes){
				cDesc = Prog.classesDD.get(cl);
				
				if(		cDesc.name.equals(_exampleBase.get(0)) || cDesc.name.equals(_exampleBase.get(1)) || cDesc.name.equals(_exampleBase.get(2)) ||
						cDesc.name.equals(_exampleBase.get(3)) || cDesc.name.equals(_exampleBase.get(4)) || cDesc.name.equals(_exampleBase.get(5)) ||
						cDesc.name.equals(_exampleBase.get(6)) || cDesc.name.equals(_exampleBase.get(7)) || cDesc.name.equals(_exampleBase.get(8)) ||
						cDesc.name.equals(_exampleBase.get(9)) || cDesc.name.equals(_exampleBase.get(10)) || cDesc.name.equals(_exampleBase.get(11)) ||
						cDesc.name.equals(_exampleBase.get(12)) || cDesc.name.equals(_exampleBase.get(13)) || cDesc.name.equals(_exampleBase.get(14)) ||
						cDesc.name.equals(_exampleBase.get(14)) || cDesc.name.equals(_exampleBase.get(15)) || cDesc.name.equals(_exampleBase.get(16)) ||
						cDesc.name.equals(_exampleBase.get(17)) || cDesc.name.equals(_exampleBase.get(18)) || cDesc.name.equals(_exampleBase.get(19)) ||
						cDesc.name.equals(_exampleBase.get(20)) || cDesc.name.equals(_exampleBase.get(21)) || cDesc.name.equals(_exampleBase.get(22)) ||
						cDesc.name.equals(_exampleBase.get(23)) || cDesc.name.equals(_exampleBase.get(24)) || cDesc.name.equals(_exampleBase.get(25)) ||
						cDesc.name.equals(_exampleBase.get(26)) || cDesc.name.equals(_exampleBase.get(27)) || cDesc.name.equals(_exampleBase.get(28)) ||
						cDesc.name.equals(_exampleBase.get(27)) || cDesc.name.equals(_exampleBase.get(13)) || cDesc.name.equals(_exampleBase.get(14)) ||
						cDesc.name.equals(_exampleBase.get(29)) || cDesc.name.equals(_exampleBase.get(30)) || cDesc.name.equals(_exampleBase.get(31)) ||
						cDesc.name.equals(_exampleBase.get(32)) || cDesc.name.equals(_exampleBase.get(33)) || cDesc.name.equals(_exampleBase.get(34)) ||
						cDesc.name.equals(_exampleBase.get(35)) || cDesc.name.equals(_exampleBase.get(36)) || cDesc.name.equals(_exampleBase.get(37)) ||
						cDesc.name.equals(_exampleBase.get(38)) || cDesc.name.equals(_exampleBase.get(39))) {
					FitnessFunction.exampleBase.add(cl);
				}
			}
			
		}
		
		public static double quality(HashMap<SootClass, classDescription> classes){
			init();
			double res=0;
			Set<SootClass> cl = classes.keySet();
			List<String> detected=new ArrayList<String>();
			classDescription cDesc;
			
			for(SootClass c : cl){
				cDesc=classes.get(c);
				//detection des blob
				if(cDesc.methods.size()>55 && cDesc.callOut.size()<29 && cDesc.nbSubClasses<7 && cDesc.fields.size()>18  && !detected.contains(c))
					detected.add(c.getName());
				
				//detection des Spaghetti code
				if(cDesc.cohesion.size()>56 && cDesc.nbSubClasses>3 && cDesc.nbsuperClasses<7 && cDesc.methods.size()>10 && !detected.contains(c))
					detected.add(c.getName());
				
				//detection de FD
				if(cDesc.methods.size()<93 && cDesc.nbSubClasses>14 && cDesc.callIn.size()<29 && !detected.contains(c))
					detected.add(c.getName());
			}
			
			// Commented by Patrice: System.out.println("nb Corrected : "+getNumberCorrectedDefects(_exampleBase, detected)+ " precision : "+Precision(_exampleBase, detected)+" recall : "+Recall(_exampleBase, detected));
			return FitnessFunction.round((double) ((Precision(_exampleBase, detected) + Recall(_exampleBase, detected))/2), 3);
		}
		
		public static double Precision(List<String> examples, List detected){
			
			int common = FitnessFunction.intersection(examples, detected).size();
			
			return 	(double)common/detected.size();
		}
		
		public static double Recall(List<String> examples, List detected){
			
			int common = FitnessFunction.intersection(examples, detected).size();
			
			return 	(double)common/examples.size();
		}
		
		public static double getNumberCorrectedDefects(List<String> examples, List detected){
			
			return 	(double)examples.size() - FitnessFunction.intersection(examples, detected).size();
		}
}

class semanticFitnessFunction{
	
	//public static Matrix ssMatrix;
	protected static double alpha=0.4;
	protected static double beta=0.3;
	protected static double gamma=0.3;
	
	public static double semantic(HashMap<SootClass, classDescription> classes, SootClass class1, SootClass class2){
		double sem=0;
		
		sem=alpha*cosine_similarity(classes,class1,class2) + beta*sharedMethods(classes, class1, class2) + gamma*sharedAttributesRW(classes, class1, class2);
		sem=FitnessFunction.round(sem, 3);
		return sem;
	}
	
	public static double cosine_similarity(HashMap<SootClass, classDescription> classes, SootClass class1, SootClass class2){
		double res=0;
		if(class1.equals(class2))
			res=1;
		else{
			Map<String, Double> c1=new HashMap<String, Double>();
			Map<String, Double> c2=new HashMap<String, Double>();
			
			//calculate Term Frequency tf for each class
			Iterator it=classes.get(class1).vocabulary.iterator();
			while(it.hasNext()){
				String s=(String)it.next();
				if(!c1.containsKey(s))
					c1.put(s, 1.0);
				else
					c1.put(s, c1.get(s)+1);
			}
			
			it=classes.get(class2).vocabulary.iterator();
			while(it.hasNext()){
				String s=(String)it.next();
				if(!c2.containsKey(s))
					c2.put(s, 1.0);
				else
					c2.put(s, c2.get(s)+1);
			}	
			res = FitnessFunction.round((compute_cosine(c1, c2)+compute_cosine(c2, c1))/2, 3);
		}
		return res;
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
	
	public static double sharedMethods(HashMap<SootClass, classDescription> classes, SootClass class1, SootClass class2){
		double val=0;
		if(class1.equals(class2))
			val=1;
		else{
			List<String> c1=new ArrayList();
			List<String> c2=new ArrayList();
					
			Iterator it=classes.get(class1).methods.iterator();
			while(it.hasNext()){
				String mth=((SootMethod)it.next()).getName();
				if(!c1.contains(mth) && !mth.equals("<init>") && !mth.equals("<clinit>"))
					c1.add(mth);				
			}
			it=classes.get(class2).methods.iterator();
			while(it.hasNext()){
				String mth=((SootMethod)it.next()).getName().toString();
				if(!c2.contains(mth) && !mth.equals("<init>") && !mth.equals("<clinit>"))
					c2.add(mth);
			}
			List<String> intersection = FitnessFunction.intersection(c1,c2);
			if(intersection.size()!=0 && c1.size()+c2.size()!=0)
				val = (double)(intersection.size()*2)/(c1.size()+c2.size());
			else
				val = 0;
		}
		return val;
	}
	
	public static double sharedAttributesRW(HashMap<SootClass, classDescription> classes, SootClass class1, SootClass class2){
		double val=0;
		if(class1.equals(class2))
			val=1;
		else{
			List<String> c1=new ArrayList();
			List<String> c2=new ArrayList();
					
			Iterator it=classes.get(class1).attributRW.iterator();
			while(it.hasNext()){
				String att=((SootClass)it.next()).getName();
				if(!c1.contains(att))
					c1.add(att);
			}
			it=classes.get(class2).attributRW.iterator();
			while(it.hasNext()){
				String att=((SootClass)it.next()).getName();
				if(!c2.contains(att))
					c2.add(att);				
			}
			
			List<String> intersection = FitnessFunction.intersection(c1,c2);
					
			if(intersection.size()!=0 && c1.size()+c2.size()!=0)
				val = (double)(intersection.size()*2)/(c1.size()+c2.size());
			else
				val = 0;
		}
		return val;
		
	}

	public static HashMap<SootClass, Double> getSematicSimilarityList(HashMap<SootClass, classDescription> classes, SootClass cl){
		HashMap<SootClass, Double> sem = new HashMap<SootClass, Double>();
		Set<SootClass> liste=classes.keySet();
		
		for(SootClass c : liste){
			sem.put(c, FitnessFunction.semantic(classes, cl, c));
		}
		return sem;
	}
	
	public static void writeSimilarityMatrix(HashMap<SootClass, classDescription> classes, String file){
		HashMap<SootClass, Double> sem = new HashMap<SootClass, Double>();
		Set<SootClass> liste=classes.keySet();
		
		try{
			FileWriter f = new FileWriter(file);	

			f.write("classe;");
			for (SootClass c : liste) 
				f.write(c.getName()+"\t");
			f.write("\n");
			
			for (SootClass c : liste) {
				f.write(c.getName()+"\t");
				HashMap<SootClass, Double> val = classes.get(c).semantic;
				Set<SootClass> v = val.keySet();
				for (SootClass y : v)	{
					f.write(val.get(y)+"\t");
				}
				f.write("\n");
			}
			f.close();
		}
		catch (Exception e){System.out.println("Error to write Matrix");}
		System.out.println("Matrix written succesfully");
	}
	
}
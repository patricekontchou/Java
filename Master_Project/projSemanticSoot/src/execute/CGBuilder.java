package execute;
//package SootCalculateMatrix;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.io.IOException;

import execute.iceberg.util.Strings;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.spark.SparkTransformer;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;


public class CGBuilder {
	
	public static String compiledcodeFolder="bin";
	public static String SourcePath ="C:\\Users\\P.Kontchou\\Desktop\\Master\\AntApache\\bin";
	public static String jarLibFolder="C:\\Users\\P.Kontchou\\Desktop\\Master\\AntApache\\lib";
	public static String mainClass="org.apache.tools.ant.Main";
	public static String JDK_HOME="C:\\Program Files\\Java\\jdk1.7.0_40\\";

	
	public ArrayList<SootClass> systemClasses = new ArrayList<SootClass>();
	
	
	public static void init() {
		soot.options.Options.v().set_whole_program(true);
		
		soot.options.Options.v().setPhaseOption("cg","verbose:true");
	}

	
	public static CallGraph BuildCallGraph()  {
		
		CallGraph cg;
		init();
		System.out.println("Setting classpath");
		addJarFrom(jarLibFolder);
		
	    System.out.println("Setting main class");
		
		setMainClass(mainClass);
		//loadMain();
				
		System.out.println("Building CG");
		cg=buildCG();
		
	    return cg;
	}
	
	public static void setMainClass(String className) {
		SootClass mainClass = Scene.v().loadClassAndSupport(className);
		mainClass.setApplicationClass();
		Scene.v().setMainClass(mainClass);
		List<SootMethod> ep = Scene.v().getEntryPoints();
		ep.add(Scene.v().getMainMethod());
		System.out.println("Execute Class: "+Scene.v().getMainMethod());
		Scene.v().setEntryPoints(ep);
	}
	
	
	public static CallGraph buildCG() {
		
		Scene.v().loadNecessaryClasses();
    	//Scene.v().loadBasicClasses();
		CHATransformer.v().transform();
	
		System.out.println("public static CallGraph buildCG(): Type CHA");
		
		return Scene.v().getCallGraph();
	}
	
	
	private static void loadMainClass(String classe){
		SootClass mainClass = Scene.v().loadClassAndSupport(classe);
		mainClass.setApplicationClass();
		Scene.v().setMainClass(mainClass);
		System.out.println(classe);
		List<SootMethod> ep = Scene.v().getEntryPoints();
		ep.add(Scene.v().getMainMethod());
		Scene.v().setEntryPoints(ep);
	}
	
	public static void  loadMain(){
		for (String cl : Tools.getClass(SourcePath)) {
			
	try {
			//System.out.println("pt :"+cl);
			loadMainClass(cl);
	    } catch (Exception e) {}	
       
	    System.out.println("Lodgae des class main");
		}
	}
	
	// Get list of folder from a directory
	public static void addJarFrom(String rep){
		String JDK_HOME = CGBuilder.JDK_HOME;
		
		List<String> lst=new ArrayList<String>();
		lst.add(CGBuilder.compiledcodeFolder);
		lst.add(JDK_HOME + "jre\\lib\\rt.jar");
		lst.add(JDK_HOME + "jre\\lib\\jce.jar");
		lst.add(JDK_HOME + "jre\\lib\\javaws.jar");
		lst.add(JDK_HOME + "jre\\lib\\jsse.jar");
				
		File reper = new File(rep);
		String [] listefichiers;
		
		String jarFolder=rep.substring(rep.lastIndexOf("\\")+1);
		System.out.println(jarFolder);
		
		listefichiers=reper.list();
		for(int i=0;i<listefichiers.length;i++){
			if(listefichiers[i].endsWith(".jar")==true){ 
				lst.add(jarFolder+"\\"+listefichiers[i]);	
				System.out.println(jarFolder+"\\"+listefichiers[i]);
			}
		}
		
		String[] cp =new String[lst.size()];
		int i=0;
		Iterator it=lst.iterator();
		while(it.hasNext()){
			cp[i]=(String)it.next();
			i++;
		}
				
		Scene.v().setSootClassPath(Strings.join(cp, ";"));
	}
	
	static void setSparkPointsToAnalysis(String ta) {
		System.out.println("[spark] Starting analysis ...");
		HashMap opt = new HashMap();
		opt.put("on-fly-cg","false");   //cg   		
		opt.put("vta","false");     //cg              
		opt.put("rta","false");     //cg  
		
		if(ta == "ofc")  
			
			opt.put("on-fly-cg","true");
		else
			opt.put(ta,"true");
		
		opt.put("enabled","true");
		opt.put("verbose","true");
		opt.put("ignore-types","false");      //cg    
		opt.put("force-gc","true");            
		opt.put("pre-jimplify","false");          
		opt.put("field-based","false");  //cg        
		opt.put("types-for-sites","false");        
		opt.put("merge-stringbuffer","true");   
		opt.put("string-constants","false");     
		opt.put("simulate-natives","true");      
		opt.put("simple-edges-bidirectional","false");
		opt.put("simplify-offline","false");    
		opt.put("simplify-sccs","false");        
		opt.put("ignore-types-for-sccs","false");
		opt.put("propagator","worklist"); //worklist iter none
		opt.put("set-impl","double");
		opt.put("double-set-old","hybrid");         
		opt.put("double-set-new","hybrid");
		opt.put("dump-html","false");           
		opt.put("dump-pag","false");             
		opt.put("dump-solution","false");        
		opt.put("topo-sort","false");           
		opt.put("dump-types","true");             
		opt.put("class-method-var","true");     
		opt.put("dump-answer","false");          
		opt.put("add-tags","false");             
		opt.put("set-mass","false"); 		
		
		SparkTransformer.v().transform("",opt);
	}

}

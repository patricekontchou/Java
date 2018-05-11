package execute;

import soot.*;
import soot.jimple.spark.SparkTransformer;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.CallGraphBuilder;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.OneCFAContextManager;
import soot.jimple.toolkits.callgraph.Sources;
import soot.jimple.toolkits.callgraph.Targets;
import soot.options.Options;
import soot.util.queue.QueueReader;

import java.io.ObjectInputStream.GetField;
import java.util.Scanner;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;



public class Execution {
	
    public static int population_size = 10 ;
    public static int MIN_NUM_REFACTORING = 1;
    public static int MAX_NUM_REFACTORING = 10;
    public static int MIN_REFACTORING = 0;
    public static int MAX_REFACTORING = 3;
    public static int OBJECTIVENUMBER = 6;
    public static int generations = 2 ;
    static int division = 3;
    public static boolean print_executed_refactorings = false ;
    public static CallGraph cg;
	
	
	public static void main(String[] args) {
		
		cg=CGBuilder.BuildCallGraph();
	
		System.out.println("on a un call graph cg");
		//System.out.println(CGBuilder.SourcePath);
		//try{
		ArrayList<String> list = new ArrayList<String>();
		list = Tools.getClass(CGBuilder.SourcePath);
		Prog l = new Prog(cg,list);
					
		System.out.println("Call Graph Builder");
		
		
		initSemantic();
		
		System.out.println("______");
		System.out.println("maxSize: "+Prog.classesDD.size());
		System.out.println("classDescription size: " + Prog.classesD.size());
		System.out.println("folder  size: " + list.size());
		
		
		//for (SootClass c : Scene.v().getApplicationClasses()) {
			//System.out.println("Application classes: "+c.getName());
		//}
		System.out.println("GetapplicationClass" + Scene.v().getApplicationClasses().size());
		System.out.println("GetLibrary " + Scene.v().getLibraryClasses().size());
		System.out.println("GetClassSize " + Scene.v().getClasses().size());	
						
		//Solution ps = new Solution();
		//ps.print_solution();
                
                Population p = new Population(population_size,generations); 
                ArrayList<Solution> parents = new ArrayList<Solution>();
                p.create_poplulation();
                p.evaluate_poplulation(0);
                p.print_popluation();
               // parents = p.random_selection();
                
                for(int i=0; i<generations; i++)
                {
                    parents = p.random_selection(); 
                    System.out.println("This is the generation "+ i +"\n");
                    p.generate_next_popluation(parents,i);
                }
                
                
	}
	
	public static void initSemantic(){
		int i=0;
		for(SootClass c : Prog.classesDD.keySet()){
			i++;
			Prog.classesDD.get(c).semantic.putAll(semanticFitnessFunction.getSematicSimilarityList(Prog.classesDD, c));
			
		}
		
			
				
		semanticFitnessFunction.writeSimilarityMatrix(Prog.classesDD, "D:\\Dropbox\\Workspace\\ProjSemanticSoot\\sssssssss.xls");
	}
}


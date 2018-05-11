package execute;

/*
 * This class implements all refactorings that can be applied on a package level. A solution will be a set of refactorings  
 * operations randomly generated. 
 */

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import soot.SootClass;


public  class  PackageRefactoring{
	
	static double cosineSimilarity;		
	static String refSequence;
	/*
	 * Create a new package, add it to the hash table and add it the  refactored package hash table.
	 */
	 		
public static ArrayList<packageDescription> createPackage(ArrayList<packageDescription> packageRef, String packageName){
		
		refSequence = null;
			ArrayList<packageDescription> res = new ArrayList<packageDescription>();
			res.addAll(packageRef);
			packageDescription newPackage = new packageDescription(packageName);
			res.add(newPackage);
			refSequence = (" createPackage "+ packageName+".");
			
			return res;
			
			}
	
	
	
public static ArrayList<packageDescription> splitPackage(ArrayList<packageDescription> packageRef){
	
	//Randomly generate two packages for the operation
	ArrayList<packageDescription> res = new ArrayList<packageDescription>();
	res.addAll(packageRef);
	int srcPckIndex = 0, trgPackIndex = 0;
	cosineSimilarity = 0;
	refSequence = null;
	int correct = 1;
	int correct1 = 1;
	
	packageDescription sourcePackage = Random.randomPackage(res);
	do {
		if(sourcePackage.pClasses.size() <2)
		    sourcePackage = Random.randomPackage(res); 
		else correct = 0;
	}while(correct == 1);
	
	srcPckIndex = res.indexOf(sourcePackage);
	packageDescription targetPackage = Random.randomPackage(res); 
	trgPackIndex = res.indexOf(targetPackage);
	int numberOfClasses = 0, copyNumberOfClasses = 0;
	String SliptList = new String();
	int min = 0;
	int max = sourcePackage.pClasses.size()-1;
		
		numberOfClasses = Random.random(min, max);
		do {
			if(numberOfClasses <1)
				numberOfClasses = Random.random(min, max); 
			else correct1 = 0;
		}while(correct == 1);
		copyNumberOfClasses = numberOfClasses;
	
	while(numberOfClasses>0){
	        	
	        SootClass s = Random.randomClass(sourcePackage.pClasses);
		    targetPackage.pClasses.add(s);
		    SliptList += ("\n \t \t "+s.getName());
		    sourcePackage.pClasses.remove(s);
		    numberOfClasses--;	
		    
			}
		
		refSequence = ("splitPackage "+ copyNumberOfClasses+ " -Classes:- "+ " from "+ sourcePackage.packageName+ " to "+ targetPackage.packageName+"\n" +SliptList);
		targetPackage.pMethods = targetPackage.getPackageMethod();
		targetPackage.pFields = targetPackage.getPackageField(targetPackage);
		targetPackage.nbFields = targetPackage.getNumberOfField();
		targetPackage.pCohesion = Prog.pCohesion(targetPackage);
		targetPackage.pCallIn = Prog.pCallIn(targetPackage);
		targetPackage.pCallOut = Prog.pCallOut(targetPackage);
		targetPackage.pCoupling = targetPackage.pCoupling;
		targetPackage.pAttributRW=Prog.pAttributRW(targetPackage);
		targetPackage.vocabulary = targetPackage.generateVocabulary(targetPackage);
	
		sourcePackage.pMethods = sourcePackage.getPackageMethod();
		sourcePackage.pFields = sourcePackage.getPackageField(sourcePackage);
		sourcePackage.nbFields = sourcePackage.getNumberOfField();
		sourcePackage.pCohesion = Prog.pCohesion(sourcePackage);
		sourcePackage.pCallIn = Prog.pCallIn(sourcePackage);
		sourcePackage.pCallOut = Prog.pCallOut(sourcePackage);
		sourcePackage.pCoupling = sourcePackage.pCoupling;
		sourcePackage.pAttributRW=Prog.pAttributRW(sourcePackage);
		sourcePackage.vocabulary = sourcePackage.generateVocabulary(sourcePackage);
		
		cosineSimilarity = Prog.pCosine_similarity(sourcePackage, targetPackage);
		
		res.set(srcPckIndex, sourcePackage);
		res.set(trgPackIndex, targetPackage);
	
return res;
}

	
	/*
	 * This Method moved a class from source package to target package and recompute metrics value for the two packages(source and target) 
	 * return the new hashTable with new values.
	 */
public static ArrayList<packageDescription> moveClass( ArrayList<packageDescription> packageRef){
	
	int correct = 1;
	refSequence = null;
	ArrayList<packageDescription> res = new ArrayList<packageDescription>();
	res.addAll(packageRef);
	int srcPckIndex = 0, trgPckIndex = 0;
	cosineSimilarity = 0;
	packageDescription sourcePackage = Random.randomPackage(res); 
	
	do {
		if(sourcePackage.pClasses.size() <2)
		    sourcePackage = Random.randomPackage(res); 
		else correct = 0;
	}while(correct == 1);
	
	srcPckIndex = res.indexOf(sourcePackage);
	packageDescription targetPackage = Random.randomPackage(res);
	trgPckIndex = res.indexOf(targetPackage);
	SootClass movedClass = Random.randomClass(sourcePackage.pClasses);
	targetPackage.pClasses.add(movedClass);
	sourcePackage.pClasses.remove(movedClass); 
	cosineSimilarity = Prog.pCosine_similarity(sourcePackage, targetPackage);
	refSequence = ("moveClass: "+movedClass.getName()+ " from "+sourcePackage.packageName+ " to "+ targetPackage.packageName+".");
	
	targetPackage.pMethods = targetPackage.getPackageMethod();
	targetPackage.pFields = targetPackage.getPackageField(targetPackage);
	targetPackage.nbFields = targetPackage.getNumberOfField();
	targetPackage.pCohesion = Prog.pCohesion(targetPackage);
	targetPackage.pCallIn = Prog.pCallIn(targetPackage);
	targetPackage.pCallOut = Prog.pCallOut(targetPackage);
	targetPackage.pCoupling = targetPackage.pCoupling;
	targetPackage.pAttributRW=Prog.pAttributRW(targetPackage);
	targetPackage.vocabulary = targetPackage.generateVocabulary(targetPackage);

	sourcePackage.pMethods = sourcePackage.getPackageMethod();
	sourcePackage.pFields = sourcePackage.getPackageField(sourcePackage);
	sourcePackage.nbFields = sourcePackage.getNumberOfField();
	sourcePackage.pCohesion = Prog.pCohesion(sourcePackage);
	sourcePackage.pCallIn = Prog.pCallIn(sourcePackage);
	sourcePackage.pCallOut = Prog.pCallOut(sourcePackage);
	sourcePackage.pCoupling = sourcePackage.pCoupling;
	sourcePackage.pAttributRW=Prog.pAttributRW(sourcePackage);
	sourcePackage.vocabulary = sourcePackage.generateVocabulary(sourcePackage);

	res.set(srcPckIndex, sourcePackage);
	res.set(trgPckIndex, targetPackage);
	
	return res;
}

	/*
	 * This Method merges two packages: sourcePackage is the package to be merged and targetPackage is the package 
	 * where we want to merge sourcePackage in. After merging the package, we recompute all metrics value of the
	 * and we put the package back in the HashMap table that we return and we remove sourcePackage from the system.
	 */
public  static ArrayList<packageDescription>  mergePackage(ArrayList<packageDescription> packageRef){
	
	//Randomly generate two packages for the operation
	ArrayList<packageDescription> res = new ArrayList<packageDescription>();
	res.addAll(packageRef);
	cosineSimilarity = 0;
	refSequence = null;
	packageDescription sourcePackage = Random.randomPackage(res);
	packageDescription targetPackage = Random.randomPackage(res);
	
	res.remove(sourcePackage);
	res.remove(targetPackage);
	
	for(SootClass c: sourcePackage.pClasses ){
		
		if(!targetPackage.pClasses.contains(c))
			targetPackage.pClasses.add(c);
	}

	refSequence = ("mergePackage: "+sourcePackage.packageName+ " to "+ targetPackage.packageName+".");

	targetPackage.pMethods = targetPackage.getPackageMethod();
	targetPackage.pFields = targetPackage.getPackageField(targetPackage);
	targetPackage.nbFields = targetPackage.getNumberOfField();
	targetPackage.pCohesion = Prog.pCohesion(targetPackage);
	targetPackage.pCallIn = Prog.pCallIn(targetPackage);
	targetPackage.pCallOut = Prog.pCallOut(targetPackage);
	targetPackage.pCoupling = targetPackage.pCoupling;
	targetPackage.pAttributRW=Prog.pAttributRW(targetPackage);
	targetPackage.vocabulary = targetPackage.generateVocabulary(targetPackage);
	
	cosineSimilarity = Prog.pCosine_similarity(sourcePackage, targetPackage);
	
	res.add(targetPackage);
	
	return res;	
 }
		
}
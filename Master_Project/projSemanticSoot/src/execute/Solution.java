 package execute;

/*
 * This class implements solution class. A population will be a collection of many solution and a solution object contains number of refactings 
 * which is determined randomly between min and max value. a solution contains an array of refactoring that should be applied on a solution instance
 * and these refactoring are generated randomly. A solution accepts as parameter a list of class description. 
 */


import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import soot.SootClass;
import soot.SootMethod;
import soot.SootField;

import java.util.Set;


public class Solution 
{
			int MIN_NUM_REFACTORING = 1;
			int MAX_NUM_REFACTORING = 10;
			int MIN_REFACTORING = 1;
			int MAX_REFACTORING = 4;
			int OBJECTIVENUMBER = 6;
            public static boolean print_executed_refactorings = true ;
                        
			int newPackageId;
			double SolCohesion;
			double SolCoupling;
			double classPerPackage;
			double numberOfPackage;
			double cosineSimilarity;
			
			ArrayList<String> solutionSequence;
			ArrayList<Integer> Refactorings; 
			ArrayList<Double> objectives; 
			ArrayList<Double> objectivesCopy;
            ArrayList<String> objectives_names ;
			Double numberOfRefactorings;
			ArrayList<packageDescription> copyOfFile;
                        
                        int rank ;
                        double dominance ;
                        double distance ;
                        double crowding_distance;
    
                        ArrayList<Double> ref_lines_distance ;
                        ArrayList<Integer> ref_lines_index ;
                        int associated_ref_point_index ;
                        
                        //list of candiate refactoring operations to apply
                        public static String[] refactoring={"Create Package","Merge Package","Move Class","Split package"};
                        
			
			public Solution()
                        {
				
				
                            MIN_NUM_REFACTORING = Execution.MIN_NUM_REFACTORING;
                            MAX_NUM_REFACTORING = Execution.MAX_NUM_REFACTORING;
                            MIN_REFACTORING = Execution.MIN_REFACTORING;
                            MAX_REFACTORING = Execution.MAX_REFACTORING;
                            OBJECTIVENUMBER = Execution.OBJECTIVENUMBER;
                            print_executed_refactorings = Execution.print_executed_refactorings ;
                            
                            numberOfRefactorings = 0.0;
                            this.Refactorings = new ArrayList<Integer>();
                numberOfRefactorings = getNumberOfRefactorings(MIN_NUM_REFACTORING, MAX_NUM_REFACTORING);
                Refactorings = getRefactorings(MIN_REFACTORING, MAX_REFACTORING);
				
				newPackageId = 0;
				copyOfFile =  new ArrayList<packageDescription> (Prog.packageD);
				solutionSequence = new ArrayList<String>();
				SolCohesion = 0;
				SolCoupling = 0;
				classPerPackage = 0;
				numberOfPackage = 0;
				cosineSimilarity = 0;
                                rank = 0 ;
				objectives = new ArrayList<Double>();
				objectivesCopy = new ArrayList<Double>();
				
                                objectives_names = new ArrayList<String>();
                                dominance =0;
                                distance =0;
                                crowding_distance =0;
                                ref_lines_distance = new ArrayList<Double>();
                                ref_lines_index = new ArrayList<Integer>();
                                associated_ref_point_index = 0 ;
			}
                        
                        public Solution(Solution s)
                        {
				
				
                            MIN_NUM_REFACTORING = Execution.MIN_NUM_REFACTORING;
                            MAX_NUM_REFACTORING = Execution.MAX_NUM_REFACTORING;
                            MIN_REFACTORING = Execution.MIN_REFACTORING;
                            MAX_REFACTORING = Execution.MAX_REFACTORING;
                            OBJECTIVENUMBER = Execution.OBJECTIVENUMBER;
                            print_executed_refactorings = Execution.print_executed_refactorings ;
                            
                           // numberOfRefactorings = new Double(0);
                            numberOfRefactorings=s.numberOfRefactorings;
				this.Refactorings = new ArrayList<Integer>();
								for(int i=0;i<s.Refactorings.size();i++)
								{
									this.Refactorings.add(s.Refactorings.get(i));
								}
								numberOfRefactorings = Double.valueOf(this.Refactorings.size());
								//System.out.println("number of refactorings at the second constructor "+ this.Refactorings.size()+ "number above it "+ this.numberOfRefactorings);
				solutionSequence = new ArrayList<String>();
				
				this.newPackageId = s.newPackageId;
				this.copyOfFile =  new ArrayList<packageDescription>();
				for (int i = 0; i< Prog.packageD.size(); i++) 
					{
					this.copyOfFile.add(Prog.packageD.get(i));
					}
				this.SolCohesion = s.SolCohesion;
				this.SolCoupling = s.SolCoupling;
				this.classPerPackage = s.classPerPackage;
				this.numberOfPackage = s.numberOfPackage;
				this.cosineSimilarity = s.cosineSimilarity;
                                rank = s.rank ;
                                this.objectives = new ArrayList<Double>();
                                for(int i=0;i<s.objectives.size();i++)
                                {
                                    this.objectives.add(s.objectives.get(i));
                                }
                                this.objectivesCopy = new ArrayList<Double>();
                                for(int i=0;i<s.objectivesCopy.size();i++)
                                {
                                    this.objectivesCopy.add(s.objectivesCopy.get(i));
                                }
                                this.objectives_names = new ArrayList<String>();

                                for(int i=0;i<s.objectives_names.size();i++)
                                {
                                    this.objectives_names.add(s.objectives_names.get(i));
                                }
                                this.dominance =s.dominance;
                                this.distance =s.distance;
                                this.crowding_distance =s.crowding_distance;
                                this.ref_lines_distance = new ArrayList<Double>(s.ref_lines_distance);
                                this.ref_lines_index = new ArrayList<Integer>(s.ref_lines_index);
                                this.associated_ref_point_index = s.associated_ref_point_index;
			}
       /*create solution*/
       void createSolution()
       {
    	   this.numberOfRefactorings = getNumberOfRefactorings(MIN_NUM_REFACTORING, MAX_NUM_REFACTORING);
    	   this.Refactorings = getRefactorings(MIN_REFACTORING, MAX_REFACTORING);
       }
                        
			//This function generates the number of refactoring to apply per solution
			Double getNumberOfRefactorings(int min, int max){
				
				double res = 0;
				boolean correctChoice = false;
				
					while(!correctChoice){
						
						res = Random.random(min, max);
						if(res > min || res < max)
							correctChoice = true;
					}
				
					return res;
			}
			
			
			ArrayList<Integer>getRefactorings(int min,int max){
				
				int random = 0;
				ArrayList<Integer> res = new ArrayList<Integer>();
				
				for(int i = 0; i<this.numberOfRefactorings; i++){
					
					random = Random.random(min, max);				
					res.add(random);
				}
				
				return res;
			}
			
			//This function apply sequence of one solution refactorings
		public 	void applyRefactoring(){
				
			    int refactoringInstance = 0;
				for(int i = 0; i < this.Refactorings.size() ; i++){
					
					refactoringInstance = this.Refactorings.get(i);
					
					if(refactoringInstance == 1){
						
						String packageName = new String("NewPackage" + newPackageId);
						this.copyOfFile = PackageRefactoring.createPackage(copyOfFile, packageName);
						this.newPackageId += 1;
					}
					
					else if (refactoringInstance == 2){
						
						this.copyOfFile = PackageRefactoring.mergePackage(copyOfFile);
						cosineSimilarity += PackageRefactoring.cosineSimilarity;	
					}
					
					else if (refactoringInstance == 3){
						
						this.copyOfFile = PackageRefactoring.splitPackage(copyOfFile) ;
						cosineSimilarity += PackageRefactoring.cosineSimilarity;
					}
					
					//else if (refactoringInstance == 4){
					else{	
						this.copyOfFile = PackageRefactoring.moveClass(copyOfFile);
						cosineSimilarity += PackageRefactoring.cosineSimilarity;
					}
				}
			}
	/* Create a new apply refactorings function for sequence export
	 * 
	 */
		public 	void applyRefactoring1(){
			
		    int refactoringInstance = 0;
		   // System.out.println("Size of refactoring array" + this.Refactorings.size());
			for(int k = 0; k < this.Refactorings.size() ; k++){
				
				refactoringInstance = this.Refactorings.get(k);
				
				if(refactoringInstance == 1){
					
					String packageName = new String("NewPackage" + newPackageId);
					this.copyOfFile = PackageRefactoring.createPackage(copyOfFile, packageName);
					this.newPackageId += 1;
					this.solutionSequence.add( PackageRefactoring.refSequence);
					//System.out.println("Applied refactoring on solution "+k + PackageRefactoring.refSequence); 
				}
				
				else if (refactoringInstance == 2){
					
					this.copyOfFile = PackageRefactoring.mergePackage(copyOfFile);
					cosineSimilarity += PackageRefactoring.cosineSimilarity;
					//System.out.println("Instance semantic = " + cosineSimilarity);
					this.solutionSequence.add( PackageRefactoring.refSequence);
					//System.out.println("Applied refactoring on solution "+k + PackageRefactoring.refSequence);
				}
				
				else if (refactoringInstance == 3){
					
					this.copyOfFile = PackageRefactoring.splitPackage(copyOfFile) ;
					cosineSimilarity += PackageRefactoring.cosineSimilarity;
					//System.out.println("Instance semantic = " + cosineSimilarity);
					this.solutionSequence.add( PackageRefactoring.refSequence);
					//System.out.println("Applied refactoring on solution "+k + PackageRefactoring.refSequence);
				}
				
				//else if (refactoringInstance == 4){
				else{
					this.copyOfFile = PackageRefactoring.moveClass(copyOfFile);
					cosineSimilarity += PackageRefactoring.cosineSimilarity;
					//System.out.println("Instance semantic = " + cosineSimilarity);
					this.solutionSequence.add( PackageRefactoring.refSequence);
					//System.out.println("Applied refactoring on solution "+k + PackageRefactoring.refSequence);
				}
			}
		}
	/*
	 * This function evaluates a solution and gives number of its cohesion, coupling, number of packages, 
	 * number of class per package, number of refactorings;		
	 */
	public void evaluateSolution(int current_generation){
			
		int last_generation = Execution.generations-1;
		
		if (current_generation == last_generation)
            this.applyRefactoring1();
		else
			this.applyRefactoring();
            
            int cohesion = 0, coupling = 0, sumClass = 0;
			for(packageDescription aPackage: this.copyOfFile){
				
				cohesion += aPackage.pCohesion.size();
				coupling += aPackage.pCoupling;
				sumClass += aPackage.pClasses.size(); 
			}
                        
                        objectives = new ArrayList<Double>();
                        objectives_names = new ArrayList<String>();
			
			numberOfPackage = copyOfFile.size();
			classPerPackage = sumClass/numberOfPackage;
			SolCohesion = cohesion/numberOfPackage;
			SolCoupling = coupling/numberOfPackage;
			
			objectives.add(numberOfPackage);
						objectivesCopy.add(0, numberOfPackage);
			            //objectivesCopy.add(numberOfPackage);
                        this.objectives_names.add("numberOfPackage");
			objectives.add(numberOfRefactorings);
						objectivesCopy.add(1, numberOfRefactorings);
			           // objectivesCopy.add(numberOfRefactorings);
                        this.objectives_names.add("numberOfRefactorings");
			objectives.add(classPerPackage);
			           // objectivesCopy.add();
			            objectivesCopy.add(2, classPerPackage);
                        this.objectives_names.add("ClassPerPackage");
			objectives.add(SolCoupling);
			            //objectivesCopy.add();
			            objectivesCopy.add(3, SolCoupling);
                        this.objectives_names.add("Coupling");
			objectives.add(SolCohesion);
			            //objectivesCopy.add();
			            objectivesCopy.add(4, SolCohesion);
                        this.objectives_names.add("Cohesion");
			objectives.add(cosineSimilarity);
			           //objectivesCopy.add();
			            objectivesCopy.add(5, cosineSimilarity);
                        this.objectives_names.add("CosineSimilarity");
	}
			
    public void print_solution_system(){
			int i = 0, j = 0;
			for(packageDescription s : this.copyOfFile)
                        {
				i++;
	
				for(SootClass c: s.pClasses)
                                {
					
					//System.out.println("List of class of this package: " + c.getName());
					
				}
				
				System.out.println("Class size: " + s.pClasses.size());
				j = j + s.pClasses.size();
				System.out.println("Number of Method: " + s.pMethods.size());
				System.out.println("Number of call out:" + s.pCallOut.size());
				System.out.println("Number of callin: " + s.pCallIn.size());
				System.out.println("Number of fields: " + s.nbFields);
				System.out.println("Number of cohesion: " + s.pCohesion.size());
				System.out.println("Number of vocabulary: " + s.vocabulary.size());
			}

			System.out.println("total package :" + i + "Against total app packages: " + copyOfFile.size());
			System.out.println("Number of refactoring: "+ numberOfRefactorings);
			System.out.println("Number of Class/package: " + classPerPackage);
			System.out.println("Number of coupling: "+ SolCoupling );
			System.out.println("Number of cohesion: "+ SolCohesion );
			System.out.println("Semantic similarity " + cosineSimilarity);
			
		}
    void print_solution()
    {
        System.out.println("\n --- Printing solution refactorings ---");
        System.out.println("\n --- Rank : "+this.rank);
        for(int i=0;i<this.Refactorings.size();i++)
        {
            System.out.println(refactoring[this.Refactorings.get(i)]);
        }
    }
    
    void print_solution_reference_distances(int index)
    {
        System.out.println("--- solution "+index+" is associated to reference point "+this.associated_ref_point_index);
        //System.out.println("\n --- Rank : "+this.rank);
        for(int i=0;i<this.ref_lines_distance.size();i++)
        {
            System.out.print(i+". "+this.ref_lines_distance.get(i)+" - ");
        }
        System.out.print("\n");
    }
    
    void print_metrics()
    {
        System.out.println("\n Rank :  "+this.rank+" Distance : "+this.distance+"\n");
        //System.out.println("This solotion objectives' size at SOLUTION PRINT METRICS " +this.objectives.size());
        for(int i=0;i<this.objectives.size();i++)
        {
            System.out.print(" "+objectives_names.get(i)+" : "+this.objectives.get(i));
        }
        System.out.println("\n");
    }
    
    String objectives_names_to_string()
    {
        String result = " ";
        for(int i=0;i<this.objectives_names.size();i++)
        {
            result+=(String)objectives_names.get(i)+" ";
        }
        return result ;
    }
    
    String objectives_values_to_string()
    {
        String result = " ";
        for(int i=0;i<this.objectives.size();i++)
        {
            result+=Double.toString(objectives.get(i));
            
            if(i != (this.objectives.size()-1))
            {
                result+=" , ";
            }
            
        }
        return result ;
    }
    
    
    
    void mutation1()
    {
        // Number of refactorings to be mutated
        int sol = Random.random(0, 2);
        
        if (print_executed_refactorings) System.out.println("\n Mutations to be done : "+sol);
        
        for(int i=0;i<sol;i++)
        {
            int numRefactoring=Random.random(this.MIN_REFACTORING, this.MAX_REFACTORING);
            int position = Random.random(0, (this.Refactorings.size()-1));
            if (print_executed_refactorings) System.out.println("\n Refactoring to be changed "+refactoring[this.Refactorings.get(position)]+" by "+refactoring[numRefactoring]);
            this.Refactorings.set(position, numRefactoring);
        } 
    }
    
    void mutation2()
    {
        int stop = 0 ;
        int position1 = 0 ;
        int position2 = 0 ;
        
        do
        {
            // Positions of refactorings to be rotated
            position1 = Random.random(0, (this.Refactorings.size()-1));
            position2 = Random.random(0, (this.Refactorings.size()-1));
            stop++;
        }
        while((position1==position2)&& (stop<200));
        
        if (stop == 200) 
        {
            if (print_executed_refactorings) System.out.println("\n mutation2 failed to find two diffrent indexes! mutation aborted");
        }
        else
        {
            Integer temp = this.Refactorings.get(position1);
            this.Refactorings.set(position1, this.Refactorings.get(position2));
            this.Refactorings.set(position2, temp);
            if (print_executed_refactorings) System.out.println("\n mutation2 has switched between "+position1+" and "+position2);
        }
    }
    
    public double average(double[] nums) 
    {
        double result=0.0;
        int i=0;
        for(i=0; i < nums.length; i++)
        {
            result=result + nums[i];
        }
        return (double) result/nums.length;
    }
    
    public double average_int(int[] nums) 
    {
        double result=0.0;
        int i=0;
        for(i=0; i < nums.length; i++)
        {
            result=result + nums[i];
        }
        return (double) result/nums.length;
    }
}
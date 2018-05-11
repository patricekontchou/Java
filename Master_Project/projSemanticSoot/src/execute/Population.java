package execute;

/**
 * This class generate and initialize a population of solution that will be used for GA operations.
 * evaluates first then apply crossover and mutation to create offsprings.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import soot.SootClass;
import soot.SootMethod;
import soot.SootField;

import java.util.Set;

public class Population 
{
    ArrayList<Solution> solutions;
    int population_size;
    int last_generation;
    
    Population()
    {
    
        this.solutions = new ArrayList<Solution>();
        this.population_size = 0 ;
        last_generation = 0 ;
        
    }
    
    Population(int size, int generations)
    {
        this.solutions = new ArrayList<Solution>();
        this.population_size = size ;
        //this.selection = new Selection(generations);
        last_generation = Execution.generations - 1 ;
        
    }
		
public void create_poplulation()
{
   for(int i=0;i<population_size;i++)
   {
        Solution temp = new Solution();
       // temp.createSolution();
        solutions.add(temp);
   }
}

// re-evaluate the current population, then normalize its values
public void evaluate_poplulation(int current_generation)
{
	//System.out.println("size of solution "+ this.solutions.size());
    for(int i=0;i<this.solutions.size();i++)
    {
    	System.out.println("----------Solution-------- " +i);
        this.solutions.get(i).evaluateSolution(current_generation);
        //System.out.println("Objective size at evalution "+this.solutions.get(i).objectives.size());
    }
    this.normalize_metrics();
}

// called in evaluate population. To apply r-NSGA-II objective values must be normalized and dominance distance should be calculated
public void normalize_metrics()
{
	
    // preparing values for calculating dominance distance
    double min_objectives [] = new double [this.solutions.get(0).objectives.size()];
    double max_objectives [] = new double [this.solutions.get(0).objectives.size()];
            
    for(int i=0;i<this.solutions.get(0).objectives.size();i++)
    {   	
        min_objectives[i] = this.solutions.get(0).objectives.get(i);
        max_objectives[i] = this.solutions.get(0).objectives.get(i);
               
        for(int j =0;j<solutions.size();j++)
        {
            if(solutions.get(j).objectives.get(i) < min_objectives[i])
            	
            	min_objectives[i] = solutions.get(j).objectives.get(i);
        
            if(solutions.get(j).objectives.get(i) > max_objectives[i])
            	
            	max_objectives[i] = solutions.get(j).objectives.get(i);
        }
    }
    
    for(int i=0;i<solutions.size();i++)
    {
        for(int j=0;j<this.solutions.get(0).objectives.size();j++)
        {
        	DecimalFormat format = new DecimalFormat("###.##");
        	double normValue = (double)(this.solutions.get(i).objectives.get(j)-min_objectives[j])/( max_objectives[j]-min_objectives[j]);
            this.solutions.get(i).objectives.set(j, Double.valueOf(format.format(normValue)));
        }
    }
    

        int index = 0 ;
        if(solutions.get(0).objectives_names.contains("Cohesion"))
        {
            System.out.println("\n Reversing Cohesion");
            index = solutions.get(0).objectives_names.indexOf("Cohesion");
            for(int j=0;j<solutions.size();j++)
            {
                solutions.get(j).objectives.set(index , 1 - solutions.get(j).objectives.get(index));
            }
        }
        if(solutions.get(0).objectives_names.contains("CosineSimilarity"))
        {
            System.out.println("\n Reversing CosineSimilarity");
            index = solutions.get(0).objectives_names.indexOf("CosineSimilarity");
            for(int j=0;j<solutions.size();j++)
            {
                solutions.get(j).objectives.set(index , 1 - solutions.get(j).objectives.get(index));
            }
        }
}

// used only in compare_dominance
/*
int r_dominates(Solution x, Solution y, double dmin, double dmax, Sigma s)
{
    int result = 0 ;
    double dist_between_individuals = 0 ;
            
    dist_between_individuals = ((double)(x.distance - y.distance)/(double)(dmax-dmin));
    
    if( dist_between_individuals < -s.current_value)
    {
        result = -1 ;
    }
    else if( dist_between_individuals > s.current_value)
    {
        result = 1 ;
    }
    else
    {
        result = 0 ;
    }
    
    return result ;
}
*/

 // to be used on the Comparator class when checking which is the best solution between two candidates
    public int compare_dominance(Solution solution1, Solution solution2) 
    {
//System.out.println("solution 1 on objectives size  "+ solution1.objectives.size());
//System.out.println("solution 2 on objectives size"+ solution1.objectives.size());
    int dominate1 ; // dominate1 indicates if some objective of solution1 
                    // dominates the same objective in solution2. dominate2
    int dominate2 ; // is the complementary of dominate1.

    dominate1 = 0 ; 
    dominate2 = 0 ;
    
    int flag; //stores the result of the comparison
                                                
    // Applying a normal dominance Test then
    double value1, value2;
    for (int i = 0; i < solution1.objectives.size(); i++) {
      value1 = solution1.objectives.get(i);
      value2 = solution2.objectives.get(i);
      if (value1 < value2) {
        flag = -1;
      } else if (value1 > value2) {
        flag = 1;
      } else 
      {
          flag = 0;
      }
      
      if (flag == -1) {
        dominate1 = 1;
      }
      
      if (flag == 1) {
        dominate2 = 1;           
      }
    }
            
    if (dominate1 == dominate2) {            
      return 0; //No one dominate the other
    }
    if (dominate1 == 1) {
      return -1; // solution1 dominate
    }
    return 1;    // solution2 dominate   
  } // compare
    
  // used in generating next population 
// used in generating next population  
    /*
public void crowdingDistanceAssignment(ArrayList<Solution> one_front) 
{
    int size = one_front.size();        
            
if (size == 0)
  return;

if (size == 1) {
  one_front.get(0).crowding_distance = Double.POSITIVE_INFINITY;
  return;
} // if
    
if (size == 2) {
  one_front.get(0).crowding_distance = Double.POSITIVE_INFINITY;
  one_front.get(1).crowding_distance = Double.POSITIVE_INFINITY;
  return;
} // if       
    
//Use a new SolutionSet to evite alter original solutionSet
//ArrayList<Solution> front = new ArrayList<Solution>();

//for (int i = 0; i < size; i++){
 // front.add(one_front.get(i));
}

    
for (int i = 0; i < size; i++)
  one_front.get(i).crowding_distance = 0.0;        
    
double objetiveMaxn;
double objetiveMinn;
double distance;
            
for (int i = 0; i<one_front.get(0).objectives.size(); i++) 
{          
  // Sort the population by Obj n            
  this.sorting_one_objective(one_front, i);
  objetiveMinn = one_front.get(0).objectives.get(i);      
  objetiveMaxn = one_front.get(one_front.size()-1).objectives.get(i);     
  
  //Set de crowding distance            
  one_front.get(0).crowding_distance = Double.POSITIVE_INFINITY;
  one_front.get(size-1).crowding_distance = Double.POSITIVE_INFINITY;
  
  for (int j = 1; j < size-1; j++) {
    distance = one_front.get(j+1).objectives.get(i) - one_front.get(j-1).objectives.get(i);                    
    distance = distance / (objetiveMaxn - objetiveMinn);        
    distance += one_front.get(j).crowding_distance;                
    one_front.get(j).crowding_distance = distance;   
  } // for
} // for        
} // crowdingDistanceAssing 
* 
*/

// used when calculating the crowding distance
void sorting_one_objective(ArrayList<Solution> sol, int index)
{
    int min_index = 0 ;
    
    for(int i=0;i<(sol.size()-1);i++)
    {
        min_index = i ;
        for(int j=i+1;j<sol.size();j++)
        {
            if (sol.get(min_index).objectives.get(index) >sol.get(j).objectives.get(index)) min_index = j ;
        }
        if (min_index != i)
        {
            Solution temp = new Solution();
            temp = sol.get(i);
            sol.set(i, sol.get(min_index));
            sol.set(min_index, temp);
        }
    }
}
// used in generating next population
/*
public void sorting_crowding_distance(ArrayList<Solution> sol)
{
    int max_index = 0 ;
    
    for(int i=0;i<(sol.size()-1);i++)
    {
        max_index = i ;
        for(int j=i+1;j<sol.size();j++)
        {
            if (sol.get(max_index).crowding_distance < sol.get(j).crowding_distance) max_index = j ;
        }
        if (max_index != i)
        {
            Solution temp = new Solution();
            temp = sol.get(i);
            sol.set(i, sol.get(max_index));
            sol.set(max_index, temp);
        }
    }
} // compare
*/

public void print_popluation()
{ 
    for(int i=0;i<solutions.size();i++)
    {
        System.out.println("\n--- Solution number "+(i+1)+"---");
        solutions.get(i).print_solution();
    }
}

public void print_popluation_metrics(int generation)
{ 
    System.out.println("\n--------------- Population number "+generation+"--------------- ");
    for(int i=0;i<solutions.size();i++)
    {
        System.out.println("\n--- Solution number "+i+"---");
        solutions.get(i).print_metrics();
    }
}
// used only in tournament_selection2
public void crossover(Solution a, Solution b)
{
    ArrayList<Solution> result = new ArrayList<Solution>();
    int minimum = Math.min(a.Refactorings.size(), b.Refactorings.size());
    int cut = Random.random(1, minimum-2);
    
    result.add(new Solution());
    
    for(int i=0;i<cut;i++)
    {
       // result.get(0).Refactorings.add(a.Refactorings.get(i));
    	 // result.get(0).Refactorings.set(i, a.Refactorings.get(i));
    	if(i>result.get(0).Refactorings.size()-1)
    	{
    		//result.get(0).Refactorings.add(a.Refactorings.get(i));
    	}
    	else
        result.get(0).Refactorings.set(i, a.Refactorings.get(i));
    }
    
    for(int i=cut;i<b.Refactorings.size();i++)
    {
        //result.get(0).Refactorings.add(b.Refactorings.get(i));
    	//result.get(0).Refactorings.set(i, a.Refactorings.get(i));
    	if(i>result.get(0).Refactorings.size()-1)
    	{
    		//result.get(0).Refactorings.add(b.Refactorings.get(i));
    	}
    	else
        result.get(0).Refactorings.set(i, b.Refactorings.get(i));
    }
    
    result.add(new Solution());
    
    for(int i=0;i<cut;i++)
    {
       // result.get(1).Refactorings.add(b.Refactorings.get(i));
        //result.get(1).Refactorings.set(i, a.Refactorings.get(i));
    	if(i>result.get(1).Refactorings.size()-1)
    	{
    		//result.get(1).Refactorings.add(b.Refactorings.get(i));
    	}
    	else
        result.get(1).Refactorings.set(i, b.Refactorings.get(i));
    }
    
    for(int i=cut;i<a.Refactorings.size();i++)
    {
        //result.get(1).Refactorings.add(a.Refactorings.get(i));
    	if(i>result.get(1).Refactorings.size()-1)
    	{
    		//result.get(1).Refactorings.add(a.Refactorings.get(i));
    	}
    	else
        result.get(1).Refactorings.set(i, a.Refactorings.get(i));
    }
    // updating solutions
    a.Refactorings = new ArrayList<Integer>(result.get(0).Refactorings);
    b.Refactorings = new ArrayList<Integer>(result.get(1).Refactorings);
    //return result;
}
// used only in tournament_selection2
public Solution mutation(Solution s)
{
    Solution result = new Solution(s);
    int random = Random.random(0,1);
    
    if(random==0) result.mutation1();
    else result.mutation2();
    
    return result;
}

// it is called to select randomly the best candidate solutions to be parents for the upcoming offsprings
public ArrayList<Solution> random_selection()
{
    System.out.println("\n random selection started...");
    
    // evaluate the mix of current population and generated offsprings
    //this.evaluate_poplulation();
    
    java.util.Random number_generator = new java.util.Random();
    int first_individual_index = 0;
    int tournament_bound = this.solutions.size();
    ArrayList<Solution> parents = new ArrayList<Solution>();
    
    for(int i=0;i<tournament_bound;i++)
    {
        number_generator = new java.util.Random();
        do
        {
            first_individual_index = number_generator.nextInt((this.solutions.size()));
        }
        while(first_individual_index<0);

        parents.add(new Solution(solutions.get(first_individual_index)));

    }
    
    //crossover & mutation for parents to create offsprings
    for(int i=0;i<parents.size()-1;i+=2)
    {
        this.crossover(parents.get(i),parents.get(i+1));
        parents.set(i, this.mutation(parents.get(i)));
        parents.set(i+1, this.mutation(parents.get(i+1)));
    }
    System.out.println("\n offsprings created... their number is : "+parents.size());
    return parents; 
}

    public void generate_next_popluation(ArrayList<Solution> offsprings, int current_generation)
    {
        System.out.print("\n evaluating current population + offsprings started... ");
        for(int i=0;i<offsprings.size();i++)
        {
            this.solutions.add(new Solution(offsprings.get(i)));
        }
        // re-evaluate the population with the offsprings
        this.evaluate_poplulation(current_generation);
        System.out.print("creating fronts for current population and offsprings... ");
        Comparator c = new Comparator(this);
        c.print_fronts();
        int remain = population_size;
        int front_index = 0;
        ArrayList<Solution> front = null;
        this.solutions.clear();

      // Obtain the next front
      front = c.getSubfront(front_index);

      while ((remain > 0) && (remain >= front.size())) 
      {
          //Assign crowding distance to individuals
          //this.crowdingDistanceAssignment(front);
          //Add the individuals of this front
          for (int k = 0; k < front.size(); k++) 
          {
              this.solutions.add(front.get(k));
          } // for

          //Decrement remain
          remain = remain - front.size();

          //Obtain the next front
          front_index++;
        
          if (remain > 0) 
          {
              front = c.getSubfront(front_index);
          } // if        
      } // while

      // Remain is less than front(index).size, insert only the best one
      if (remain > 0) 
      {
          // front contains individuals to insert 
          
          Hyperplane h = new Hyperplane(Execution.OBJECTIVENUMBER,Execution.division);
          
          BigInteger exprected_results_size = h.binomial( ( Execution.OBJECTIVENUMBER+(int)Execution.division - 1 ) , (int)Execution.division );
        
          System.out.println("\n Creating a Hyperplane to choose "+remain+" solutions from the current front ");
          
          System.out.print("\n exprected reference points to create : "+exprected_results_size);
          
          h.create_hyperplane();
          
          System.out.println(" and the actual reference points number is : "+h.ref_points.size());
          
          ArrayList<Solution> candidate_solutions = new ArrayList<Solution>();
          ArrayList<Solution> chosen_solutions = new ArrayList<Solution>();
                           
          candidate_solutions = h.calculate_normal_distance(front);
          chosen_solutions = h.niching(candidate_solutions, remain);
          
          
          for (int k = 0; k < remain; k++) 
          {
              this.solutions.add(chosen_solutions.get(k));
          } // for

          remain = 0;
      } // if
      /*
      // getting the best solution:
      
      int min_distance_index = 0 ;
      
      for(int i=1;i<this.solutions.size();i++)
        {
            if(solutions.get(min_distance_index).distance > solutions.get(i).distance) min_distance_index = i;
        }  
      selection.selection.add(solutions.get(min_distance_index));
      selection.fronts += c.getNumberOfSubfronts();
      */
      if (current_generation == last_generation)
      {
          System.out.println("\n last generation created! exporting results...");

          c = new Comparator(this);
          front = new ArrayList<Solution>();
          front = c.getSubfront(0);
          c.export_population();
          this.export_pareto(front);
          this.export_solution_value(front);
          this.export_refactoring_sequence(front);
          this.export_configuration(front.size());
      }
      else
      {
          System.out.println("\n next generation ready...");
      }  
    }
    
    void export_pareto(ArrayList<Solution> pareto)
    {
        Date dNow = new Date( );
        SimpleDateFormat ft = 
        new SimpleDateFormat ("yyyy.MM.dd'-'hh.mm.ss");
        
        String file_name = new String("C:/Users/P.Kontchou/Desktop/Master/CIS 565/Master Project/projSemanticSoot/result_pareto_");
        file_name = file_name.concat(ft.format(dNow));
        file_name = file_name.concat(".csv");
        File exportPareto = new File (file_name);
        try
	{
	    FileWriter writer = new FileWriter(exportPareto);
            
            for(int i=0;i<pareto.get(0).objectives_names.size();i++)
            {
                writer.append(pareto.get(0).objectives_names.get(i));
                if(i == (pareto.get(0).objectives_names.size()-1))
                {
                    writer.append('\n');
                }
                else
                {
                    writer.append(',');
                }
            }
            
            for(int i=0;i<pareto.size();i++)
            {
                for(int j=0;j<pareto.get(i).objectives.size();j++)
                {
                    writer.append(Double.toString(pareto.get(i).objectives.get(j)));
                    if(j == (pareto.get(i).objectives.size()-1))
                    {
                        writer.append('\n');
                    }
                    else
                    {
                        writer.append(',');
                    }
                }
                
            }
            
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	} 
    }
    
    /*Export Refactoring Sequence*/
    void export_refactoring_sequence(ArrayList<Solution> pareto)
    {
        Date dNow = new Date( );
        SimpleDateFormat ft = 
        new SimpleDateFormat ("yyyy.MM.dd'-'hh.mm.ss");
        
        String file_name = new String("C:/Users/P.Kontchou/Desktop/Master/CIS 565/Master Project/projSemanticSoot/refactoring_sequence_");
        file_name = file_name.concat(ft.format(dNow));
        file_name = file_name.concat(".txt");
        File refactoringSequence = new File (file_name);
        try
	{
	    FileWriter writer = new FileWriter(refactoringSequence);
                        
	    for(int i=0;i<pareto.size();i++)
        {
	    	 
	    	writer.append("--------A Solution--- ");
            writer.append('\n');
	    	//String solutionSeq = new String ("Solution "+i+" solution number of refactorings " +pareto.get(i).objectivesCopy.get(1) + " Against refactoring sequence " +pareto.get(i).solutionSequence.size()+"refactorings size " + pareto.get(i).Refactorings.size() );
	    	//writer.append(solutionSeq+'\n');
            for(int j=0;j<pareto.get(i).solutionSequence.size();j++)
            {
                writer.append(pareto.get(i).solutionSequence.get(j)+'\n');
                writer.append('\n');
            }
            
            
        }
            
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	} 
    }
    
    /* Exporting the real value of solutions*/
    void export_solution_value(ArrayList<Solution> pareto)
    {
        Date dNow = new Date( );
        SimpleDateFormat ft = 
        new SimpleDateFormat ("yyyy.MM.dd'-'hh.mm.ss");
        
        String file_name = new String("C:/Users/P.Kontchou/Desktop/Master/CIS 565/Master Project/projSemanticSoot/result_Value_");
        file_name = file_name.concat(ft.format(dNow));
        file_name = file_name.concat(".csv");
        File solutionValue = new File (file_name);
        try
	{
	    FileWriter writer = new FileWriter(solutionValue);
            
            for(int i=0;i<pareto.get(0).objectives_names.size();i++)
            {
                writer.append(pareto.get(0).objectives_names.get(i));
                if(i == (pareto.get(0).objectives_names.size()-1))
                {
                    writer.append('\n');
                }
                else
                {
                    writer.append(',');
                }
            }
            
            for(int i=0;i<pareto.size();i++)
            {
                for(int j=0;j<pareto.get(i).objectives.size();j++)
                {
                    writer.append(Double.toString(pareto.get(i).objectivesCopy.get(j)));
                    if(j == (pareto.get(i).objectives.size()-1))
                    {
                        writer.append('\n');
                    }
                    else
                    {
                        writer.append(',');
                    }
                }
                
            }
            
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	} 
    }
    
    //Exporting configuration
        void export_configuration(int pareto_size)
    {
        Date dNow = new Date( );
        SimpleDateFormat ft = 
        new SimpleDateFormat ("yyyy.MM.dd'-'hh.mm.ss");
        
        String file_name = new String("C:/Users/P.Kontchou/Desktop/Master/CIS 565/Master Project/projSemanticSoot/result_configuration_");
        file_name = file_name.concat(ft.format(dNow));
        file_name = file_name.concat(".csv");
        File result = new File(file_name);
        try
	{
	    FileWriter writer = new FileWriter(result);
 
	    writer.write("--- Execution Configuration ---\n\n");
            writer.append("Number of Objectives : "+Integer.toString(this.solutions.get(0).objectives_names.size())+"\n\n");
            writer.append("Considered Objectives : "+this.solutions.get(0).objectives_names_to_string()+"\n\n");
            writer.append("Population size : "+Integer.toString(this.solutions.size())+"\n\n");
            writer.append("Pareto size : "+Integer.toString(pareto_size)+"\n\n");
            writer.append("Selected solutions are the set of best solution (minimal distance) in each iteration (duplicated solutions removed) \n\n");
 
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	} 
    }
    
    /* public static void main(String[] args) 
    {
        // just for testing case
        Execution e = new Execution(); // important for parameters
        double aspiration_values[] = {0.4,0.6};
        Population p = new Population(5,50);
        p.create_poplulation();
        p.print_popluation();
        
        
    }*/
    
}

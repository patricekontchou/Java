package execute;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MWM
 */
public class Comparator 
{
    ArrayList<Solution> solutions ;
    ArrayList<ArrayList<Solution>> fronts   ;
    
    // Backup solutions
    public Comparator(Population pop) 
    {
        this.solutions = new ArrayList<Solution>();
        this.fronts = new ArrayList<ArrayList<Solution>>();
        
        for (int i = 0; i < pop.solutions.size(); i++)
        {
            this.solutions.add(pop.solutions.get(i));
        }
        
    //solutionSet_ = solutionSet ;

    // dominateMe[i] contains the number of solutions dominating i        
    int [] dominateMe = new int[this.solutions.size()];

    // iDominate[k] contains the list of solutions dominated by k
    List<Integer> [] iDominate = new List[this.solutions.size()];

    // front[i] contains the list of individuals belonging to the front i
    List<Integer> [] front = new List[this.solutions.size()+1];
        
    // flagDominate is an auxiliar variable
    int flagDominate;    

    // Initialize the fronts 
    for (int i = 0; i < front.length; i++)
      front[i] = new LinkedList<Integer>();
        
    //-> Fast non dominated sorting algorithm
    for (int p = 0; p < this.solutions.size(); p++) 
    {
    // Initialice the list of individuals that i dominate and the number
    // of individuals that dominate me
      iDominate[p] = new LinkedList<Integer>();
      dominateMe[p] = 0;            
      // For all q individuals , calculate if p dominates q or vice versa
      for (int q = 0; q < this.solutions.size(); q++) 
      {
          flagDominate =pop.compare_dominance(solutions.get(p), solutions.get(q));
          
          if (flagDominate == -1) 
          {
              iDominate[p].add(new Integer(q));
          } 
          else if (flagDominate == 1) 
          {
                    dominateMe[p]++;   
          }
      }
            
      // If nobody dominates p, p belongs to the first front
      if (dominateMe[p] == 0) 
      {
          front[0].add(new Integer(p));
          solutions.get(p).rank = 0 ;
      }            
    }
        
    //Obtain the rest of fronts
    int i = 0;
    Iterator<Integer> it1, it2 ; // Iterators
    while (front[i].size()!= 0) 
    {
      i++;
      it1 = front[i-1].iterator();
      while (it1.hasNext()) 
      {
        it2 = iDominate[it1.next().intValue()].iterator();
        while (it2.hasNext()) 
        {
          int index = it2.next().intValue();
          dominateMe[index]--;
          if (dominateMe[index]==0) 
          {
            front[i].add(new Integer(index));
            solutions.get(index).rank =i;
          }
        }
      }
    }
    //<-
      
    //0,1,2,....,i-1 are front, then i fronts
    for (int j = 0; j < i; j++) 
    {
      it1 = front[j].iterator();
      fronts.add(j, new ArrayList<Solution>());
      while (it1.hasNext()) 
      {
                fronts.get(j).add(solutions.get(it1.next().intValue()));       
      }
      
    }
    for (int j = 0; j < fronts.size(); j++) 
    {
        for (int k = 0; k < fronts.get(j).size(); k++) 
        {
            fronts.get(j).get(k).rank = j ;
        }
    }
  } // Ranking

  /**
   * Returns a <code>SolutionSet</code> containing the solutions of a given rank. 
   * @param rank The rank
   * @return Object representing the <code>SolutionSet</code>.
   */
  public ArrayList<Solution> getSubfront(int rank) {
    return fronts.get(rank);
  } // getSubFront

  public void createHyperPlane()
  {
      
  }
  
  /** 
  * Returns the total number of subFronts founds.
  */
  public int getNumberOfSubfronts() {
    return fronts.size();
  } // getNumberOfSubfronts
  
  public void print_fronts()
  {
      System.out.println("done! and number of fronts is : "+this.getNumberOfSubfronts());
      for (int j = 0; j < fronts.size(); j++) 
    {
        System.out.println("\n Front number : "+j+" has solutions size : "+fronts.get(j).size());
        for (int k = 0; k < fronts.get(j).size(); k++) 
        {
            fronts.get(j).get(k).print_metrics();
        }
    }
  }
  
  void export_population()
    {
        Date dNow = new Date( );
        SimpleDateFormat ft = 
        new SimpleDateFormat ("yyyy.MM.dd'-'hh.mm.ss");
        
        String file_name = new String("./result_population_");
        file_name = file_name.concat(ft.format(dNow));
        file_name = file_name.concat(".csv");
        File R_population = new File (file_name);
        try
	{
	    FileWriter writer = new FileWriter(R_population);
            
            for (int j = 0; j < fronts.size(); j++) 
            {
                 writer.append("--- Front number "+Integer.toString(j)+" has "+fronts.get(j).size()+" solutions ---\n\n");
                 
                 for(int i=0;i<fronts.get(0).get(0).objectives_names.size();i++)
                 {
                    writer.append(fronts.get(0).get(0).objectives_names.get(i));
                    if(i == (fronts.get(0).get(0).objectives_names.size()-1))
                    {
                        writer.append('\n');writer.append('\n');
                    }
                    else
                    {
                        writer.append(',');
                    }
                 }
                 
                 System.out.println("\n Front number : "+j+" has solutions size : "+fronts.get(j).size());
                 for (int k = 0; k < fronts.get(j).size(); k++) 
                 {
                    writer.append(fronts.get(j).get(k).objectives_values_to_string()+"\n");
                    
                    if(k == (fronts.get(j).size()-1))
                    {
                        writer.append('\n');
                    }
                    
                 }
                 
                
            }
	    //generate whatever data you want
 
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	} 
    }
    
}

package execute;


import java.util.ArrayList;


public class ParetOptimality<T>{
	
	// declare variables
	private ArrayList< ArrayList<T>> data;
	private int noCriteria; 
	
	/**
	 * Object containing data, an array list for each criteria (data column). At least two columns are required.
	 * @param x first column of data in ArrayList format
	 * @param y second column of data in ArrayList format
	 * @param z any possible other column(s) : 0 to many
	 */
	public ParetOptimality(ArrayList<T> _x, ArrayList<T> _y, ArrayList<T>... _z){
		// create a object (matrix) for all data
		data = new ArrayList< ArrayList<T> >();
		
		
		// add data to matrix
		data.add(_x);
		data.add(_y);
		// create a loop for the var-args
		for (int i=0; i<_z.length; i++)
			data.add(_z[i]);
		
		noCriteria = data.size(); // number of criteria
		int size = data.get(0).size();
		
		// check whether arraylists are not empty
		if (size == 0){
			System.out.println("ERROR: Empty ArrayLists are not allowed!");
			System.exit(1);
		}
		
		// check whether all columns have the same maxSize
		boolean sameSize = true;
		for (int j=1; j<noCriteria; j++)
			if (size != data.get(j).size())
				sameSize = false;
		
		// if not, exit
		if (!sameSize){
			System.out.println("ERROR: Not all data entries (columns) have the same maxSize.");
			System.exit(1);
		}
		
		run();
	}
	
	/**
	 * Constructor with one already formed array-list containing different array-lists
	 * @param _paropt
	 */
	public ParetOptimality(ArrayList< ArrayList<T> > _paropt){
		// set data object
		data = _paropt;
		
		// calculate properties
		noCriteria = data.size(); // number of criteria
		int size = data.get(0).size();
		
		// check whether arraylists are not empty
		if (size == 0){
			System.out.println("ERROR: Empty ArrayLists are not allowed!");
			System.exit(1);
		}
		
		// check whether all columns have the same maxSize
		boolean sameSize = true;
		for (int j=1; j<noCriteria; j++)
			if (size != data.get(j).size())
				sameSize = false;
		
		// if not, exit
		if (!sameSize){
			System.out.println("ERROR: Not all data entries (columns) have the same maxSize.");
			System.exit(1);
		}
		
		run();
	}
	
	public void run(){
		// sort the data matrix
		sort();
		
		// remove duplicates
		duplicates();
		
		// filter Pareto Optimal solutions
		filter();
	}
	
	/**
	 * method to sort the data
	 */
	private void sort(){
		// local variables
		int min;
		T temp;
		
		// check whether a string comparison is needed
		boolean isString = true;
		try{
			@SuppressWarnings("unused")
			String a = (String) data.get(0).get(0);
		} catch (Exception e){
			isString = false;
		}
		
		// sorting process; 
		for (int index = 0; index < data.get(0).size() - 1; index++){
			min = index;
			for (int scan = index+1; scan < data.get(0).size(); scan++){				
				// check for the minimum (if double or integer)
				if( !isString && ( (Double) data.get(0).get(scan) -  (Double) data.get(0).get(min)) < 0)
					min = scan;
				// check in case of a string (should not be the case, but ... )
				else if ( isString && ((String) data.get(0).get(scan)).compareTo((String) data.get(0).get(min)) < 0 )
					min = scan;
			} // end inner for
			
			// swap all criteria
			for (int i=0; i<noCriteria; i++){
				temp = data.get(i).get(min);
				data.get(i).set(min, data.get(i).get(index));
				data.get(i).set(index, temp);
			}
		}
	}
	
	/*
	 * Method to remove double lines; one alternative solutions is enough
	 */
	public void duplicates(){
		// an array list containing indices that duplicates
		ArrayList<Integer> doubles = new ArrayList<Integer>();
		
		// create a loop to check all solutions
		for (int index = 1; index < data.get(0).size(); index++){
			// somehow it was needed to first store them in a seperate variable
			double a,b;
			a = (Double) data.get(0).get(index);
			b = (Double) data.get(0).get(index-1);

			// if they are the same, check other criteria
			if (a == b)
			{
				// if other stay the same, this holds true and thus it can be removed
				boolean par = true;

				// check other criteria
				for (int j=1; j<noCriteria; j++ ){
					a = (Double) data.get(j).get(index);
					b = (Double) data.get(j).get(index - 1);
					if ( a != b)
						par = false;
				} // end inner for
				
				// add index to array-list
				if (par)
					doubles.add(index-1);

			} // end-if
		} // end for-loop
		
		// remove double lines;
		for (int index = doubles.size()-1; index >= 0; index--){
			int rem = doubles.get(index);
			for (int i=0; i<noCriteria; i++)
				data.get(i).remove(rem);
		}
		
	} // end method
	
	/**
	 * Method to remove all dominated solutions. Thus the non-dominated solutions (the Pareto front) are the only left.
	 */
	public void filter(){
		
		// maximize all objectives
		// create a loop to check all solutions
		for (int index = 1; index < data.get(0).size(); index++){

			// an inner loop to compare two solutions
			for (int sec = 0; sec < index; sec++){				
				// indicators to determine whether the solution is dominated
				boolean succ = false; boolean succ2 = false;
				boolean neq = false; boolean neq2 = false;
				
				// check all criteria
				for (int crit = 0; crit < data.size(); crit++){
					// solutions should be checked both ways to determine whether one dominates the other
					if ((Double) data.get(crit).get(sec) > (Double) data.get(crit).get(index))
						succ = true;
					else if ( (Double) data.get(crit).get(sec) < (Double) data.get(crit).get(index) )
						neq = true;
					// and the other way around
					if ( (Double) data.get(crit).get(index) > (Double) data.get(crit).get(sec) )
						succ2 = true;
					else if ( (Double) data.get(crit).get(index) < (Double) data.get(crit).get(sec) )
						neq2 = true;
				}
				
				// remove dominated from data matrix
				if (succ && !neq){
					// remove each criterion separately
					for (int crit=0; crit < data.size(); crit++)
						data.get(crit).remove(index);
					
					// restart index because their indices has been changed
					index=0;
					break;
				}
				else if(succ2 && !neq2){
					// remove each criterion separately
					for (int crit=0; crit < data.size(); crit++)
						data.get(crit).remove(sec);
					
					// restart index because their indices has been changed
					index=0;
					break;
				}
				
			} // end for-innerloop
		} // end for-outerloop
	} // end method
	
	/**
	 * Method to get Pareto front
	 * @return an ArrayList containing arraylists for each criteria
	 */
	public ArrayList< ArrayList<T> > getPareto(){
		return data;
	}
	
	// Print data matrix
	
	public String toString(){
		// create empty string
		String output = "";
		
		// create for-loop to add each row
		int noRows = data.get(0).size();
		for (int i=0; i<noRows; i++){
			for (int j=0; j<data.size(); j++){
				output += data.get(j).get(i) + "\t";
			}
			output += "\n";
		}
		
		// return matrix in String format
		return output;
	}
}

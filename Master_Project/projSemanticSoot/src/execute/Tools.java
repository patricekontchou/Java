package execute;

import execute.iceberg.util.Strings;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import soot.Scene;


public class Tools {
	
	
	public static ArrayList<String> getClass(String repertoire) {
		ArrayList<String> list;
		
			File rep = new File(repertoire);
			rep.getName();
			String [] listefichiers;
			list = new ArrayList<String>();
			listefichiers=rep.list();
			for(int i=0;i<listefichiers.length;i++){
				if(listefichiers[i].endsWith(".class")==true ){ 
						list.add(listefichiers[i].split(".class")[0]);
						//System.out.println("addFile: ");
				}
			}
			for (File r : rep.listFiles()) {
				if(r.isDirectory()){
					list.addAll(getClass(r.getName(), r));
					//System.out.println("addFile: ");
				}
					
			}
		return list;
	}
	
	private static ArrayList<String> getClass(String repertoire,File rep){
		
		String [] listefichiers;
		ArrayList<String> list = new ArrayList<String>();
		
		int i;
		listefichiers=rep.list();
		
		for(i=0;i<listefichiers.length;i++){
			if(listefichiers[i].endsWith(".class")==true){ 
					list.add(repertoire+"."+listefichiers[i].split(".class")[0]);
			}
		}
		for (File r : rep.listFiles()) {
			if(r.isDirectory())
				list.addAll(getClass(repertoire+"."+r.getName(), r));
		}
		return list;
	}

}

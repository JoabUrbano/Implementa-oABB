package view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import model.TreeABB;

public class TreeABBView {

	public static void main(String[] args) {
		TreeABB tree = new TreeABB();
		
		Path file1 = Paths.get("./arquivo1.txt");
		
		try {
			List<String> lines = Files.readAllLines(file1);
			
			for(String line: lines) {
				String[] splitedLine = line.split(" ");
	    		for(String num : splitedLine) {
		        	int i = Integer.parseInt(num);
		        	tree.insert(i, tree.getSource());
	    		}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Path file2 = Paths.get("./arquivo2.txt");
		
		try {
			List<String> lines = Files.readAllLines(file2);
			
			for(String line: lines) {
				String[] splitedLine = line.split(" ");
	        	if(splitedLine.length == 1)
	        		tree.metodo(splitedLine[0], "");
	        	else
	        		tree.metodo(splitedLine[0], splitedLine[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

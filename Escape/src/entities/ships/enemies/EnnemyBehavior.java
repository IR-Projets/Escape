package entities.ships.enemies;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class EnnemyBehavior {

	private static int life = 0;
	private static String filename = null;
	private static List<Couple> listAppear = null;
	
	private static class Couple {
		private final int time;
		private final int pos;
		
		public Couple(int time, int pos) {
			this.pos=pos;
			this.time=time;
		}
	}




	public static void checkHead(BufferedReader bufIn) throws IOException{
		listAppear = new LinkedList<>();
		
		String line;
		while(!(line = bufIn.readLine()).contains("<head>"));
		
		while(!(line = bufIn.readLine()).contains("</head>")){
			
			if(line.startsWith("life")){
				int index = line.indexOf('=')+1;
				while(line.charAt(index) == ' ')
					index++;
				life=Integer.valueOf(line.substring(index, line.length()));
			}
			
			if(line.startsWith("sprite")){
				int index = line.indexOf('=')+1;
				while(line.charAt(index) == ' ')
					index++;
				filename=line.substring(index, line.length());
			}
			
			if(line.startsWith("begin")){
				int index = line.indexOf('=')+1, indexEnd = line.indexOf('t');
				int index2 = line.indexOf(',')+1, index2End = line.indexOf('p');
				while(line.charAt(index) == ' ')
					index++;
				while(line.charAt(index2) == ' ')
					index2++;
				int time = Integer.valueOf(line.substring(index, indexEnd));
				int position = Integer.valueOf(line.substring(index2, index2End));
				listAppear.add(new Couple(time, position));
			}
		}
		
	}


	public void checkBody(BufferedReader bufIn){

	}




	public static void loadEnnemyFromScript(String filename){
		BufferedReader bufIn = null;
			try {
				bufIn = new BufferedReader(new FileReader(filename));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				checkHead(bufIn);
				bufIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

	public static void main(String[] args) {
		loadEnnemyFromScript("../script.sir.txt");
		System.out.println("Apres chargement : ");
		System.out.println("life "+life);
		System.out.println("sprite "+filename);
		System.out.println("begin "+listAppear.get(0).pos + " et "+listAppear.get(0).time);
		System.out.println("begin "+listAppear.get(1).pos + " et "+listAppear.get(1).time);
		
	}
}

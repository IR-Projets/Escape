package entities.ships.enemies;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import entities.weapons.WeaponFactory.WeaponType;


public class EnnemyBehavior {

	private static int life = 0;
	private static String filename = null;
	private static List<Couple> listAppear = null;
	private static List<Behavior> listBehavior = null;

	private enum Move{LB, RB, R, L, B;
	public static Move convert(String moveName){
		switch(moveName){
		case "lb": return LB;
		case "rb": return RB;
		case "r": return R;
		case "l": return L;
		case "b": return B;
		default: return null;
		}
	}};


	private static class Couple {
		private final int time;
		private final int pos;

		public Couple(int time, int pos) {
			this.pos=pos;
			this.time=time;
		}
	}


	private static class Behavior {
		private final int step;
		private final int speed;
		private final Move move;
		private final Move moveWeapon;
		private final WeaponType weaponType;

		public Behavior(int step, int speed, Move move, Move moveWeapon, WeaponType weaponType) {
			this.step=step;
			this.speed=speed;
			this.move=move;
			this.moveWeapon=moveWeapon;
			this.weaponType=weaponType;
		}
	}


	public static String stringAfterSeparator(String name, Character separator, int length){
		int index = name.indexOf(separator)+1;
		while(name.charAt(index) == ' ')
			index++;
		return name.substring(index, length);
	}


	public static void initHead(BufferedReader bufIn) throws IOException{
		listAppear = new LinkedList<>();
		String line;

		while(!(line = bufIn.readLine()).contains("<head>"));

		while(!(line = bufIn.readLine()).contains("</head>")){


			if(line.startsWith("life"))
				life=Integer.valueOf(stringAfterSeparator(line, '=', line.length()));

			if(line.startsWith("sprite"))
				filename=stringAfterSeparator(line, '=', line.length());

			if(line.startsWith("begin")){
				int time = Integer.valueOf(stringAfterSeparator(line, '=', line.indexOf('t')));
				int position = Integer.valueOf(stringAfterSeparator(line, ',', line.indexOf('p')));
				listAppear.add(new Couple(time, position));
			}
		}
	}


	public static void initBody(BufferedReader bufIn) throws IOException{
		listBehavior = new LinkedList<>();
		String line;

		int step = 0;
		int speed = 0;
		Move move = null;
		Move moveWeapon = null;
		WeaponType weaponType = null;
		int nbInit=0;

		while(!(line = bufIn.readLine()).contains("<body>"));

		while(!(line = bufIn.readLine()).contains("</body>")){

			if(line.startsWith("step")){
				step=Integer.valueOf(stringAfterSeparator(line, ':', line.length()));
				nbInit++;
			}

			if(line.startsWith("speed")){
				speed=Integer.valueOf(stringAfterSeparator(line, ':', line.length()));
				nbInit++;
			}

			if(line.startsWith("move")){
				move=Move.convert(stringAfterSeparator(line, ':', line.length()));
				nbInit++;
				System.out.println(move );
			}

			if(line.startsWith("shoot")){
				weaponType=WeaponType.convert(stringAfterSeparator(line, ':', line.indexOf(',')));
				moveWeapon=Move.convert(stringAfterSeparator(line, ',', line.length()));
				nbInit++;
			}

			if(nbInit == 4){
				listBehavior.add(new Behavior(step, speed, move, moveWeapon, weaponType));
				step=speed=nbInit=0;
				move = moveWeapon = null;
				weaponType = null;
			}
		}
	}




	public static void loadEnnemyFromScript(String filename){
		BufferedReader bufIn = null;
		try {
			bufIn = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			initHead(bufIn);
			initBody(bufIn);
			bufIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		loadEnnemyFromScript("../script.sir.txt");
		System.out.println("Apres chargement : ");
		
		System.out.println("HEAD");
		System.out.println("life "+life);
		System.out.println("sprite "+filename);
		System.out.println("begin "+listAppear.get(0).pos + " et "+listAppear.get(0).time);
		System.out.println("begin "+listAppear.get(1).pos + " et "+listAppear.get(1).time);

		System.out.println("BODY");
		Behavior behav = listBehavior.get(0);
		System.out.println("step :"+behav.step);
		System.out.println("speed :"+behav.speed);
		System.out.println("move :"+behav.move);
		System.out.println("shoot :"+behav.weaponType);
		System.out.println("moveshoot :"+behav.moveWeapon);
		
		Behavior behav2 = listBehavior.get(1);
		System.out.println("step :"+behav2.step);
		System.out.println("speed :"+behav2.speed);
		System.out.println("move :"+behav2.move);
		System.out.println("shoot :"+behav2.weaponType);
		System.out.println("moveshoot :"+behav2.moveWeapon);
		
	}
}

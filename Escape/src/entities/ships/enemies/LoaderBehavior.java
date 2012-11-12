package entities.ships.enemies;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import factories.WeaponFactory.WeaponType;

public class LoaderBehavior {

	
	/* Our HeadScript, which represents the Head of the script*/
	public static class HeadScript {
		private final int life;
		private final String filename;
		private final List<Couple> listAppear;
		
		public HeadScript(int life, String filename, List<Couple> listAppear){
			this.life=life;
			this.filename=filename;
			this.listAppear=listAppear;
		}
		
		public int getLife() {
			return life;
		}

		public String getFilename() {
			return filename;
		}

		public List<Couple> getListAppear() {
			return listAppear;
		}

		public static class Couple {
			private final int time;
			private final int pos;

			public Couple(int time, int pos) {
				this.pos=pos;
				this.time=time;
			}

			public int getTime() {
				return time;
			}

			public int getPos() {
				return pos;
			}
		}
	}
	
	/* Our Behavior, which represents the behavior of a ship (movement + weapon) */
	public static class Behavior {
		public enum Move{LB, RB, R, L, B;
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
		
		public int getStep() {
			return step;
		}

		public int getSpeed() {
			return speed;
		}

		public Move getMove() {
			return move;
		}

		public Move getMoveWeapon() {
			return moveWeapon;
		}

		public WeaponType getWeaponType() {
			return weaponType;
		}
	}
	
	
	private static String stringAfterSeparator(String name, Character separator, int length){
		int index = name.indexOf(separator)+1;
		while(name.charAt(index) == ' ')
			index++;
		return name.substring(index, length);
	}


	public static HeadScript initHead(BufferedReader bufIn) throws IOException{
		List<HeadScript.Couple> listAppear = new LinkedList<>();
		int life = 0;
		
		String filename = null, line;

		while(!(line = bufIn.readLine()).contains("<head>"));

		while(!(line = bufIn.readLine()).contains("</head>")){
			if(line.startsWith("life"))
				life=Integer.valueOf(stringAfterSeparator(line, '=', line.length()));

			if(line.startsWith("sprite"))
				filename=stringAfterSeparator(line, '=', line.length());

			if(line.startsWith("begin")){
				int time = Integer.valueOf(stringAfterSeparator(line, '=', line.indexOf('t')));
				int position = Integer.valueOf(stringAfterSeparator(line, ',', line.indexOf('p')));
				listAppear.add(new HeadScript.Couple(time, position));
			}
		}
		return new HeadScript(life, filename, listAppear);
	}

	
	
	public static List<Behavior> initBody(BufferedReader bufIn) throws IOException{
		List<Behavior> listBehavior = new LinkedList<>();
		String line;

		int step = 0;
		int speed = 0;
		Behavior.Move move = null;
		Behavior.Move moveWeapon = null;
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
				move=Behavior.Move.convert(stringAfterSeparator(line, ':', line.length()));
				nbInit++;
			}

			if(line.startsWith("shoot")){
				weaponType=WeaponType.convert(stringAfterSeparator(line, ':', line.indexOf(',')));
				moveWeapon=Behavior.Move.convert(stringAfterSeparator(line, ',', line.length()));
				nbInit++;
			}

			if(nbInit == 4){
				listBehavior.add(new Behavior(step, speed, move, moveWeapon, weaponType));
				step=speed=nbInit=0;
				move = moveWeapon = null;
				weaponType = null;
			}
		}
		return listBehavior;
	}

	
}

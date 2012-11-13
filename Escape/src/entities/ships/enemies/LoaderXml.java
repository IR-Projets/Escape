package entities.ships.enemies;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import entities.ships.enemies.EnemyBehavior.Action;
import entities.ships.enemies.EnemyBehavior.Types;
import factories.WeaponFactory.WeaponType;
import game.Ressources;


public class LoaderXml{


	private class EnemyProperties{
		public String name=null;
		public int life=-1;
		public String nameImage = null;
		public int repeatTime=-1; 
		public List<Action> actions=null;
	}
	/*
	 * Le handler XML
	 */
	private static class EnemyHandler extends DefaultHandler {
		private StringBuilder sb;
		private Action actionTmp;
		private EnemyProperties enemyProperties;

		@Override
		public void characters(char[] argv,int start, int length) throws SAXException{
			String read = new String(argv,start,length);
			if(sb != null) sb.append(read);       
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
			if (qName.equals("enemy")){
				if (enemyProperties.name!=null){
					throw new SAXException("Enemy already init, you can only load one enemy by file");
				}
				return;
			}
			if (qName.equals("life")){
				if (enemyProperties.life!=-1){
					throw new SAXException("life already init, you can only load one life by file");
				}
				return;
			}
			if (qName.equals("image")){
				if (enemyProperties.nameImage!=null){
					throw new SAXException("image already init, you can only load one image by file");
				}
				return;
			}

			if (qName.equals("actions")){
				if (enemyProperties.actions==null) throw new SAXException("Image undefined: define the image first");
				try {
					enemyProperties.repeatTime=Integer.parseInt(attributes.getValue("repeattime"));
				} catch (Exception e){
					throw new SAXException("Error while parsing actions: repeattime");
				}
				return;
			}


			if (qName.equals("move")){
				if (enemyProperties.repeatTime==-1) throw new SAXException("Actions undefined: define the actions first");
				try {
					actionTmp.type=Types.Move;
					actionTmp.beg=Integer.parseInt(attributes.getValue("beg"));
					actionTmp.end=Integer.parseInt(attributes.getValue("end"));
				} catch (Exception e){
					throw new SAXException("Error while parsing actions: repeattime");
				}
				return;
			}

			if (qName.equals("fire")){
				if (enemyProperties.repeatTime==-1) throw new SAXException("Actions undefined: define the actions first");
				if (actionTmp.beg==-1) throw new SAXException("Beg undefined : define the beg first");
				if (actionTmp.end==-1) throw new SAXException("End undefined : define the end first");
				try {
					actionTmp.type=Types.Fire;
					actionTmp.beg=Integer.parseInt(attributes.getValue("beg"));
					actionTmp.end=Integer.parseInt(attributes.getValue("end"));
				} catch (Exception e){
					throw new SAXException("Error while parsing actions: beg and end");
				}
				return;
			}

			if (qName.equals("angle")){
				if (actionTmp.angle !=-1){
					throw new SAXException("angle already init, you can only load one angle by actions");
				}
				return;
			}

			if (qName.equals("velocity")){
				if (actionTmp.velocity !=-1){
					throw new SAXException("velocity already init, you can only load one velocity by actions");
				}
				return;
			}

			if (qName.equals("name")){
				if (actionTmp.name !=null){
					throw new SAXException("name already init, you can only load one name by actions");
				}
				return;
			}
			throw new SAXException("Unknown XML attribute: " + qName);
		}



		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException{
			if (qName.equals("enemy")){
				try{
					enemyProperties.life=Integer.parseInt(sb.toString());
				} catch (Exception e){
					throw new SAXException(qName+" tag should contain an integer value");
				}

				if (qName.equals("image")){
					try{
						enemyProperties.nameImage=sb.toString();
					} catch (Exception e){
						throw new SAXException(qName+" tag should contain a String");
					}
				}

				else if (qName.equals("move")){
					if (actionTmp.angle != -1 && actionTmp.beg != -1 && actionTmp.end != -1 && actionTmp.type != null && actionTmp.velocity != -1)
						throw new SAXException("Move miss constructed : check if the angle, beg, end and velocity are correctly set");
					enemyProperties.actions.add(actionTmp);
					actionTmp = new EnemyBehavior.Action();
					return;
				}

				if (qName.equals("fire")){
					if (actionTmp.angle != -1 && actionTmp.beg != -1 && actionTmp.end != -1 && actionTmp.type != null && actionTmp.velocity != -1 && actionTmp.name != null)
						throw new SAXException("Fire miss constructed : check if the angle, beg, end and velocity are correctly set");
					enemyProperties.actions.add(actionTmp);
					actionTmp = new Action();
					return;
				}

				if (qName.equals("angle")){
					try{
						actionTmp.angle=Double.parseDouble(sb.toString());
					} catch (Exception e){
						throw new SAXException(qName+" tag should contain a double value");
					}
				}

				if (qName.equals("velocity")){
					try{
						actionTmp.velocity=Integer.parseInt(sb.toString());
					} catch (Exception e){
						throw new SAXException(qName+" tag should contain an integer value");
					}
				}

				if (qName.equals("name")){
					try{
						enemyProperties.name=sb.toString();
					} catch (Exception e){
						throw new SAXException(qName+" tag should contain a string value");
					}
				}

			}
		}

		@Override
		public void endDocument() throws SAXException {
			sb = null;
		}

	}
	
	public static EnemyProperties getEnemiesFromXml(InputStream input){
		EnemyHandler eh = new EnemyHandler();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(true);
			SAXParser parser = factory.newSAXParser();
			parser.parse(input, eh);
		} catch (ParserConfigurationException pce) {
			System.err.println("An error occured during parsing");
			System.exit(1);
		} catch (SAXException se){
			System.err.println("Parsing error : "+se.getMessage());
			System.exit(1);
		}  catch (IOException ioe) {
			System.err.println("Read/Write error");
			System.exit(1);
		}
		return eh.enemyProperties;
	}

		/*public static void main(String[] args) {
			
			new File(Ressources.getFile("enemies.xml"));
			
			Graph g = getGraphFromXml(f);
			System.out.println(g.toString());
		}*/

	}
package entities.weapons;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import factories.WeaponFactory.WeaponType;
/**
 * This class represents a list of Item, which contains the main function for show it on a Graphics, and for manage the event with it.
 * 
 * @author Quentin Bernard et Ludovic Feltz
 */


/* <This program is an Shoot Them up space game, called Escape-IR, made by IR students.>
 *  Copyright (C) <2012>  <BERNARD Quentin & FELTZ Ludovic>

 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
public class WeaponItems {


	private final List<WeaponItem> weaponItems;


	public WeaponItems() {
		weaponItems = new LinkedList<>();
		weaponItems.add(new WeaponItem(WeaponType.Missile, 20));
		weaponItems.add(new WeaponItem(WeaponType.Fireball, 20));
		weaponItems.add(new WeaponItem(WeaponType.ShiboleetExtended, 100));
		weaponItems.add(new WeaponItem(WeaponType.Shuriken, 20));

	}

	/**
	 * Returns true if this list contains no elements
	 * @return true if this list contains no elements
	 */
	public boolean isEmpty() {
		return weaponItems.isEmpty();
	}

	/*public List<WeaponItem> getWeaponItems() {
		return weaponItems;
	}*/

	public WeaponItem getCurrentWeaponItem(){
		return weaponItems.get(0);
	}


	public void addWeaponItem(WeaponType weaponType, int quantity){
		for(WeaponItem item: weaponItems){
			if(item.weaponType==weaponType){
				item.addQuantity(quantity);
				return;
			}
		}
		weaponItems.add(new WeaponItem(weaponType, quantity));
	}

	/**
	 * Remove the CurrentItem, by removing the quantity by One, or removing totally the item, depending on his quantity
	 * @return the Item remove
	 */
	public WeaponItem removeCurrentItem() {
		WeaponItem weaponItem = weaponItems.get(0);
		if(weaponItem.getQuantity()>0)
			weaponItem.removeQuantity();
		else{
			weaponItems.remove(0);
			if (weaponItems.size() == 0)
				weaponItems.add(new WeaponItem(WeaponType.Missile, 0));
		}
		return weaponItem;
	}

	public Iterator<WeaponItem> iterator() {
		return weaponItems.iterator();
	}

	public int size() {
		return weaponItems.size();
	}

	public void setIndexInList(int indexOld, int indexNew){
		weaponItems.add(indexNew, weaponItems.remove(indexOld));
	}
}

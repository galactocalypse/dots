/*
	Copyright 2014, Adarsh Yagnik (adarsh_yagnik@yahoo.com)
	
	This file is part of Dots.
	 
	Dots is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Dots is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with 2048(file: LICENSE).  If not, see <http://www.gnu.org/licenses/>.
*/

public class Dots{
	/* Driver class with overall game loop. Whatever that means. */
	
	public static void main(String args[]){
		System.out.println("Copyright 2014, Adarsh Yagnik (adarsh_yagnik@yahoo.com)\n"+
			"Dots is free software, and you are welcome to "+
			"redistribute it within the framework of the GNU Public Licence.\nRead the file LICENSE for details.\n\n");
		
		DGame d = new DGame(2);//parameter refers to dimension of the board
		int turn = 1;
		while(!d.gameOver()){
			d.disp();
			d.dispScores();
			System.out.println("Player" + turn + "'s turn.");
			int a[] = d.parse(d.read());
			if(a == null){
				System.out.println("Invalid input! Try again.");
				continue;
			}
			if(a[3] != turn){
				System.out.println("Out of turn!");
				continue;
			}
			if(d.makeMove((char)a[0], a[1], a[2], a[3])){
				System.out.println("Player" + turn + " scored! Gets another move!");
			}
			else turn = 3 - turn;
		}
		
		d.dispScores();
		int w = d.getWinner();
		if(w == -1)	
			System.out.println("Game still in progress. Developer clueless.");
		else if(w == 0)
			System.out.println("Game tied!");
		else
			System.out.println("Player" + w + " wins!");
	}
}

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

import java.util.*;
import java.io.*;

class DGame{
	private BufferedReader br;
	private boolean u[][];
	private boolean l[][];
	private int o[][];
	private int size;
	private int sp1, sp2;
	private int occ;

	DGame(){
		init(10);
	}
	
	DGame(int size){
		init(size);
	}
	
	public void init(int sz){
		/*
			Initializes the board.
		*/
		size = sz;
		u = new boolean[size+1][size];
		l = new boolean[size][size+1];
		o = new int[size][size];
		occ = 0;
		sp1 = sp2 = 0;
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public boolean gameOver(){
		/*
			Returns true if no more moves can be made.
		*/
		return (occ == size*size);
	}
	
	public void disp(){
		/*
			Displays the board.
		*/
		for(int i = 0; i <= size; i++){
			String s1 = ".";
			String s2 = "";
			for(int j = 0; j <= size; j++){
				if(j < size){
					if(u[i][j])
						s1 += "_.";
					else s1 += " .";
				}
				if(i < size){
					if(l[i][j])
						s2 += "|";
					else s2 += " ";
				}
				if(i < size && j < size){
					if(o[i][j] == 0)
						s2 += " ";
					else s2 += o[i][j];
				}
			}
			System.out.println(s1+"\n"+s2);
		}
	}
	
	private boolean setCell(int i, int j, int pl){
		/*
			Marks cell (i, j) as occupied by player pl if it's available and closed.
		*/
		if(i >= size || j >= size)
			return false;
		if(!isClosed(i, j))
			return false;
		if(o[i][j] != 0)
			return false;
		o[i][j] = pl;
		occ++;
		return true;
	}
	
	private boolean mark(char dir, int i, int j){
		/*
			Marks a border. Dir specifies horizontal or vertical.
		*/
		if(dir > 90)
			dir -= 32;
		if(dir == 'U'){
			if(u[i][j])
				return false;
			u[i][j] = true;
		}
		else if(dir == 'L'){
			if(l[i][j])
				return false;
			l[i][j] = true;
		}
		return true;
	}
	
	private boolean isClosed(int i, int j){
		/*
			Returns true if cell (i, j) has borders on all sides.
		*/
		if(i < 0 || j < 0 || i >= size || j >= size)
			return false;
		return (l[i][j] && l[i][j+1] && u[i][j] && u[i+1][j]);
	}
	
	private boolean checkCell(int i, int j, int pl){
		/*
			Checks if a cell is closed. If yes, updates the score of player pl.
			Called for all cells affected by a move.
		*/
	
		if(isClosed(i, j)){
			if(setCell(i, j, pl)){
				if(pl == 1)
					sp1++;
					else if(pl == 2)
						sp2++;
				return true;
			}
		}
		return false;
	}
	
	public boolean makeMove(char dir, int i, int j, int pl){
		/*
			Most crucial method in the program.
			If possible, makes a move in the direction supplied as dir. 'U' refers to a horizontal line, the top border of the cell (i, j).
			'L' refers to a vertical line, the left border of the cell (i, j).
			Player pl is the player making the move. Scores are updated automatically. Probably not a good thing to do here.
		*/
		boolean scored = false;
		if(mark(dir, i, j)){
			scored = scored || checkCell(i, j, pl);
			if(dir == 'U')
				scored = scored || checkCell(i-1, j, pl);
			else scored = scored || checkCell(i, j-1, pl);	
		}
		return scored;
	}
	
	public int[] parse(String s){
		/*
			Converts the string input from read() to an integer array to supply arguements to makeMove()
		*/
		String arr[] = s.split(" ");
		int a[] = new int[4];
		if(arr.length != 4)
			return null;
		try{
			a[0] = arr[0].charAt(0);
			if(a[0] > 90)
				a[0] -= 32;
			if(a[0] != 'U' && a[0] != 'L')
				return null;
			for(int i = 1; i < arr.length; i++)
				a[i] = Integer.parseInt(arr[i]);
		}
		catch(Exception e){
			return null;
		}
		return a;
	}
	
	public String read(){
		/*
			Reads a line from standard input.
		*/
		if(br == null)
			br = new BufferedReader(new InputStreamReader(System.in));
		try{
			return br.readLine();
		}
		catch(Exception e){
			return null;
		}
	}
	
	public void dispScores(){
		/*
			Displays player scores.
		*/
		System.out.println("Player1: " + sp1 + "\tPlayer2: " + sp2);
	}
	
	public int getWinner(){
		/*
			Returns winner of the game if the game is over.
		*/
		if(!gameOver())
			return -1;
		return (sp1 == sp2)?0:(sp1 > sp2)?sp1:sp2;
	}
}


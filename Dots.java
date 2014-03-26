/*
	Copyright 2014, Adarsh Yagnik (adarsh_yagnik@yahoo.com)
*/
import java.util.*;
import java.io.*;
class DGame{
	BufferedReader br;
	boolean u[][];
	boolean l[][];
	int o[][];
	int size;
	int sp1, sp2;
	int occ;

	DGame(){
		init(10);
	}
	
	DGame(int size){
		init(size);
	}
	
	void init(int sz){
		size = sz;
		u = new boolean[size+1][size];
		l = new boolean[size][size+1];
		o = new int[size][size];
		occ = 0;
		sp1 = sp2 = 0;
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	boolean gameOver(){
		return (occ == size*size);
	}
	
	void disp(){
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
	
	boolean setCell(int i, int j, int pl){
		if(i >= size || j >= size)
			return false;
		if(!l[i][j] || !u[i][j] || !l[i][j+1] || !u[i+1][j])
			return false;
		if(o[i][j] != 0)
			return false;
		o[i][j] = pl;
		occ++;
		return true;
	}
	
	boolean mark(char dir, int i, int j){
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
	
	boolean isClosed(int i, int j){
		if(i < 0 || j < 0 || i >= size || j >= size)
			return false;
		return (l[i][j] && l[i][j+1] && u[i][j] && u[i+1][j]);
	}
	
	boolean checkCell(int i, int j, int pl){
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
	
	boolean makeMove(char dir, int i, int j, int pl){
		boolean scored = false;
		if(mark(dir, i, j)){
			scored = scored || checkCell(i, j, pl);
			if(dir == 'U')
				scored = scored || checkCell(i-1, j, pl);
			else scored = scored || checkCell(i, j-1, pl);	
		}
		return scored;
	}
	
	int[] parse(String s){
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
	
	String read(){
		if(br == null)
			br = new BufferedReader(new InputStreamReader(System.in));
		try{
			return br.readLine();
		}
		catch(Exception e){
			return null;
		}
	}
	
	void dispScores(){
		System.out.println("Player1: " + sp1 + "\tPlayer2: " + sp2);
	}
	
	int getWinner(){
		return (sp1 == sp2)?0:(sp1 > sp2)?sp1:sp2;
	}
}
public class Dots{
	public static void main(String args[]){
		DGame d = new DGame(2);
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
		if(w == 0)
			System.out.println("Game tied!");
		else System.out.println("Player" + w + " wins!");
	}
}

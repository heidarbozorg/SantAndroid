package com.farzaneganco.sant.sant.logic;

import java.util.ArrayList;
 
public class Sabet {
	public int SabetId;
	public String NammeSabet;

	
	public Sabet(int id, String name)
	{
		this.SabetId = id;
		this.NammeSabet = name;
	}
	
	public String toString()
	{
	    return( this.NammeSabet );
	}

	
	public static ArrayList<Sabet> GetOlaviat()
	{
		ArrayList<Sabet> rst = new ArrayList<Sabet>();
		rst.add(new Sabet(66, "عادی"));
		rst.add(new Sabet(67, "فوری"));
		rst.add(new Sabet(68, "آنی"));
		
		return (rst);
	}
}

package it.polito.tdp.food.db;

import it.polito.tdp.food.model.Portion;

public class Adiacenza
{
	private Portion p1; 
	private Portion p2;
	private Integer peso;
	
	public Adiacenza(Portion p1, Portion p2, Integer peso)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.peso = peso;
	}
	public Portion getP1()
	{
		return p1;
	}
	public Portion getP2()
	{
		return p2;
	}
	public Integer getPeso()
	{
		return peso;
	}
	@Override public String toString()
	{
		return String.format("%s - %s (%d)",p1,p2,peso);
	}
	@Override public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
		result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
		return result;
	}
	@Override public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Adiacenza other = (Adiacenza) obj;
		if (p1 == null)
		{
			if (other.p1 != null) return false;
		}
		else if (!p1.equals(other.p1)) return false;
		if (p2 == null)
		{
			if (other.p2 != null) return false;
		}
		else if (!p2.equals(other.p2)) return false;
		return true;
	} 
}

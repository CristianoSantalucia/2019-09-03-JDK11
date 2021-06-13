package it.polito.tdp.food.model;

import java.util.*;

import javax.security.auth.DestroyFailedException;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.*;

import it.polito.tdp.food.db.Adiacenza;
import it.polito.tdp.food.db.FoodDao;

public class Model
{
	private FoodDao dao;
	private Map<Integer, Portion> vertici;
	private Graph<Portion, DefaultWeightedEdge> grafo;

	public Model()
	{
		this.dao = new FoodDao();
	}

	public void creaGrafo(int calorie)
	{
		// ripulisco mappa e grafo
		this.vertici = new HashMap<>();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		/// vertici
		this.dao.getVertici(vertici, calorie);
		Graphs.addAllVertices(this.grafo, this.vertici.values());

		/// archi
		List<Adiacenza> adiacenze = new ArrayList<>(this.dao.getAdiacenze(this.vertici, calorie));
		for (Adiacenza a : adiacenze)
		{
			Graphs.addEdge(this.grafo, a.getP1(), a.getP2(), a.getPeso());
		}
	}

	public int getNumVertici()
	{
		return this.grafo.vertexSet().size();
	}

	public int getNumArchi()
	{
		return this.grafo.edgeSet().size();
	}

	public Collection<Portion> getVertici()
	{
		List<Portion> list = new ArrayList<>(this.grafo.vertexSet());
		list.sort((p1, p2) -> -this.grafo.degreeOf(p1) - this.grafo.degreeOf(p2));
		return list;
	}

	public Collection<DefaultWeightedEdge> getArchi()
	{
		return this.grafo.edgeSet();
	}

	public String getConnessioni(Portion partenza)
	{
		String s = "";
		s += "\nPORZIONI ADIACENTI a \"" + partenza + "\"";
		for (Portion p : Graphs.neighborListOf(this.grafo, partenza))
		{
			s += "\n-" + p;
			s += " (" + this.grafo.getEdgeWeight(this.grafo.getEdge(p, partenza)) + ")";
		}
		return s.equals("\nPORZIONI ADIACENTI a: \"" + partenza + "\"") ? (s + "\n***LISTA VUOTA***") : s;
	}

	// ricorsione

	List<Portion> cammino;
	Integer N;
	double pesoBest;

	public String cammino(Integer N, Portion partenza)
	{
		this.cammino = new ArrayList<>();
		this.N = N;
		this.pesoBest = 0;

		List<Portion> parziale = new ArrayList<>();
		parziale.add(partenza);

		this.cerca(parziale);

		System.out.println(pesoBest);
		System.out.println(cammino);
		return cammino + "\nPeso: " + pesoBest;
	}

	private void cerca(List<Portion> parziale)
	{
		// caso terminale
		if (parziale.size() == this.N)
		{
			double peso = this.calcolaPeso(parziale);
			if (peso >= pesoBest)
			{
				pesoBest = peso;
				this.cammino = new ArrayList<>(parziale);
			}
			return;
		}
		// caso intermedio
		else
		{
			List<Portion> vicini = new ArrayList<>(Graphs.neighborListOf(this.grafo, parziale.get(parziale.size() - 1)));
			for (Portion p : vicini)
			{
				if (!parziale.contains(p))
				{
					parziale.add(p);
					this.cerca(parziale);
					parziale.remove(p);
					System.out.println("qui");
				}
			}
		}
	}

	private double calcolaPeso(List<Portion> parziale)
	{
		double peso = 0.0;
		for (int i = 1; i < parziale.size(); i++)
			peso += this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i - 1), parziale.get(i)));
		return peso;
	}
}

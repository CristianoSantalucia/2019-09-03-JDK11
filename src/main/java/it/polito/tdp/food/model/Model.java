package it.polito.tdp.food.model;

import java.util.*; 
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.*;

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
		this.dao.getVertici(vertici, calorie); //riempio la mappa
		Graphs.addAllVertices(this.grafo, this.vertici.values()); 
		
		/// archi
//		List<Adiacenza> adiacenze = new ArrayList<>(this.dao.getAdiacenze());
//		for (Adiacenza a : adiacenze)
//		{
//			//recupero gli Oggetti dalla chiave della mappa e faccio controlli
//			Object n1 = this.vertici.get(a.getNodo1());
//			Object n2 = this.vertici.get(a.getNodo2());
//			if (n1 != null && n2 != null)
//				Graphs.addEdge(this.grafo, n1, n2, a.getPeso());
//		}
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
		list.sort((p1,p2)->p1.getPortion_display_name().compareTo(p2.getPortion_display_name()));
		return list;
	}
	public Collection<DefaultWeightedEdge> getArchi()
	{
		return this.grafo.edgeSet();
	}  
	
}

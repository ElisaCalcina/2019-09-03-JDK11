package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	Graph<String, DefaultWeightedEdge> grafo;
	FoodDao dao;
	List<Adiacenze> adiacenze;
	
	
	public Model() {
		dao= new FoodDao();
	}
	
	public void creaGrafo(int calorie) {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		adiacenze= this.dao.getAdiacenze();
		
		Graphs.addAllVertices(grafo, dao.getTipoPorzione(calorie));
		
		for(Adiacenze a: adiacenze) {
			if(this.grafo.containsVertex(a.getTipo1()) && this.grafo.containsVertex(a.getTipo2())) {
				Graphs.addEdgeWithVertices(grafo, a.getTipo1(), a.getTipo2(), a.getPeso());
			}
		}
		
		System.out.println(String.format("Grafo creato con %d vertici e %d archi", this.grafo.vertexSet().size(), this.grafo.edgeSet().size()));
	
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<String> getTipoPorzione(Integer calorie) {
		return dao.getTipoPorzione(calorie);
	}
	
	public List<Adiacenze> getConnesse(String tipo){
		List<Adiacenze> result= new ArrayList<>();
		
		List<String> vicini= Graphs.neighborListOf(grafo, tipo);
		for(String s: vicini) {
			double peso= this.grafo.getEdgeWeight(this.grafo.getEdge(tipo, s));
			result.add(new Adiacenze(tipo, s, peso));
		}
		return result;
	}
}

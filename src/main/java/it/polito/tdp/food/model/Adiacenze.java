package it.polito.tdp.food.model;

public class Adiacenze implements Comparable<Adiacenze> {
	String tipo1;
	String tipo2;
	Double peso;
	
	public Adiacenze(String tipo1, String tipo2, Double peso) {
		super();
		this.tipo1 = tipo1;
		this.tipo2 = tipo2;
		this.peso = peso;
	}

	public String getTipo1() {
		return tipo1;
	}

	public void setTipo1(String tipo1) {
		this.tipo1 = tipo1;
	}

	public String getTipo2() {
		return tipo2;
	}

	public void setTipo2(String tipo2) {
		this.tipo2 = tipo2;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Adiacenze o) {
		return this.getTipo1().compareTo(o.getTipo1());
	}

	@Override
	public String toString() {
		return  tipo2 +" "+ peso ;
	}
	
	
	

}

package br.com.treinaweb.java.streams;

public class Empregado {
	
	private long id;
	private String nome;
	private double salario;
	private String departamento;
	
	public Empregado(long id, String nome, double salario, String departamento) {
		this.id = id;
		this.nome = nome;
		this.salario = salario;
		this.departamento = departamento;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public double getSalario() {
		return this.salario;
	}
	
	public void setSalario(double salario) {
		this.salario = salario;
	}
	
	public String getDepartamento() {
		return this.departamento;
	}
	
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	@Override
	public String toString() {
		return nome;
	}
	
	
	
}

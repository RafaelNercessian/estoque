package br.com.cabolider.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Entrada {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data=Calendar.getInstance();
	private Integer quantidadeEntrada;
	private String ordemDeProducao;
	private String tamanho;
	private String descricao;
	private String codigo;
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Calendar getData() {
		return data;
	}
	
	public void setData(Calendar data) {
		this.data = data;
	}
	
	public Integer getQuantidadeEntrada() {
		return quantidadeEntrada;
	}
	
	public void setQuantidadeEntrada(Integer quantidadeEntrada) {
		this.quantidadeEntrada = quantidadeEntrada;
	}
	
	public String getOrdemDeProducao() {
		return ordemDeProducao;
	}
	
	public void setOrdemDeProducao(String ordemDeProducao) {
		this.ordemDeProducao = ordemDeProducao;
	}
	
	public String getTamanho() {
		return tamanho;
	}
	
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}

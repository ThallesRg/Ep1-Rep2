package ep1.processo;

import java.util.ArrayList;
import java.util.List;

import ep1.BCP;

public class Processo {
	
	private Estado estado = Estado.PRONTO;
	private List<String> instrucoes = new ArrayList<String>();
	private String nome;
	
    private int creditos;
    private int flag;
    
    private BCP bcp = new BCP(this);

    
	public Processo(Estado estado, List<String> instrucoes, String nome, int creditos, int flag) {
		this.estado = estado;
		this.nome = nome;
		this.creditos = creditos;
		this.flag = flag;
		this.instrucoes.addAll(instrucoes);
		bcp.setNome(nome);
	}


	public Estado getEstado() {
		return estado;
	}

	public List<String> getInstrucoes() {
		return instrucoes;
	}

	public String getNome() {
		return nome;
	}

	public int getCreditos() {
		return creditos;
	}

	public int getFlag() {
		return flag;
	}


	public void setEstado(Estado estado) {
		this.estado = estado;
	}


	@Override
	public String toString() {
		return "Processo [estado=" + estado + ", instrucoes=" + instrucoes + ", nome=" + nome + ", creditos=" + creditos
				+ ", flag=" + flag + "]\n";
	}

	public BCP getBcp() {
		return bcp;
	}

 
}
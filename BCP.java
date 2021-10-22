package ep1;

import ep1.processo.Processo;

public class BCP {
	
	private String nome; // variavel que armazena o nome do processo
	private int registX; // variavel que corresponde ao registrador x
	private int registY; // vatiavel que corresponde ao registrador y
	private int contadorPrograma = 1; //ignorando primeira linha (nome)
	
	private int sairBloquados = 0; // variavel axiliar no controle de saida do BCP na Tabela de Processos
	
	Processo processo;
	
	public BCP(Processo processo) { // Contrutor com referencia ao processo criado
		this.processo = processo;
	}

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public int getRegistX() {
		return registX;
	}


	public void setRegistX(int registX) {
		this.registX = registX;
	}


	public int getRegistY() {
		return registY;
	}


	public void setRegistY(int registY) {
		this.registY = registY;
	}


	public int getContadorPrograma() {
		return contadorPrograma;
	}


	public void setContadorPrograma(int contadorPrograma) {
		this.contadorPrograma = contadorPrograma;
	}
	
	public void decrementaSairBloquados() {
		sairBloquados--;
	}

	public int getSairBloquados() {
		return sairBloquados;
	}

	public void setSairBloquados(int sairBloquados) {
		this.sairBloquados = sairBloquados;
	}
	
	public Processo getProcesso() {
		return processo;
	}

	@Override
	public String toString() {
		return "BCP [nome=" + nome + ", registX=" + registX + ", registY=" + registY + ", contadorPrograma="
				+ contadorPrograma + "]";
	}

	
	
}


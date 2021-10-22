package ep1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import ep1.processo.Estado;

public class Escalonador {

	static int processosRodando = 0; //Variável de controle do número de processos para remover de bloqueados
	
	static double qtdTrocas = 0; //variável que armazena quantidade de trocas dos processos
	static double qtdInstrucoes = 0; //variável que armazena quantidade de instruções executadas
	
	public static void main(String[] args) throws FileNotFoundException {

		LeituraArquivo arq = new LeituraArquivo(); //arquivo que armazena o output da função de leitura dos processos txt

		int quantum = arq.getQuantum(); //variavel que armazena o quantum definido no txt.


		if (quantum < 10 && quantum > 0) { //Mudança do output padrão do print do console para escrever em arquivo e gerar os logs
			System.setOut(new PrintStream(new FileOutputStream("log0" + quantum + ".txt")));
		} else {
			System.setOut(new PrintStream( new FileOutputStream("log"+quantum + ".txt")));
		}
		
		for (int i=0; i<TabelaDeProcessos.prontos.size(); i++) { //inicializando processos
			System.out.println("Carregando " + TabelaDeProcessos.prontos.get(i).getNome());
		}

		while (TabelaDeProcessos.prontos.size() > 0 || TabelaDeProcessos.bloqueados.size() > 0) { //condicional que verifica se existe processos na TabelaDeProcessos
			if (TabelaDeProcessos.prontos.size() > 0) {
				BCP bcpAtual = TabelaDeProcessos.prontos.get(0); //pegando referência para o primeiro bcp da tabela de processos
				System.out.println("Executando " + bcpAtual.getNome());
				List<String> listaInstrucoes = TabelaDeProcessos.prontos.get(0).getProcesso().getInstrucoes(); //pegando referência das instruções do primeiro bcp
				
				for (int auxQuantum = 0; auxQuantum < quantum; auxQuantum++) { //loop que torna o escalonador sincrono com o valor do quantum
					if (TabelaDeProcessos.prontos.size() > 0) {

						int ContadorPrograma = TabelaDeProcessos.prontos.get(0).getContadorPrograma(); //variável para pegar contador do programa


						if (listaInstrucoes.get(ContadorPrograma).contains("E/S")) { //Verifica se a intrução corresponde a parada de E/S
							System.out.println("Interrompendo " + bcpAtual.getNome()+ " após " + (auxQuantum + 1) + " instrução(ões)"); //printa instrução com base no contador do programa
							incrementaContador(ContadorPrograma); //incrementa o contador
							TabelaDeProcessos.adicionaBloqueados(bcpAtual); // adiciona o bcp atual lista de bloqueados
							qtdInstrucoes++; //incrementa variável que contabiliza a quantidade de intruções executadas
							break;

						} else if (listaInstrucoes.get(ContadorPrograma).contains("X=")) { //Verifica se a intrução corresponde ao "X"
							bcpAtual.setRegistX(Integer.parseInt(listaInstrucoes.get(ContadorPrograma).substring(2))); //Pega o elemento e armazena no bcp
							qtdInstrucoes++;
							incrementaContador(ContadorPrograma);
						} else if (listaInstrucoes.get(ContadorPrograma).contains("Y=")) { //Verifica se a intrução corresponde ao "Y"
							bcpAtual.setRegistY(Integer.parseInt(listaInstrucoes.get(ContadorPrograma).substring(2)));//Pega o elemento e armazena no bcp
							qtdInstrucoes++;
							incrementaContador(ContadorPrograma);

						} else if (listaInstrucoes.get(ContadorPrograma).contains("SAIDA")) { //verifica se a intrução corresponde a SAIDA do processo. 
							System.out.println(bcpAtual.getNome() + " terminado X=" + bcpAtual.getRegistX()+ " Y= " + bcpAtual.getRegistY() ); // LOG
							incrementaContador(ContadorPrograma);
							TabelaDeProcessos.prontos.remove(bcpAtual); // metodo que remove o BCP da lista de prontos
							bcpAtual.getProcesso().setEstado(Estado.FINALIZADO); // seta estado do processo para FINALIZADO
							qtdInstrucoes++;
							break;
						} else if (listaInstrucoes.get(ContadorPrograma).contains("COM")) { //Verifica se a intrução corresponde ao "COM"
							qtdInstrucoes++;
							incrementaContador(ContadorPrograma);
						}

					} else {
						break;
					}
				}
				
				qtdTrocas++;
				
				

				if (bcpAtual.getProcesso().getEstado() != Estado.FINALIZADO 
						&& bcpAtual.getProcesso().getEstado() != Estado.BLOQUEADO) { //Verifica se o estado é diferente de finalizado e bloqueado
					TabelaDeProcessos.prontos.remove(bcpAtual); //remove o bcp da tabela de prontos
					TabelaDeProcessos.prontos.add(bcpAtual); //adiciona o bcp na tabela de pronto (manda para o final da fila)
				}

			} 
			
			incrementaProcessosRodando(); //incrementa a variavel 'processos rodando' que remove o elemento bloqueado caso seu numero chegue a 0
		}
		
		System.out.println("Media de trocas: " + qtdTrocas/quantum); // LOG
		System.out.println("Media de instruoes: " + qtdInstrucoes/10); //LOG
		System.out.println("Quantum: " + quantum); //LOG
		

	}

	public static void incrementaContador(int ContadorPrograma) { // incrementar variavel de controle da saida do BCP da lista de Processos
		TabelaDeProcessos.prontos.get(0).setContadorPrograma(ContadorPrograma + 1);
	}

	public static void incrementaProcessosRodando() { 
		processosRodando++;
		if (processosRodando % 2 == 0 && TabelaDeProcessos.bloqueados.size() > 0) {
			for (int i = 0; i < TabelaDeProcessos.bloqueados.size(); i++) {
				TabelaDeProcessos.bloqueados.get(i)
						.setSairBloquados(TabelaDeProcessos.bloqueados.get(i).getSairBloquados() - 1);
				if (TabelaDeProcessos.bloqueados.get(i).getSairBloquados() == 0) {
					TabelaDeProcessos.removeBloqueados(TabelaDeProcessos.bloqueados.get(i));
				}
			}
		}
	}
}

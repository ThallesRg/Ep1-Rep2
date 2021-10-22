package ep1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import ep1.processo.Estado;

public class Escalonador {

	static int processosRodando = 0;
	
	static double qtdTrocas = 0;
	static double qtdInstrucoes = 0;
	
	public static void main(String[] args) throws FileNotFoundException {

		LeituraArquivo arq = new LeituraArquivo();

		int quantum = arq.getQuantum();


		if (quantum < 10 && quantum > 0) {
			System.setOut(new PrintStream(new FileOutputStream("log0" + quantum + ".txt")));
		} else {
			System.setOut(new PrintStream( new FileOutputStream("log"+quantum + ".txt")));
		}
		
		for (int i=0; i<TabelaDeProcessos.prontos.size(); i++) {
			System.out.println("Carregando " + TabelaDeProcessos.prontos.get(i).getNome());
		}

		while (TabelaDeProcessos.prontos.size() > 0 || TabelaDeProcessos.bloqueados.size() > 0) {
			if (TabelaDeProcessos.prontos.size() > 0) {
				BCP bcpAtual = TabelaDeProcessos.prontos.get(0);
				System.out.println("Executando " + bcpAtual.getNome());
				List<String> listaInstrucoes = TabelaDeProcessos.prontos.get(0).getProcesso().getInstrucoes();
				
				for (int auxQuantum = 0; auxQuantum < quantum; auxQuantum++) {
					if (TabelaDeProcessos.prontos.size() > 0) {

						int ContadorPrograma = TabelaDeProcessos.prontos.get(0).getContadorPrograma();

						// incrementaProcessosRodando(bcpAtual);

						if (listaInstrucoes.get(ContadorPrograma).contains("E/S")) {
							System.out.println("Interrompendo " + bcpAtual.getNome()+ " após " + (auxQuantum + 1) + " instrução(ões)");
							incrementaContador(ContadorPrograma);
							TabelaDeProcessos.adicionaBloqueados(bcpAtual);
							// TabelaDeProcessos.prontos.remove(bcpAtual);
							qtdInstrucoes++;
							break;

						} else if (listaInstrucoes.get(ContadorPrograma).contains("X=")) {
							bcpAtual.setRegistX(Integer.parseInt(listaInstrucoes.get(ContadorPrograma).substring(2)));
							qtdInstrucoes++;
							incrementaContador(ContadorPrograma);
						} else if (listaInstrucoes.get(ContadorPrograma).contains("Y=")) {
			
							bcpAtual.setRegistY(Integer.parseInt(listaInstrucoes.get(ContadorPrograma).substring(2)));
							qtdInstrucoes++;
							incrementaContador(ContadorPrograma);

						} else if (listaInstrucoes.get(ContadorPrograma).contains("SAIDA")) {
							System.out.println(bcpAtual.getNome() + " terminado X=" + bcpAtual.getRegistX()+ " Y= " + bcpAtual.getRegistY() );
							incrementaContador(ContadorPrograma);
							TabelaDeProcessos.prontos.remove(bcpAtual);
							bcpAtual.getProcesso().setEstado(Estado.FINALIZADO);
							qtdInstrucoes++;
							break;
						} else if (listaInstrucoes.get(ContadorPrograma).contains("COM")) {
							qtdInstrucoes++;
							incrementaContador(ContadorPrograma);
						}

						// System.out.println(TabelaDeProcessos.prontos.get(0).getContadorPrograma());
					} else {
						break;
					}
				}
				
				qtdTrocas++;
				
				

				if (bcpAtual.getProcesso().getEstado() != Estado.FINALIZADO
						&& bcpAtual.getProcesso().getEstado() != Estado.BLOQUEADO) {
					TabelaDeProcessos.prontos.remove(bcpAtual);
					TabelaDeProcessos.prontos.add(bcpAtual);
				}

			} 
			
			incrementaProcessosRodando();
		}
		
		System.out.println("Media de trocas: " + qtdTrocas/quantum);
		System.out.println("Media de instruoes: " + qtdInstrucoes/10);
		System.out.println("Quantum: " + quantum);
		

	}

	public static void incrementaContador(int ContadorPrograma) {
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

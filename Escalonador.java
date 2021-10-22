package ep1;

import java.io.FileNotFoundException;
import java.util.List;

public class Escalonador {
	
	static int processosRodando = 0;
	
	public static void main(String[] args) throws FileNotFoundException{
		
		
		
		LeituraArquivo arq = new LeituraArquivo();
		
		int quantum = arq.getQuantum();
		
		
		
		//System.out.println(TabelaDeProcessos.prontos.get(0));

		while(TabelaDeProcessos.prontos.size() > 0 || TabelaDeProcessos.bloqueados.size() > 0) {			
			BCP bcpAtual  = TabelaDeProcessos.prontos.get(0);
			List<String> listaInstrucoes = TabelaDeProcessos.prontos.get(0).getProcesso().getInstrucoes();
			for (int auxQuantum=0; auxQuantum<quantum; auxQuantum++) {
				if (TabelaDeProcessos.prontos.size() > 0) {

					int ContadorPrograma = TabelaDeProcessos.prontos.get(0).getContadorPrograma();
					
					//incrementaProcessosRodando(bcpAtual);
					
					if (listaInstrucoes.get(ContadorPrograma).contains("E/S")) {
						System.out.println("Bloqueando Na E/S o " + bcpAtual.getNome());
						incrementaContador(ContadorPrograma);
						TabelaDeProcessos.adicionaBloqueados(bcpAtual);
						//TabelaDeProcessos.prontos.remove(bcpAtual);
						
					} else if (listaInstrucoes.get(ContadorPrograma).contains("X=")) {
						System.out.println("Executando o " + bcpAtual.getNome());
						bcpAtual.setRegistX(Integer.parseInt(listaInstrucoes.get(ContadorPrograma).substring(2)));
						incrementaContador(ContadorPrograma);
					} else if (listaInstrucoes.get(ContadorPrograma).contains("Y=")) {
						System.out.println("Executando o " + bcpAtual.getNome());
						bcpAtual.setRegistY(Integer.parseInt(listaInstrucoes.get(ContadorPrograma).substring(2)));
						incrementaContador(ContadorPrograma);
						
					} else if (listaInstrucoes.get(ContadorPrograma).contains("SAIDA")) {
						System.out.println("Saindo do programa " + bcpAtual.getNome());
						incrementaContador(ContadorPrograma);
						TabelaDeProcessos.prontos.remove(bcpAtual);
					} else if (listaInstrucoes.get(ContadorPrograma).contains("COM")) {
						System.out.println("Executando o " + bcpAtual.getNome());
						incrementaContador(ContadorPrograma);
					}
					
					
					
					//System.out.println(TabelaDeProcessos.prontos.get(0).getContadorPrograma());
				} else {
					break;
				}
			}
			TabelaDeProcessos.prontos.remove(bcpAtual);
			processosRodando++;
			incrementaProcessosRodando(bcpAtual);
			
		}
		
		System.out.println(arq.getProcessos().get(7).getBcp());
		System.out.println(processosRodando);
		
		
	}
	
	
	public static void incrementaContador(int ContadorPrograma) {
		TabelaDeProcessos.prontos.get(0).setContadorPrograma(ContadorPrograma + 1);
	}
	

	
	public static void incrementaProcessosRodando (BCP bcpAtual) {
        processosRodando++;
        if( processosRodando % 2 == 0 && TabelaDeProcessos.bloqueados.size() > 0) {
            for(int i =0; i < TabelaDeProcessos.bloqueados.size();i++){
                TabelaDeProcessos.bloqueados.get(i).setSairBloquados(TabelaDeProcessos.bloqueados.get(i).getSairBloquados() - 1);
                if(TabelaDeProcessos.bloqueados.get(i).getSairBloquados() == 0){
                    TabelaDeProcessos.removeBloqueados(bcpAtual);
                }
            }
        }
    }
}
        
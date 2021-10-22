package ep1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ep1.processo.Estado;
import ep1.processo.Processo;

public class LeituraArquivo {
	
	private final int AUX = 10; //variável auxiliar para quantidade de programas

	private int quantum; //variável auxiliar quantum
	private List<Processo> processos = new ArrayList<Processo>();

	public LeituraArquivo() throws FileNotFoundException {
		lerQuantum(); 	//faz leitura do quantum
		lerProgramas(); //faz leitura dos programas
	}

	private void lerQuantum() {
		File file = new File("processos/quantum.txt"); // Leitura dos dados do TXT de quantum.
		try {
			Scanner sc = new Scanner(file);
			quantum = sc.nextInt(); // Armazena valor do quantum na variável.
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	private void lerProgramas() { //método lê todos os programas, cria o BCP e adiciona na tebela de processos
		List<String> listaAux = new ArrayList<String>();
		FileReader file;
		Scanner sc;
		
		try {
			for (int i=1; i<=AUX; i++) {
				
				if (i < 10) {
					file = new FileReader("processos/0" + i + ".txt");
				} else {
					file = new FileReader("processos/10.txt");
				}
				
				sc = new Scanner(file);
				while (sc.hasNextLine()) {
					listaAux.add(sc.nextLine());
				}
			
				
				processos.add(new Processo(Estado.PRONTO, listaAux, listaAux.get(0)));
				TabelaDeProcessos.prontos.add(processos.get(i-1).getBcp());
				
				listaAux.clear();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public int getQuantum() {
		return quantum;
	}

	public List<Processo> getProcessos() {
		return processos;
	}
	
	
}

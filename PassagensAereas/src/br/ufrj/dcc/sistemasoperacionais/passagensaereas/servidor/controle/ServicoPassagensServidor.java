package br.ufrj.dcc.sistemasoperacionais.passagensaereas.servidor.controle;

public class ServicoPassagensServidor {
	
	private Trechos trechos;

	public ServicoPassagensServidor() {
		trechos = new Trechos();
		trechos.adicionaTrecho(1, "RJ-SP", 100);
		trechos.adicionaTrecho(2, "RJ-SA", 50);
		trechos.adicionaTrecho(3, "RJ-BH", 30);
		trechos.adicionaTrecho(4, "RJ-BR", 100);
		trechos.adicionaTrecho(5, "RJ-MN", 20);
	}
    
    public synchronized String obtemTrechos() {
		return trechos.obtemTrechos();
    }
    
    public synchronized int obtemVagasNoTrecho(int trecho) {
		Trecho objTrecho = trechos.getTrecho(trecho);
		return (objTrecho != null ? objTrecho.getVagas() : -1);
    }
    
    public synchronized boolean reservaTrecho(int numeroDeAssentos, int trecho, Object cliente) {
		Trecho objTrecho = trechos.getTrecho(trecho);
		return (objTrecho != null ? objTrecho.adicionaReserva(cliente, numeroDeAssentos) : false);
    }   
    
    public synchronized boolean compraTrecho(int trecho, int numeroDeAssentos, Object cliente) {
		Trecho objTrecho = trechos.getTrecho(trecho);
		return (objTrecho != null ? objTrecho.efetuaCompra(cliente, numeroDeAssentos) : false);
    }
    
    public synchronized int consultaReserva(int trecho){
		Trecho objTrecho = trechos.getTrecho(trecho);
		return (objTrecho != null ? objTrecho.getReservas() : -1);
    }
    
    public synchronized int consultaCompras(int trecho){
		Trecho objTrecho = trechos.getTrecho(trecho);
		return (objTrecho != null ? objTrecho.getCompras() : -1);
    }

	public Trechos getTrechos() {
		return trechos;
	}
	
}

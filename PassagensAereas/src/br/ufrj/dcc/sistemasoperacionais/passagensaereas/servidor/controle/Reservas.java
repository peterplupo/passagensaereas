package br.ufrj.dcc.sistemasoperacionais.passagensaereas.servidor.controle;

import java.util.ArrayList;

public class Reservas {

	private ArrayList<Reserva> reservas = new ArrayList<Reserva>();
	private Trecho trecho;
	
	public Reservas(Trecho trecho){
		this.trecho = trecho;
	}

	private Reserva obtemNovaReservaCliente(Object cliente){
		int i = buscaReserva(cliente);
		if (i > -1) 
			return reservas.get(i);
		else {
			Reserva resultado = new Reserva(cliente, 0);
			reservas.add(resultado);
			return resultado;
		}
	}
	
	private int buscaReserva(Object cliente){
		boolean bAchou = false;
		int i = 0;
		while ((i < reservas.size()) && !bAchou) {
			bAchou = (cliente == reservas.get(i).GetCliente());
			i++;
		}
		return (bAchou ? i-1 : -1);
	}
	
	public boolean adicionaReserva(Object cliente, int numeroDeAssentos){
		boolean resultado = false;
		int numReservasRealizadas = trecho.getNumeroReservas(); 
		int numReservasPossiveis = (int)(trecho.getNumeroAssentos() * 1.1);		
		
		if ((numReservasRealizadas + numeroDeAssentos) <= numReservasPossiveis) {
			Reserva reserva = obtemNovaReservaCliente(cliente);			 
			reserva.SetNumeroAssentos(reserva.GetNumeroAssentos() + numeroDeAssentos);
			trecho.setNumeroReservas(numReservasRealizadas + numeroDeAssentos);
			new TimerLiberacaoReserva(reserva, this, trecho, 15);
			resultado = true;
		}
		return resultado;		
	}
	
	public void removeReserva(Reserva reserva){
		if (reservas.indexOf(reserva) > -1) {
			trecho.setNumeroReservas(trecho.getNumeroReservas() - reserva.GetNumeroAssentos());
			reservas.remove(reserva);
		}
	}
	
	public boolean efetuaCompra(Object cliente, int numeroDeAssentos) {
		boolean resultado = false;
		int i = buscaReserva(cliente);
		if (i > -1) {
			Reserva reserva = reservas.get(i);
			if (reserva.GetNumeroAssentos() >= numeroDeAssentos) {
				int numAssentosComprados = trecho.getNumeroCompras(); 
				if ((numAssentosComprados + numeroDeAssentos) < trecho.getNumeroAssentos()) {
					trecho.setNumeroCompras(numAssentosComprados + numeroDeAssentos);
					resultado = true;
				}
				removeReserva(reserva);
			}
		}
		return resultado;
	}

}
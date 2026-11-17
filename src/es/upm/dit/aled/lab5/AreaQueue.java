package es.upm.dit.aled.lab5;

import java.util.LinkedList;
import java.util.Queue;

import es.upm.dit.aled.lab5.gui.Position2D;

/**
 * Extension of Area that maintains a strict queue of the Patients waiting to
 * enter in it. After a Patient exits, the first one in the queue will be
 * allowed to enter.
 * 
 * @author rgarciacarmona
 */
public class AreaQueue extends Area {
	
	private Queue<Patient> waitQueue;
	
	public AreaQueue(String name, int time, int capacity, Position2D position) {
		super(name, time, capacity, position);
		this.waitQueue = new LinkedList<>();
	}

	@Override
	public synchronized void enter(Patient p) {
		//TODO
		try {
			waitQueue.add(p);
			waiting++;
			System.out.println("Paciente " + p.getNumber() + " intentado entrar en " + name);
			while(waitQueue.peek() != p || numPatients >= capacity) {
				System.out.println("Paciente " + p.getNumber() + " esperando en " + name 
						+ ". Primero en la cola: Paciente " + waitQueue.peek().getNumber());
				wait();
			} 
			waitQueue.remove();
			numPatients++;
			waiting--;
			System.out.println("Paciente " + p.getNumber() + " ha entrado en " + name);
			
		} catch(InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

}

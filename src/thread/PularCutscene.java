package thread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PularCutscene extends Thread{
	
	JPanel painel;
	JButton botaoPular;
	
	public PularCutscene(JPanel painel) {
		this.painel = painel;	
		
		botaoPular = new JButton("Pular");
		botaoPular.setBounds(30, 30, 80, 30);
		painel.add(botaoPular);
	}
	
	public void run(){
		
		botaoPular.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CutsceneThread.tempoTransicaoGif = 0;
				System.out.println("PULAR");
			}
		});
	}
}
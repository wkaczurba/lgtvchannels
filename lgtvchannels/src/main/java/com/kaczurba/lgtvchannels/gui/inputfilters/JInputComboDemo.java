package com.kaczurba.lgtvchannels.gui.inputfilters;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Arrays;
import java.util.LinkedHashSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class JInputComboDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7827568466193954197L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInputComboDemo frame = new JInputComboDemo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JInputComboDemo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Example begins here: 
		DataModel<String> dataModel = new DataModel<>();
		dataModel.add("isSkipped", new LinkedHashSet<String>( Arrays.asList("all", "0", "1")));
		dataModel.add("isDeleted", new LinkedHashSet<String>( Arrays.asList("all", "0", "1")));
		dataModel.add("isBlocked", new LinkedHashSet<String>( Arrays.asList("all", "0", "1")));
		
		JInputCombo<String> inputCombo = new JInputCombo<>(dataModel);
		
		inputCombo.setActionListener( (e) -> {
			if (e.getSource() == inputCombo) {
				System.out.println("[update] 1. event: " + e);
				System.out.println("         2. textField: " + inputCombo.getTextFieldText());
				System.out.println("         3. combos: " + inputCombo.getComboBoxesValues());
			}
		});

		contentPane.add(inputCombo, BorderLayout.CENTER);
		pack();		
		
	}


}

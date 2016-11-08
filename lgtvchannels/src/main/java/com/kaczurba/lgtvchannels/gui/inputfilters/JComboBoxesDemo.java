package com.kaczurba.lgtvchannels.gui.inputfilters;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import javax.swing.JComboBox;

public class JComboBoxesDemo extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5052357058546252462L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.setVisible(true);
					
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setBounds(100, 100, 400, 400);
					JPanel contentPane = new JPanel();
					contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
					frame.setContentPane(contentPane);
					
					JComboBoxes<String> filters = new JComboBoxes<>();
					
					DataModel<String> dataModel = new DataModel<>();
					dataModel.add("isSkipped", new LinkedHashSet<String>( Arrays.asList("0", "1")));
					dataModel.add("isDeleted", new LinkedHashSet<String>( Arrays.asList("0", "1")));
					dataModel.add("isBlocked", new LinkedHashSet<String>( Arrays.asList("0", "1")));
					
					HashMap<String, List<String>> labelsAndValues2 = new HashMap<>();
					labelsAndValues2.put("zz", Arrays.asList("0", "1"));
					labelsAndValues2.put("aa", Arrays.asList("0", "1"));
					labelsAndValues2.put("bb", Arrays.asList("0", "1"));
					
					filters.setDataModel(dataModel);

					filters.setActionListener( (e) -> {
						Object obj = e.getSource();
						
						if (obj instanceof JComboBox) {
							JComboBox<?> combo = (JComboBox<?>) obj;
							System.out.println("MAIN: " + combo.getName() + " has changed to: " + combo.getSelectedItem());
						}						
					});
					contentPane.add(filters, BorderLayout.CENTER);

					// ** THIS SHOULD CASE ILLEGAL STATE EXCEPTION **					
					dataModel.add("isFive", new LinkedHashSet<String>( Arrays.asList("0", "1")));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}

}

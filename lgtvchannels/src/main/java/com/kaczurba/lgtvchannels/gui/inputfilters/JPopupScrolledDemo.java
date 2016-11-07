package com.kaczurba.lgtvchannels.gui.inputfilters;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import javax.swing.JList;
import javax.swing.JMenuItem;

// Todo extend JList to implement pop-up functionality.

public class JPopupScrolledDemo<E> extends JList<E> {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.setVisible(true);
					
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					//setBounds(100, 100, 450, 300);
					
					JPanel contentPane = new JPanel();
					contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
					frame.setContentPane(contentPane);
					
					// This starts from below:
					JPopupScrolledJList<Object> jList = new JPopupScrolledJList<>();	
					//jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION); 
					DefaultListModel<Object> model = new DefaultListModel<>();
					model.addElement("One");
					model.addElement("Two");
					model.addElement("Threee");
					model.addElement("Four");
					model.addElement("Five");
					model.addElement("Six");
					jList.setModel(model);
					
					// How to add....?

					
					BiConsumer<JMenuItem, JPopupScrolledJList.Selection<Object> > rightClickAction = (menuItem, selection) -> { 
				    	System.out.println("Got right-click on: " + menuItem.getText() + 
				    			"; All selected items: " + selection);
				    };
				    
				    jList.addPopupJItem("Set invisible", rightClickAction);
				    jList.addPopupJItem("Set visible", rightClickAction);
				    jList.addPopupJItem("Properties", rightClickAction);
	
				    // Wrapping everything with scrollPane:
					JScrollPane scrollPane = new JScrollPane();
					scrollPane.setViewportView(jList);
					contentPane.add(scrollPane);
					
				    

//					contentPane.add(jList);
					frame.pack();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

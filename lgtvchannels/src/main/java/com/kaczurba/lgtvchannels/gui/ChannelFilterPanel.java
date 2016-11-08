package com.kaczurba.lgtvchannels.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.function.BiConsumer;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.kaczurba.lgtvchannels.gui.inputfilters.DataModel;
import com.kaczurba.lgtvchannels.gui.inputfilters.JInputCombo;
import com.kaczurba.lgtvchannels.gui.inputfilters.JPopupScrolledJList;
import com.kaczurba.lgtvchannels.xmls.Item;

// TODO: Should ChannelFilterPanel be not refactored? <E> and replace <Item> ?
public class ChannelFilterPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8529438975358786663L;
	private final JPopupScrolledJList<Item> jList = new JPopupScrolledJList<>();
//	private JInputCombo<Item> inputCombo;
	private JInputCombo<String> inputCombo;
	private DataModel<String> dataModel;
	private JScrollPane leftPaneListScroller = new JScrollPane();
	private DefaultListModel<Item> leftListModel = new DefaultListModel<>();
    private JTextArea txtrChannelDetails = new JTextArea();
	private JScrollPane txtrChannelDetailsScrollPane = new JScrollPane();
	private JLabel lblChannelDetails = new JLabel("Channel details:");
	private final JLabel lblStatus = new JLabel("Status:");
	
	public ChannelFilterPanel() {
		// Left-Panel's stuff:
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// ****************** Setting data model - example
		DataModel<String> dataModel = new DataModel<>();
		dataModel.add("isSkipped", new LinkedHashSet<String>( Arrays.asList("all", "0", "1")));
		dataModel.add("isDeleted", new LinkedHashSet<String>( Arrays.asList("all", "0", "1")));
		dataModel.add("isBlocked", new LinkedHashSet<String>( Arrays.asList("all", "0", "1")));
		
		// Experiment: Sorting channels:
		// Consider adding sorting of the stuff:
		dataModel.add("sort by:", new LinkedHashSet<String>( Arrays.asList("original", "by name")));
		
		setDataModel(dataModel);
		
		// TODO: Consider removing; This is business logic. Should not be here.
		inputCombo.setActionListener( (e) -> {
			if (e.getSource() == inputCombo) {
				System.out.println("[update] 1. event: " + e);
				System.out.println("         2. textField: " + inputCombo.getTextFieldText());
				System.out.println("         3. combos: " + inputCombo.getComboBoxesValues());
			}
		});		
		// ****************** End of the example.
				

		add(leftPaneListScroller);
		leftPaneListScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		leftPaneListScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);		
		leftPaneListScroller.setPreferredSize(new Dimension(250, 250));
		jList.setVisibleRowCount(20);

		leftPaneListScroller.setViewportView(jList);
		
		// Setting model for the list:
		jList.setModel(leftListModel);

		demoAddPopUpItems();
		
		
		// TODO: Consider removing; This is business logic; This should be taken out of here.
	    jList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				Item it = jList.getSelectedValue();

				txtrChannelDetails.setText("");
				//txtrChannelDetails.setText(  );
				if (it != null) {
					it.getAsMap().forEach( (k,v) -> txtrChannelDetails.append(k + ":" + v + "\r\n" ) );
				}
				
				txtrChannelDetailsScrollPane.repaint();
			}					
		});

		add(lblChannelDetails);
		txtrChannelDetails.setRows(10);

		txtrChannelDetailsScrollPane.setViewportView(txtrChannelDetails);
		add(txtrChannelDetailsScrollPane);
						
		txtrChannelDetails.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtrChannelDetails.setText("Hello.");		
		
		add(lblStatus);
	}
	
	public void demoAddPopUpItems() {
		// Taken from demo:
	    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */				
		// FIXME: These stuff is to be removed:
		BiConsumer<JMenuItem, JPopupScrolledJList.Selection<Item> > rightPopupClickAction = (menuItem, selection) -> { 
	    	System.out.println("Got right-click on: " + menuItem.getText() + 
	    			"; All selected items: " + selection);
	    };
	    
	    jList.addPopupJItem("Set invisible", rightPopupClickAction);
	    jList.addPopupJItem("Set visible", rightPopupClickAction);
	    jList.addPopupJItem("Properties", rightPopupClickAction);				
		// ---------------------------------------------------------------
	    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	}
	
	public void setDataModel(DataModel<String> dataModel) {
		if (this.dataModel != null)
			throw new IllegalStateException("DataModel has been already set.");
		
		this.dataModel = dataModel;
		
		inputCombo = new JInputCombo<String>(dataModel);
		
		add(inputCombo);
		// ---------------- END OF: Setting it up. -----------------
	}
	
	public JInputCombo<String> getInputCombo() {
		return this.inputCombo;
	}
	
	public JPopupScrolledJList<Item> getJList() {
		return jList;
	}
	
	public JLabel getLblStatus() {
		return lblStatus;
	}
	
	
	// Getters and setters needed.
}

package com.kaczurba.lgtvchannels.gui.inputfilters;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JComboBox;

public class JComboBoxes<E> extends JPanel implements DataModelObserver<E> {
	
	private final ActionListener dispatchingActionListner = (e) -> this.dispatchEvent(e);
	
	// for debugging
	private final ActionListener loopbackActionListener = (e) -> {
		Object obj = e.getSource();
		if (obj instanceof JComboBox) {
			JComboBox combo = (JComboBox) obj;
			
			//System.out.println(name + " has changed to: " + comboBox.getSelectedItem());
			System.out.println("loopbackActionListener: " + combo.getName() + " has changed to: " + combo.getSelectedIndex());
		}		
	};

	ActionListener actionListener = loopbackActionListener;
	
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}
	
	private void dispatchEvent(ActionEvent e) {
		if (actionListener != null)
			actionListener.actionPerformed(e);
	}
	
	/**
	 * Create the panel.
	 */
	public JComboBoxes() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}
	
	final HashMap<String, JComboBox> comboBoxes = new HashMap<>();
	
	public String getSelection(String label) {
		return (String) comboBoxes.get(label).getSelectedItem();
	}
	
	/**
	 * 
	 * @return Map<String, Object> = map<Label, SelectedValue>
	 */
	public Map<String, Object> getAllSelections() {
		return comboBoxes.entrySet().stream()
				.collect(Collectors.toMap( x -> x.getKey().toString(), y -> y.getValue().getSelectedItem() ));
	}
	
	private void removeAllComboBoxes() {
		this.removeAll();
		//if (comboBoxes != null) {
			// Unregister all of them first.
		comboBoxes.values()
			.forEach(comboBox -> comboBox.removeActionListener(dispatchingActionListner));

		comboBoxes.clear();
	}
		
	private DataModel<E> dataModel;
	
	// TODO: dataModel should be observable and JComboBoxes should keep checking if a change is made.  
	public void setDataModel(DataModel<E> dataModel) {
		// Content:
		// 1. label -> list of values.
		
		if (comboBoxes != null) {
			removeAllComboBoxes();
		}
		
		this.dataModel = dataModel;
		dataModel.addDataModelObserver(this);
		
		Set<Entry<String, LinkedHashSet<E>>> entries = dataModel.entrySet();
		
		int y = 0;
		for (Entry<String, LinkedHashSet<E>> e : entries) {
			String labelText = e.getKey();
			List<E> values = new ArrayList<E>(e.getValue());
			//values.add(0, "all");
			
			JLabel label = new JLabel(labelText);
			GridBagConstraints gbc_lblIsskipped = new GridBagConstraints();
			gbc_lblIsskipped.anchor = GridBagConstraints.EAST;
			gbc_lblIsskipped.insets = new Insets(0, 0, 5, 5);
			gbc_lblIsskipped.gridx = 0;
			gbc_lblIsskipped.gridy = y;
			add(label, gbc_lblIsskipped);
	
//			@SuppressWarnings("unchecked")
//			JComboBox<E> comboBox = new JComboBox(values.toArray()); // FIXME: Change values so the correct ones are here.
			@SuppressWarnings({ "rawtypes", "unchecked" })
			JComboBox<E> comboBox = new JComboBox(values.toArray());
		
			comboBox.setName(labelText);
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = y++;//.getAndIncrement();
			add(comboBox, gbc_comboBox);
			
			comboBoxes.put(labelText, comboBox);
		}//);
		
		// Need to register it somehow
		comboBoxes.forEach((name, comboBox) -> {
			comboBox.setActionCommand(name);
			comboBox.addActionListener( dispatchingActionListner );
		} );
	}

	@Override
	public void dateModelEventAdd(DataModel<E> src, String label, LinkedHashSet<E> values) {
		//throw new IllegalStateException("JComboBoxes do not support dynamic adding of items to the dataModel. Add all elements to dataModel before assigning the model to JComboBoxes");
		throw new IllegalStateException("Illegal Attempt to add '"+label+"' to the dataModel after "
				+ "dataModel was assigned to the JComboBox. JComboBoxes do not support dynamic "
				+ "adding of items to the dataModel. Add all elements to dataModel "
				+ "before assigning the model to JComboBoxes");
	}

	@Override
	public void dataModelEventRemove(DataModel<E> src, String label) {
		throw new IllegalStateException("Illegal attempt to remove '"+label+"' from the dataModel "
				+ "after dataModel was assigned to the JComboBox."
				+ "JComboBoxes do not support dynamic removing of items to the dataModel. "
				+ "Remove elements from before assigning the model to JComboBoxes");
	}	
}

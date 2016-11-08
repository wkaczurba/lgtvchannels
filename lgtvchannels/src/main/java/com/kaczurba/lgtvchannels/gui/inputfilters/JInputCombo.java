package com.kaczurba.lgtvchannels.gui.inputfilters;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Component;

public class JInputCombo<E> extends JPanel implements ActionListener, DocumentListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2702506654407905492L;
	private JTextField textField;
	private JComboBoxes<E> comboBoxes;
	private ActionListener actionListener;
	
	/**
	 * Create the panel.
	 */
	public JInputCombo() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel label = new JLabel("Search:");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		add(label);
		
		// TODO: Set alignment of this stuff.
		
		
		textField = new JTextField();
		textField.setAlignmentX(Component.LEFT_ALIGNMENT);
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		add(textField);
		textField.setColumns(10);
		textField.setActionCommand("textField");
		textField.getDocument().addDocumentListener(this);
		//textField.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.setSize(this.getWidth(), 100);
		
		/*JComboBoxes filters = new JComboBoxes();
		panel.add(filters);
		add(panel);*/
		setComboBoxes(new JComboBoxes<E>());
	}
	
	public JInputCombo(DataModel<E> dataModel) {
		this();
		getComboBoxes().setDataModel(dataModel);
	}
	
	/**
	 * Sets actionListener that is called when an event happens, example:
	 * 
	 * {@code inputCombo.setActionListener( (e) -> {<br>
		&nbsp; &nbsp; if (e.getSource() == inputCombo) {<br>
		&nbsp; &nbsp; &nbsp; &nbsp; System.out.println("[update] 1. event: " + e);<br>
		&nbsp; &nbsp; &nbsp; &nbsp; System.out.println("         2. textField: " + inputCombo.getTextFieldText());<br>
		&nbsp; &nbsp; &nbsp; &nbsp; System.out.println("         3. combos: " + inputCombo.getComboBoxesValues());<br>
		&nbsp; &nbsp; }<br>
		});}
		
	 * 
	 * @param actionListener
	 */
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	} 
	
	ActionListener actionListener() {
		return this.actionListener;
	}
	
	public void dispatchEvent(ActionEvent e) {
		// FIXME: Is this one that is unsafe?
		
		if (actionListener != null) 
			actionListener.actionPerformed(e);
	}
	
	private void setComboBoxes(JComboBoxes<E> comboBoxes) {
		if (this.comboBoxes != null)
			remove(this.comboBoxes);
		
		this.comboBoxes = comboBoxes;
		comboBoxes.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(comboBoxes);
		comboBoxes.setActionListener(this);
	}

	private JComboBoxes<E> getComboBoxes() {
		return comboBoxes;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if (obj instanceof JComboBox) {
			JComboBox<?> combo = (JComboBox<?>) obj;
			
			//System.out.println("InputCombo: " + combo.getName() + " has changed to: " + combo.getSelectedItem());
			this.dispatchEvent(new ActionEvent(this, 3, combo.getName()));
			
			// TODO: Dispatch further the event.
		} else if (obj instanceof JTextField) {
			JTextField textField = (JTextField) obj;
			System.out.println("textField changed: " + textField.getText());
			
			this.dispatchEvent(new ActionEvent(this, 4, "textField"));			
		} else {
			System.out.println("Unknown event from: " + obj);
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		if (e.getDocument() == textField.getDocument()) {
			System.out.println("textField Updated");

			this.dispatchEvent(new ActionEvent(this, 0, "textField"));
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if (e.getDocument() == textField.getDocument()) {
			System.out.println("textField Updated");
			
			this.dispatchEvent(new ActionEvent(this, 1, "textField"));
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if (e.getDocument() == textField.getDocument()) {
			System.out.println("textField Updated");
			// TODO: DispatchEvent:
			
			this.dispatchEvent(new ActionEvent(this, 2, "textField"));
		}
	}
	
	public String getTextFieldText() {
		return this.textField.getText();
	}
	
	public Map<String, Object> getComboBoxesValues() {
		return this.comboBoxes.getAllSelections();
	}	
}


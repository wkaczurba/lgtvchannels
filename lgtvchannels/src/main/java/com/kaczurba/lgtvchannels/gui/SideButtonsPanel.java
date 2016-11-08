package com.kaczurba.lgtvchannels.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SideButtonsPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4279164373158453016L;
	private final JButton btnLoad = new JButton("Load WIP");	
	private final JButton btnNewFromFile = new JButton("New from file...");
	private final JButton btnSave = new JButton("Save WIP");
	private final JButton btnMoveUp = new JButton("Move up");
	private final JButton btnMoveDown = new JButton("Move down");
	//private final JLabel lblStatus = new JLabel("Status:");
	
	public SideButtonsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
/*		add(rightListPaneScoller);
		rightListPaneScoller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		rightListPaneScoller.setPreferredSize(new Dimension(250, 80));
				
		// Selection...:
		rightJList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
												
		rightListPaneScoller.setViewportView(rightJList);
		rightJList.setModel(rightListModel);*/
		add(btnMoveUp);
		add(btnMoveDown);
		add(btnNewFromFile);
		add(btnSave);
		add(btnLoad);
		//add(lblStatus);
		
		// Right Panel business logic:
/*		btnLoad.addActionListener(a -> frameHandler.load(a));
		btnSave.addActionListener(a -> frameHandler.saveWIP(a));
		btnNewFromFile.addActionListener(a -> frameHandler.newFromFile(a));
		btnMoveDown.addActionListener( a -> frameHandler.moveDown( a, rightJList.getSelectedValuesList()) );
		btnMoveUp.addActionListener(   a -> frameHandler.moveUp(a, rightJList.getSelectedValuesList()) );*/
			
	}
	
	
/*	private JScrollPane getRightListPaneScoller() {
		return rightListPaneScoller;
	}

	public JList<Item> getRightJList() {
		return rightJList;
	}*/
	
	/*public DefaultListModel<Item> getRightListModel() {
		return rightListModel;
	}*/
	
	public JButton getBtnLoad() {
		return btnLoad;	
	}
	
	public JButton getBtnNewFromFile() {
		return btnNewFromFile;
	}
	public JButton getBtnSave() {
		return btnSave;
	}
	
	public JButton getBtnMoveUp() {
		return btnMoveUp;
	}
	
	public JButton getBtnMoveDown() {
		return btnMoveDown;
	}

    /*
	public JLabel getLblStatus() {
		return lblStatus;
	}*/
	
	// FIXME: This is not the right way...
	/*public JList<Item> getRightJList() {
		return super.getjList();
	}*/
}

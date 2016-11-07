package com.kaczurba.lgtvchannels.gui;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.kaczurba.lgtvchannels.xmls.Item;

public class MiddlePanel extends JPanel {
	private final JButton btnUndo = new JButton("Undo");
	private final JButton btnRedo = new JButton("Redo");
	private final JButton btnMoveRight = new JButton("Move >");
	private final JButton btnMoveLeft = new JButton("< Move");
	
	// TODO: Remove these elements from here:
	/*FrameHandler frameHandler;
	LeftPanel leftPanel;
	JList<Item> rightJList;*/
	
	public MiddlePanel() {		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Alignment:
		btnMoveRight.setHorizontalAlignment(SwingConstants.CENTER);
		btnMoveLeft.setHorizontalAlignment(SwingConstants.CENTER);
		btnUndo.setHorizontalAlignment(SwingConstants.CENTER);
		btnRedo.setHorizontalAlignment(SwingConstants.CENTER);

		btnMoveRight.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnMoveLeft.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnUndo.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRedo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		add(btnMoveRight);
		add(btnMoveLeft);
		add(btnUndo);
		add(btnRedo);
		
		// Action listeners:
		/*
		btnMoveRight.addActionListener(   a -> { 
			System.out.println("getMinSelectionIndex: " + rightJList.getSelectionModel().getMinSelectionIndex());
			System.out.println("getMaxSelectionIndex: " + rightJList.getSelectionModel().getMaxSelectionIndex());
			frameHandler.moveRight(a, leftPanel.getLeftJList().getSelectedValuesList());
		}); // TODO: Change null with seleciton of items.
		btnMoveLeft.addActionListener( a -> frameHandler.moveLeft(a, rightJList.getSelectedValuesList()) );
		btnUndo.addActionListener(a -> frameHandler.undo(a));		
		btnRedo.addActionListener(a -> frameHandler.redo(a));
		*/
	}
	
	// Button getters:
	public JButton getBtnUndo() {
		return btnUndo;
	}
	
	public JButton getBtnRedo() {
		return btnRedo;
	}

    public JButton getBtnMoveRight(){
    	return btnMoveRight;
    }
    
    public JButton getBtnMoveLeft() {
    	return btnMoveLeft;
    }
	
/*
	// TODO: Remove these elements from here:
	public void setFrameHandler(FrameHandler frameHandler) {
		this.frameHandler = frameHandler;
	}
	
	// TODO: Remove these elements from here:
	public void setLeftPanel(LeftPanel leftPanel) {
		this.leftPanel = leftPanel;
	}
	
	// TODO: Remove these elements from here:
	public void setRightJList(JList<Item> rightJList) {
		this.rightJList = rightJList;
	}
*/
}

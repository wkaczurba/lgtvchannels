package com.kaczurba.lgtvchannels.gui;

import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.util.List;

import com.kaczurba.lgtvchannels.xmls.Item;

/* FrameHandler thing: */
public interface FrameHandler {
	// Should it be actionEvent? -> I do not think so.
	
	void moveRight(ActionEvent e, List<Item> selectedItems); // btnMoveRight.addActionListener(new ActionListener() { 
	void moveLeft(ActionEvent e, List<Item> what);  // 
	void moveUp(ActionEvent e, List<Item> what);
	void moveDown(ActionEvent e, List<Item> what);
	void undo(ActionEvent e);
	void open(ActionEvent e);
	void export(ActionEvent a);
	void saveWIP(ActionEvent e);
	void load(ActionEvent e);
	void redo(ActionEvent e);
	
	// Empties both left and right windows and opens new file. 
	void newFromFile(ActionEvent e);
}
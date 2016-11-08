package com.kaczurba.lgtvchannels.gui.inputfilters;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.JList;
import javax.swing.JMenuItem;

// Todo extend JList to implement pop-up functionality.

public class JPopupScrolledJList<E> extends JList<E> {
    
//	private final JScrollPane scrollPane;
	// ? MouseAdapter mouseAdapter;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6903047985042483827L;
	/**
	 * Create the panel.
	 */
	public JPopupScrolledJList() {
		
/*		scrollPane = new JScrollPane();
		scrollPane.setViewportView(this);*/
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					JPopupScrolledJList<E> list = JPopupScrolledJList.this;
					
					int i = list.locationToIndex(e.getPoint());
					
					// Accept only clicks on visible items:
					Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex());
					if (r != null && r.contains(e.getPoint())) { 
						int index = list.locationToIndex(e.getPoint());
						
						List<E> selectedItems = list.getSelectedValuesList();
						E clickedItem = list.getModel().getElementAt(i);
						
						if (!selectedItems.contains(clickedItem)) {
							// Get range -> if clicked item is outside of the selected -> setSelectedIndex to the one that is clicked.
							list.setSelectedIndex(index);
							selectedItems = list.getSelectedValuesList();
						}						
						PopUpMenu popUp = new PopUpMenu();
						popUp.setSelection(new Selection<E>(clickedItem, selectedItems));
						popUp.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			}
		});
	}
	
/*	public JScrollPane getScrollPane() {
		return scrollPane;
	}*/	
	
	public static class Selection<E> {
		private final E clickedItem;
		private final List<E> selectedItems;
		
		public Selection(E clickedItem, List<E> selectedItems) {
			this.clickedItem = clickedItem;
			this.selectedItems = new ArrayList<>(selectedItems);
		}
		
	    public Object getClickedItem() {
	    	return clickedItem;
	    }	    
	    
	    public List<E> getSelectedItems() {
	    	return Collections.unmodifiableList(selectedItems);
	    }
	    
	    public String toString() {
	    	return "[Clicked: " + getClickedItem() + " Selection: " + getSelectedItems() + "]";
	    }
	}
		
	private final Map<String, BiConsumer<JMenuItem, Selection<E> > > popUpLabels = new LinkedHashMap<>();
	/**
	 * 
	 * @param jMnenuItemLabel 
	 * @param actionOnRightClick
	 */
	public void addPopupJItem(String jMnenuItemLabel, BiConsumer<JMenuItem, Selection<E> > onClick) {
		popUpLabels.put(jMnenuItemLabel, onClick);
	}
	
	// Consider reworking it into something.
	class PopUpMenu extends JPopupMenu {
	    /**
		 * 
		 */
		private static final long serialVersionUID = -3572623308616643540L;

		JMenuItem anItem;

	    Selection<E> selection;
	    
	    public void setSelection(Selection<E> selection) {
	    	this.selection = selection;
	    }
	    	    
	    public PopUpMenu(){
	    	popUpLabels.forEach( (label, action) -> {
	    		JMenuItem anItem = new JMenuItem(label);
	    		anItem.addActionListener( a -> { action.accept(anItem, selection); } );
	    		add(anItem);
	    	});
	    }
	}

	/*
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
					DefaultListModel<Object> model = new DefaultListModel<>();
					model.addElement("One");
					model.addElement("Two");
					model.addElement("Threee");
					model.addElement("Four");
					model.addElement("Five");
					model.addElement("Six");
					jList.setModel(model);
					
					// How to add....?

					
					BiConsumer<JMenuItem, Selection<Object> > rightClickAction = (menuItem, selection) -> { 
				    	System.out.println("Got right-click on: " + menuItem.getText() + 
				    			"; All selected items: " + selection);
				    };
				    
				    jList.addPopupJItem("Set invisible", rightClickAction);
				    jList.addPopupJItem("Set visible", rightClickAction);
				    jList.addPopupJItem("Properties", rightClickAction);
				    

					contentPane.add(jList);

					frame.pack();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

}

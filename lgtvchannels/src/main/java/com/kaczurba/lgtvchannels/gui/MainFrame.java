package com.kaczurba.lgtvchannels.gui;


import java.awt.Color;
import java.awt.EventQueue;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.kaczurba.lgtvchannels.gui.inputfilters.JPopupScrolledJList;
import com.kaczurba.lgtvchannels.xmls.Item;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.border.BevelBorder;
import java.awt.Font;


// TODO:
//  - Clean up models so they are in FrameTwo
// 
//  - when channels are (moved up) and (move down) -> they should have their prNum updated. (I think).
//  - clean-up models so they are togehter:
//     - (ChannelFilterPanel has dataModel (e.g. "sort by:")
//     - FrameTwo has actions (Comparator, Predicates)
//     - These items should be together
//
//  - add buttons:
//     - to move selection to the TOP.
//     - to move to #(channels)
//     - How about skipping channels ? -> Can I skip directly to channel 200?
//
//  - add setting properties?
//  - add icons / highlights for stuff that is missing.
public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9010698440489811779L;

	FrameHandler frameHandler = null;

    private final JPanel top = new JPanel();
	private final JPanel contentPane = new JPanel();
    private final ChannelFilterPanel leftPanel;
    private final MiddlePanel middlePanel;// = new JPanel();
    private final ChannelFilterPanel rightPanel;// = new RightPanel();
    private final SideButtonsPanel sideButtonsPanel = new SideButtonsPanel();
	private final JPanel bottom = new JPanel();
	
	// What do these two list carry?
	private List<Item> leftList  = new ArrayList<>();
	private List<Item> rightList = new ArrayList<>();	
	
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setLeftList(List<Item> leftList) {
		this.leftList = leftList;
		refresh();
	}
	
	public void setRightList(List<Item> rightList) {
		this.rightList = rightList;
		refresh();
	}
	
	public List<Item> getLeftList() {
		return Collections.unmodifiableList( leftList );
	}
	
	public List<Item> getRightList() {
		return Collections.unmodifiableList( rightList );
	}
	
	public void setFrameHandler(FrameHandler frameHandler) {
		this.frameHandler = frameHandler;
	}
	
	public FrameHandler getFrameHandler() {
		return this.frameHandler;
	}
	
	// TODO: Think where to add this stuff.
	public void channelFilterPanelPopupInit(ChannelFilterPanel channelFilterPanel) {
		// Taken from demo:
	    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */				
		// FIXME: These stuff is to be removed:
		BiConsumer<JMenuItem, JPopupScrolledJList.Selection<Item> > rightPopupClickAction = (menuItem, selection) -> { 
	    	System.out.println("Got right-click on: " + menuItem.getText() + 
	    			"; All selected items: " + selection);
	    };
	    
		BiConsumer<JMenuItem, JPopupScrolledJList.Selection<Item> > setSkipped = (menuItem, selection) -> {
			
	    	System.out.println("Setting isSkipped=1." + selection.getSelectedItems());
	    	selection.getSelectedItems().forEach( x -> { x.set("isSkipped", "1" );} );
	    	//selection.getSelectedItems().forEach(x -> x.);
	    	//selection.
	    	refresh();
	    };	    
	    
		BiConsumer<JMenuItem, JPopupScrolledJList.Selection<Item> > setNotSkipped = (menuItem, selection) -> {
			
	    	System.out.println("Setting isSkipped=0. " + selection.getSelectedItems());
	    	selection.getSelectedItems().forEach( x -> { x.set("isSkipped", "0" );} );
	    	//selection.getSelectedItems().forEach(x -> x.);
	    	//selection.
	    	refresh();
	    };	  	    
	    
	    channelFilterPanel.getJList().addPopupJItem("Set Skipped=1", setSkipped);
	    channelFilterPanel.getJList().addPopupJItem("Set isSkipped=0", setNotSkipped);
	    channelFilterPanel.getJList().addPopupJItem("Properties", rightPopupClickAction);				
		// ---------------------------------------------------------------
	    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	}
	
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1089, 686);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		GridBagLayout gbl_bottom = new GridBagLayout();
		gbl_bottom.columnWidths = new int[]{41, 377, 75, 33, 302, 132, 0};
		gbl_bottom.rowHeights = new int[]{32, 130, 25, 9, 25, 36, 16, 172, 25, 25, 25, 16, 0};
		gbl_bottom.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_bottom.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		bottom.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bottom.setLayout(gbl_bottom);

		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		contentPane.add(top);
		
		leftPanel = new ChannelFilterPanel("From Input XML:", new Font("Tahoma", Font.BOLD, 16));
// Label added:
/*		JLabel leftLabel = new JLabel();
		leftLabel.setFont();
		leftPanel.add(leftLabel, 0);*/
		
		top.add(leftPanel);
		middlePanel = new MiddlePanel();
		top.add(middlePanel);				
		rightPanel = new ChannelFilterPanel("Output (result)", new Font("Tahoma", Font.BOLD, 16));
		// Label added:
		
		top.add(rightPanel);
		top.add(sideButtonsPanel);
		

	    // Business logic:
		channelFilterPanelPopupInit(leftPanel);
		channelFilterPanelPopupInit(rightPanel);
		
		middlePanel.getBtnMoveRight().addActionListener(   a -> { 
			System.out.println("getMinSelectionIndex: " + rightPanel.getJList().getSelectionModel().getMinSelectionIndex());
			System.out.println("getMaxSelectionIndex: " + rightPanel.getJList().getSelectionModel().getMaxSelectionIndex());
			frameHandler.moveRight(a, leftPanel.getJList().getSelectedValuesList());
		}); // TODO: Change null with seleciton of items.
		middlePanel.getBtnMoveLeft().addActionListener( a -> frameHandler.moveLeft(a, rightPanel.getJList().getSelectedValuesList()) );
		middlePanel.getBtnUndo().addActionListener(a -> frameHandler.undo(a));		
		middlePanel.getBtnRedo().addActionListener(a -> frameHandler.redo(a));		
		

		// TODO: Rework this so it 
		leftPanel.getInputCombo().setActionListener(e ->
		    //refreshLeftPanel(e));
			refreshChannelFilterPanel(leftPanel, getLeftList(),  e));

		rightPanel.getInputCombo().setActionListener(e ->
			//refreshRightPanel(e));
			refreshChannelFilterPanel(rightPanel, getRightList(), e));

		sideButtonsPanel.getBtnOpen().addActionListener(a -> frameHandler.open(a));
		sideButtonsPanel.getBtnExport().addActionListener(a -> frameHandler.export(a));
		sideButtonsPanel.getBtnLoad().addActionListener(a -> frameHandler.load(a));
		sideButtonsPanel.getBtnSave().addActionListener(a -> frameHandler.saveWIP(a));
		sideButtonsPanel.getBtnNewFromFile().addActionListener(a -> frameHandler.newFromFile(a));
		sideButtonsPanel.getBtnMoveDown().addActionListener( a -> frameHandler.moveDown( a, rightPanel.getJList().getSelectedValuesList()) );
		sideButtonsPanel.getBtnMoveUp().addActionListener(   a -> frameHandler.moveUp(a, rightPanel.getJList().getSelectedValuesList()) );		
	}
	
	public void refresh() {
		refreshChannelFilterPanel(leftPanel, getLeftList(), null);
		refreshChannelFilterPanel(rightPanel, getRightList(), null);
	}
	
	public <T> void refreshListModel(
		JList<T> jList, List<T> updatedList, Optional<JLabel> lblStatus, Predicate<T> matcherPredicate, Optional<Comparator<T>> comparator ) {
		
	    boolean isError = false;
	    int toSelectMinIndex = -1;
	    int toSelectMaxIndex = -1;

	    if (!(jList.getModel() instanceof DefaultListModel)) {
	    	throw new IllegalStateException("Jlist does not contain DefaultListModel");
	    }
		    
	    // TOOD: Add preservation of selection
	    // Getting selection mode here -> continuos or single or... 
	    // jList.getSelectionModel().getSelectionMode();
	    
	    //synchronized(this) {
			int[] indices = jList.getSelectedIndices();
			
			
			System.out.println("selectedIndices: " + IntStream.of(indices).boxed().collect(Collectors.toList()));
			
			if (indices.length > 0) {
				
			   // FIXME: getElementAt(idx) may be outside of range.
			   List<T> objects = Arrays.stream(indices)
	//				.filter(idx -> jList.getModel().getSize() < idx) // make sure that element is within valid range.
			    	.mapToObj(idx -> (T) jList.getModel().getElementAt(idx))
			    	.collect(Collectors.toList());
				    
			   List<Integer> indexesOfObjectsUpdatedList = 
			   		objects.stream()
			   			.filter(obj -> updatedList.contains(obj))
			   			.map(obj -> updatedList.indexOf(obj))
			   			.collect(Collectors.toList());
				    
			    System.out.println("indexesOfObjectsUpdatedList: " + indexesOfObjectsUpdatedList);
				    
			    if (indexesOfObjectsUpdatedList.size() > 0) {
				    //indexesOfObjectsUpdatedList.stream().mapToInt(i -> i).min()
				    int minIndexOnUpdatedList = Collections.min(indexesOfObjectsUpdatedList);
				    int maxIndexOnUpdatedList = Collections.max(indexesOfObjectsUpdatedList);
				    boolean isContinuousInterval = IntStream.rangeClosed(minIndexOnUpdatedList, maxIndexOnUpdatedList).allMatch(i -> indexesOfObjectsUpdatedList.contains(i));
					    
				    if (isContinuousInterval) {
				    	toSelectMinIndex = minIndexOnUpdatedList;
				    	toSelectMaxIndex = maxIndexOnUpdatedList;
				    }
					    System.out.println("isContinuousInterval: " + isContinuousInterval);
				}
			} else {
			   	System.out.println("Indices.length == 0");
			}
			    		 
			    			
/* *********************** TODO: Here it starts LISTMODEL UPDATE STARTS HERE *********************** */
			boolean dbgIsEdt = javax.swing.SwingUtilities.isEventDispatchThread();
			System.out.println("EDT?: " + dbgIsEdt);
			if (!dbgIsEdt) {
				System.err.println("Not in EDT!");
			}
			// THE ABOVE IS ONLY A DEBUG BIT.
				
			
			DefaultListModel<T> listModel = (DefaultListModel<T>) jList.getModel();
		    listModel.removeAllElements();
			    
// TODO: Comparator added; refactor the stuff.		    
		    	Stream<T> elementsToAdd = updatedList.stream().filter(matcherPredicate);
		    if (comparator.isPresent()) {
//		    	System.out.println(x);
		    	elementsToAdd = elementsToAdd.sorted(comparator.get());
		    }
	
		    // matcherPredicate for left list (regex)    : item -> item.toString().matches(textToMatch.get())
		    // matcherPredicate for left list (non-regex): item -> textToMatch.get().trim().isEmpty() || item.toString().toLowerCase().contains(textToMatch.get().toLowerCase()))
		    try {
		    	elementsToAdd.forEach(element->listModel.addElement(element));
		    	
		    	
				if ((toSelectMinIndex > -1) && (toSelectMaxIndex > -1)) {
					
					/*if ((toSelectMinIndex >= jList.getSelectionModel().getMinSelectionIndex()) && 
					    (toSelectMaxIndex < jList.getSelectionModel().getMaxSelectionIndex()))*/
					
					if (toSelectMinIndex < jList.getModel().getSize() && 
						(toSelectMaxIndex < jList.getModel().getSize())) {
						jList.getSelectionModel().setSelectionInterval(toSelectMinIndex, toSelectMaxIndex);
					}
					System.out.println("Updated; found: " + listModel.getSize());
					
				} else {
					System.out.format("Not updated... toSelectMinIndex = %d, toSelectMaxIndex=%d%n ", toSelectMinIndex, toSelectMaxIndex);
				}
		    } catch (Exception e) {
				//e.printStackTrace();
				isError = true;
				e.printStackTrace();
				System.out.println(e.getClass());
		    } finally {
		    	if (lblStatus.isPresent()) {
		    		if (!isError) {
					    lblStatus.get().setForeground(Color.BLACK);
					    lblStatus.get().setText("Status: all ok");
					    System.out.println("!ok");
			    	} else {
				    	System.out.println("Err.");
						lblStatus.get().setForeground(Color.RED);
						lblStatus.get().setText("Status: Invalid expression.");
						System.out.println("err");
			    	}
		    	}
		    }
/* *********************** TODO: Here it EDNDS: LISTMODEL UPDATE *********************** */					
		    
	    //}
	}		
	
	public void refreshChannelFilterPanel(ChannelFilterPanel panel, List<Item> list,  ActionEvent e) {
		// TODO: Rework variables.
		JList<Item> jList = panel.getJList();
		
		String textToMatch = panel.getInputCombo().getTextFieldText();
		Map<String, Object> comboValues = panel.getInputCombo().getComboBoxesValues();
		
		System.out.println("ComboValues: " +comboValues);
		
		Predicate<Item> filterTextInput;
		Predicate<Item> filterParameters;
		Optional<Comparator<Item>> sorting;
		
		// Filter that is to match parameters first:
		filterParameters = item -> {
			// FIrst fail returns false;
			for (Entry<String, Object> entry : comboValues.entrySet()) {
				String key = entry.getKey();
				Object expectedValue = entry.getValue();
				
// TODO: Refactor this bit.
				// If searching KEY is SORT BY -> It is not a filter; but sorter.
				if (key.equals("sort by:"))
					return true;
				
				// For sorting things.
				
				// TODO: Check that expectedValue is a string.
				if (expectedValue.equals("all"))
					continue;
				
				if (!item.get(key).equals(expectedValue))
					return false;
			}
			return true;
		};
		
// TODO: Sorting addition - to REFACTOR
		String sortBy = comboValues.getOrDefault("sort by:", "original").toString();
		if (sortBy.equals("original")) {
			sorting = Optional.empty();
		} else {
			System.out.println("Sorting set to a non-null value");
			sorting = Optional.of(new Comparator<Item>() {

				@Override
				public int compare(Item o1, Item o2) {
					try {
						return o1.get("vchName").compareTo(o2.get("vchName"));
					} catch (NullPointerException e) {
						throw new IllegalArgumentException("Either of Items contain no vchName", e);
					}
				}});
		}
		
	    filterTextInput = item -> { 
	    	if (textToMatch.trim().isEmpty())
	    		return true;
	    	
	    	// Find stuff... within special stuff...
	    	// Item i;
	    	for (String subString : textToMatch.split(" ")) {
	    		
	    		if (item.get("vchName").contains(subString))
	    			return true;
// Searching only for channel:	    		
/*	    		
	    		// Mimicks "AND" operator:
	    		StringBuilder fullMap = new StringBuilder();
	    		item.getAsMap().forEach( (k,v) -> fullMap.append(k.toLowerCase() + ":" + v.toLowerCase() + "\r\n" ) );
	    		
		    	//if (item.getAsMap().toString().toLowerCase().contains(subString.toLowerCase())) { // Constains "text" filter:
	    		if (fullMap.toString().contains(subString.toLowerCase())) { // Constains "text" filter:
		    		continue;
		    	} else {
		    		// System.out.println(item.getAsMap().toString() + " does not contain " + subString);
		    		return false;
		    	}
*/		    	
	    	}		    	
	    	return false;
	    };
		
		Predicate<Item> twoFitlers = filterParameters.and(filterTextInput);
		
		// TODO: Change filtering.
		refreshListModel(jList, 
				         list,
				         Optional.of( panel.getLblStatus() ),
				         twoFitlers,
				         sorting);		
	}	
}

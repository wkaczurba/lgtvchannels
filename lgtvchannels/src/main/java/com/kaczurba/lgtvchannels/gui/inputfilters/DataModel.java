package com.kaczurba.lgtvchannels.gui.inputfilters;

import java.util.*;
import java.util.Map.Entry;


/**
 * Data Model for {@code JComboBoxes}.
 * Each entry has a label and all possible values a combobox can have.
 * 
 * @author wkaczurb
 *
 * @param <E>
 */
public class DataModel<E extends Object> {
	Map<String, LinkedHashSet<E>> labelsAndValues = new LinkedHashMap<>();
	Vector<DataModelObserver<E>> dataModelObservers = new Vector<>();
	
	public void add(String label,  LinkedHashSet<E> values) {
		labelsAndValues.put(label, values);
		fireAdd(label, values);
	}
	
	public void remove(String label) {
		labelsAndValues.remove(label);
		fireRemove(label);
		//throw new IllegalStateException("Function not implemented");
		
	}
	
	public LinkedHashSet<E> get(String label) {
		return labelsAndValues.get(label);
	}
	
	public Set<Entry<String, LinkedHashSet<E>>> entrySet() {
		return labelsAndValues.entrySet();
	}
	
	public void addDataModelObserver(DataModelObserver<E> dmo) {
		System.out.println("DataModelObserverADDED.");
		dataModelObservers.add(dmo);
	}
	
	public void removeDataModelObserver(DataModelObserver<E> dmo) {
		dataModelObservers.remove(dmo);
	}
	
	/**
	 * For updating stuff.
	 * @param label
	 * @param values
	 */
	public void fireAdd(String label, LinkedHashSet<E> values) {
		dataModelObservers.forEach(dmo -> dmo.dateModelEventAdd(this, label, values) );
	}
	
	/**
	 * For removing labels.
	 * @param label
	 * @param values
	 */
	public void fireRemove(String label) {
		dataModelObservers.forEach(dmo -> dmo.dataModelEventRemove(this, label) );
	}

}

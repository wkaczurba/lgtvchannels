package com.kaczurba.lgtvchannels.gui.inputfilters;

import java.util.LinkedHashSet;

public interface DataModelObserver<E> {
	void dateModelEventAdd(DataModel<E> src, String label, LinkedHashSet<E> values);
	void dataModelEventRemove(DataModel<E> src, String label);
}
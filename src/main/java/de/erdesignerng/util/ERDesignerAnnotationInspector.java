/**
 * Mogwai ERDesigner. Copyright (C) 2002 The Mogwai Project.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package de.erdesignerng.util;

import static org.metawidget.inspector.InspectionResultConstants.LOOKUP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.metawidget.inspector.annotation.MetawidgetAnnotationInspector;
import org.metawidget.inspector.impl.propertystyle.Property;
import org.metawidget.util.ArrayUtils;

import de.erdesignerng.model.Model;

public class ERDesignerAnnotationInspector extends
		MetawidgetAnnotationInspector {

	private Model model;
	
	private int propertyCount;
	
	public ERDesignerAnnotationInspector(Model aModel) {
		model = aModel;
	}
	
	@Override
	protected Map<String, String> inspectProperty(Property theProperty)
			throws Exception {
		
		propertyCount++;
		
		Map<String, String> theAttributes = super.inspectProperty(theProperty);

		ERDesignerLookup theLookup = theProperty
				.getAnnotation(ERDesignerLookup.class);
		if (theLookup != null) {
			List<String> theValues = new ArrayList<String>();
			
			model.addElementPropertiesTo(theValues, theLookup.elementType(), theLookup.propertyName());

			theAttributes.put(LOOKUP, ArrayUtils.toString(theValues.toArray(new String[0])));
		}

		return theAttributes;
	}

	public int getPropertyCount() {
		return propertyCount;
	}
}
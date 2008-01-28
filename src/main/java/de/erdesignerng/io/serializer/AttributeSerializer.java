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
package de.erdesignerng.io.serializer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.erdesignerng.model.Attribute;
import de.erdesignerng.model.DefaultValue;
import de.erdesignerng.model.Domain;
import de.erdesignerng.model.Model;
import de.erdesignerng.model.Table;

public class AttributeSerializer extends Serializer {

    public static final AttributeSerializer SERIALIZER = new AttributeSerializer();
    
    public static final String ATTRIBUTE = "Attribute";

    public static final String DOMAINREFID = "domainrefid";

    public static final String NULLABLE = "nullable";

    public static final String DEFAULTVALUEREFID = "defaultvaluerefid";

    public void serialize(Attribute aAttribute, Document aDocument, Element aRootElement) {
        
        Element theAttributeElement = addElement(aDocument, aRootElement, ATTRIBUTE);

        // Basisdaten des Modelelementes speichern
        serializeProperties(aDocument, theAttributeElement, aAttribute);

        // Domain usw
        Domain theDomain = aAttribute.getDomain();
        theAttributeElement.setAttribute(DOMAINREFID, theDomain.getSystemId());

        setBooleanAttribute(theAttributeElement, NULLABLE, aAttribute.isNullable());

        if (aAttribute.getDefaultValue() != null) {
            theAttributeElement.setAttribute(DEFAULTVALUEREFID, aAttribute.getDefaultValue().getSystemId());
        }

        serializeCommentElement(aDocument, theAttributeElement, aAttribute);
    }

    public void deserializeFrom(Model aModel, Table aTable, Document aDocument, Element aElement) {
        // Parse the Attributes
        NodeList theAttributes = aElement.getElementsByTagName(ATTRIBUTE);
        for (int j = 0; j < theAttributes.getLength(); j++) {
            Element theAttributeElement = (Element) theAttributes.item(j);

            Attribute theAttribute = new Attribute();
            theAttribute.setOwner(aTable);
            deserializeProperties(theAttributeElement, theAttribute);

            deserializeCommentElement(theAttributeElement, theAttribute);

            String theDomainId = theAttributeElement.getAttribute(DOMAINREFID);

            Domain theDomain = aModel.getDomains().findBySystemId(theDomainId);

            if (theDomain == null) {
                throw new IllegalArgumentException("Cannot find domain with id " + theDomainId);
            }

            DefaultValue theDefault = null;
            String theDefaultRefId = theAttributeElement.getAttribute(DEFAULTVALUEREFID);
            if ((theDefaultRefId != null) && (!"".equals(theDefaultRefId))) {
                theDefault = aModel.getDefaultValues().findBySystemId(theDefaultRefId);
                if (theDefault == null) {
                    throw new IllegalArgumentException("Cannot find default value with id " + theDefaultRefId);
                }
            }

            theAttribute.setDefinition(theDomain, TRUE.equals(theAttributeElement.getAttribute(NULLABLE)),
                    theDefault);

            aTable.getAttributes().add(theAttribute);
        }
    }
}

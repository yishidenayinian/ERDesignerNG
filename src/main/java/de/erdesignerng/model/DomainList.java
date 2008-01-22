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
package de.erdesignerng.model;

/**
 * 
 * @author $Author: mirkosertic $
 * @version $Date: 2008-01-22 20:54:05 $
 */
public class DomainList extends ModelItemVector<Domain> {

    private static final long serialVersionUID = 7547407545913894968L;

    /**
     * Find a domain by a given datatype.
     * 
     * @param aDataType
     *            the system id
     * @param aSize the size
     * @param aPrecision the precision
     * @param aRadix the radix
     * @return the found element
     */
    public Domain findByDataType(String aDataType, int aSize, int aPrecision, int aRadix) {
        for (Domain theDomain : this) {
            if (theDomain.equals(aDataType, aSize, aPrecision, aRadix)) {
                return theDomain;
            }
        }
        return null;
    }

}

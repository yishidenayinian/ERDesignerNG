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
package de.erdesignerng.visual.cells;

import java.awt.geom.Point2D;

/**
 * 
 * @author $Author: mirkosertic $
 * @version $Date: 2008-01-14 20:01:11 $
 */
public final class TransferHelper {

    private TransferHelper() {
    }

    public static Point2D createPoint2DFromString(String aValue) {
        if (aValue == null) {
            return null;
        }

        int theP = aValue.indexOf(":");
        int theX = Integer.parseInt(aValue.substring(0, theP));
        int theY = Integer.parseInt(aValue.substring(theP + 1));

        return new Point2D.Double(theX, theY);
    }
}

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
package de.erdesignerng.visual.common;

import de.erdesignerng.ERDesignerBundle;
import de.erdesignerng.visual.editor.DialogConstants;
import de.erdesignerng.visual.editor.convertmodel.ConvertModelEditor;

public class ConvertModelCommand extends UICommand {

    public ConvertModelCommand(ERDesignerComponent component) {
        super(component);
    }

    @Override
    void execute() {
        if (!component.checkForValidConnection()) {
            return;
        }

        ConvertModelEditor theEditor = new ConvertModelEditor(component.model, component.scrollPane);
        if (theEditor.showModal() == DialogConstants.MODAL_RESULT_OK) {
            try {
                theEditor.applyValues();

                component.setModel(component.model);

                component.worldConnector.setStatusText(component.getResourceHelper().getText(ERDesignerBundle.MODELCONVERTED));
            } catch (Exception e) {
                component.worldConnector.notifyAboutException(e);
            }
        }
    }

}

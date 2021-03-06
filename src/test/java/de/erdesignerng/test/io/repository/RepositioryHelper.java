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
package de.erdesignerng.test.io.repository;

import de.erdesignerng.model.Model;
import de.erdesignerng.model.ModelIOUtilities;
import de.erdesignerng.model.serializer.repository.DictionaryModelSerializer;
import de.erdesignerng.model.serializer.repository.RepositoryEntryDescriptor;

import java.io.StringWriter;
import java.sql.Connection;

public class RepositioryHelper {

	public static String performRepositorySaveAndLoad(String aModelResource, Class aHibernateDialect,
													  Connection aConnection) throws Exception {

		Model theModel = ModelIOUtilities.getInstance().deserializeModelFromXML(
				RepositioryHelper.class.getResourceAsStream(aModelResource));

		RepositoryEntryDescriptor theDesc = new RepositoryEntryDescriptor();
		theDesc.setName("Dummy");

		theDesc = DictionaryModelSerializer.SERIALIZER.serialize(theDesc, theModel, aConnection, aHibernateDialect);

		Model theNewModel = DictionaryModelSerializer.SERIALIZER.deserialize(theDesc, aConnection, aHibernateDialect);

		StringWriter theStringWriter = new StringWriter();
		ModelIOUtilities.getInstance().serializeModelToXML(theNewModel, theStringWriter);
		return theStringWriter.toString().trim();
	}
}
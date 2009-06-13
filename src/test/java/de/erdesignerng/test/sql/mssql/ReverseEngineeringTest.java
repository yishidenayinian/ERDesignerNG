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
package de.erdesignerng.test.sql.mssql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.erdesignerng.dialect.Dialect;
import de.erdesignerng.dialect.ReverseEngineeringOptions;
import de.erdesignerng.dialect.ReverseEngineeringStrategy;
import de.erdesignerng.dialect.SQLGenerator;
import de.erdesignerng.dialect.SchemaEntry;
import de.erdesignerng.dialect.TableNamingEnum;
import de.erdesignerng.dialect.mssql.MSSQLDialect;
import de.erdesignerng.model.Attribute;
import de.erdesignerng.model.Index;
import de.erdesignerng.model.IndexExpression;
import de.erdesignerng.model.Model;
import de.erdesignerng.model.Relation;
import de.erdesignerng.model.Table;
import de.erdesignerng.model.View;
import de.erdesignerng.modificationtracker.HistoryModificationTracker;
import de.erdesignerng.test.sql.AbstractReverseEngineeringTest;

/**
 * Test for XML based model io.
 * 
 * @author $Author: mirkosertic $
 * @version $Date: 2008-11-16 17:48:26 $
 */
public class ReverseEngineeringTest extends AbstractReverseEngineeringTest {

    @Override
    protected void setUp() throws Exception {

        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
        Connection theConnection = null;

        theConnection = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/master", "sa", "");

        Statement theStatement = theConnection.createStatement();
        try {
            theStatement.execute("drop database mogwai");
        } catch (Exception e) {
            e.printStackTrace();
        }

        theStatement.execute("create database mogwai");
        theConnection.close();
    }

    public void testReverseEngineerMSSQL() throws Exception {

        Connection theConnection = null;
        try {
            theConnection = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/mogwai", "sa", "");

            loadSQL(theConnection, "db.sql");

            Dialect theDialect = new MSSQLDialect();
            ReverseEngineeringStrategy<MSSQLDialect> theST = theDialect.getReverseEngineeringStrategy();

            Model theModel = new Model();
            theModel.setDialect(theDialect);
            theModel.setModificationTracker(new HistoryModificationTracker(theModel));

            List<SchemaEntry> theAllSchemas = theST.getSchemaEntries(theConnection);

            List<SchemaEntry> theSchemas = new ArrayList<SchemaEntry>();
            for (SchemaEntry theEntry : theAllSchemas) {
                System.out.println(theEntry.getSchemaName());

                // Only dbo schemas
                if ("dbo".equals(theEntry.getSchemaName())) {
                    theSchemas.add(theEntry);
                }
            }

            ReverseEngineeringOptions theOptions = new ReverseEngineeringOptions();
            theOptions.setTableNaming(TableNamingEnum.STANDARD);
            theOptions.getTableEntries().addAll(theST.getTablesForSchemas(theConnection, theSchemas));

            theST.updateModelFromConnection(theModel, new EmptyWorldConnector(), theConnection, theOptions,
                    new EmptyReverseEngineeringNotifier());

            // Implement Unit Tests here
            Table theTable = theModel.getTables().findByName("Table1");
            assertTrue(theTable != null);
            Attribute theAttribute = theTable.getAttributes().findByName("tb2_1");
            assertTrue(theAttribute != null);
            assertTrue(theAttribute.isNullable() == false);
            assertTrue(theAttribute.getDatatype().getName().equals("varchar"));
            assertTrue(theAttribute.getSize() == 20);
            theAttribute = theTable.getAttributes().findByName("tb2_2");
            assertTrue(theAttribute != null);
            assertTrue(theAttribute.isNullable());
            assertTrue(theAttribute.getDatatype().getName().equals("varchar"));
            assertTrue(theAttribute.getSize() == 100);
            theAttribute = theTable.getAttributes().findByName("tb2_3");
            assertTrue(theAttribute != null);
            assertTrue(theAttribute.isNullable() == false);
            assertTrue(theAttribute.getDatatype().getName().equals("decimal"));
            assertTrue(theAttribute.getSize() == 20);
            assertTrue(theAttribute.getFraction() == 5);

            theTable = theModel.getTables().findByName("Table2");
            assertTrue(theTable != null);
            theAttribute = theTable.getAttributes().findByName("tb3_1");
            assertTrue(theAttribute != null);
            theAttribute = theTable.getAttributes().findByName("tb3_2");
            assertTrue(theAttribute != null);
            theAttribute = theTable.getAttributes().findByName("tb3_3");
            assertTrue(theAttribute != null);

            Index thePK = theTable.getPrimarykey();
            assertTrue(thePK != null);
            assertTrue(thePK.getExpressions().findByAttributeName("tb3_1") != null);

            View theView = theModel.getViews().findByName("View1");
            assertTrue(theView != null);

            Relation theRelation = theModel.getRelations().findByName("FK1");
            assertTrue(theRelation != null);
            assertTrue("Table1".equals(theRelation.getImportingTable().getName()));
            assertTrue("Table2".equals(theRelation.getExportingTable().getName()));

            assertTrue(theRelation.getMapping().size() == 1);
            Map.Entry<IndexExpression, Attribute> theEntry = theRelation.getMapping().entrySet().iterator().next();
            assertTrue("tb2_1".equals(theEntry.getValue().getName()));
            assertTrue("tb3_1".equals(theEntry.getKey().getAttributeRef().getName()));

            SQLGenerator theGenerator = theDialect.createSQLGenerator();
            String theResult = statementListToString(theGenerator.createCreateAllObjects(theModel), theGenerator);

            System.out.println(theResult);

            String theReference = readResourceFile("result.sql");
            assertTrue(theResult.equals(theReference));

        } finally {
            if (theConnection != null) {
                theConnection.close();
            }
        }
    }
    
    public void testReverseEngineeredSQL() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
        Connection theConnection = null;
        try {
            theConnection = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/mogwai", "sa", "");

            loadSingleSQL(theConnection, "result.sql");
        } finally {
            if (theConnection != null) {

                theConnection.close();
            }
        }
    }

}
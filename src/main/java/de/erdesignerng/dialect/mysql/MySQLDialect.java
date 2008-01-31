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
package de.erdesignerng.dialect.mysql;

import de.erdesignerng.dialect.JDBCReverseEngineeringStrategy;
import de.erdesignerng.dialect.NameCastType;
import de.erdesignerng.dialect.SQLGenerator;
import de.erdesignerng.dialect.sql92.SQL92Dialect;

/**
 * @author $Author: mirkosertic $
 * @version $Date: 2008-01-31 16:14:37 $
 */
public class MySQLDialect extends SQL92Dialect {

    public MySQLDialect() {
        setSpacesAllowedInObjectNames(false);
        setCaseSensitive(true);
        setMaxObjectNameLength(30);
        setNullablePrimaryKeyAllowed(false);
        setCastType(NameCastType.NOTHING);

        registerType(new MySQLDataType("BIT", "", java.sql.Types.BIT));
        registerType(new MySQLDataType("BOOL", "", java.sql.Types.BIT));
        registerType(new MySQLDataType("TINYINT", "[(M)] [UNSIGNED] [ZEROFILL]", java.sql.Types.TINYINT));
        registerType(new MySQLDataType("TINYINT UNSIGNED", "[(M)] [UNSIGNED] [ZEROFILL]", java.sql.Types.TINYINT));
        registerType(new MySQLDataType("BIGINT", "[(M)] [UNSIGNED] [ZEROFILL]", java.sql.Types.BIGINT));
        registerType(new MySQLDataType("BIGINT UNSIGNED", "[(M)] [ZEROFILL]", java.sql.Types.BIGINT));
        registerType(new MySQLDataType("LONG VARBINARY", "", java.sql.Types.LONGVARBINARY));
        registerType(new MySQLDataType("MEDIUMBLOB", "", java.sql.Types.LONGVARBINARY));
        registerType(new MySQLDataType("LONGBLOB", "", java.sql.Types.LONGVARBINARY));
        registerType(new MySQLDataType("BLOB", "", java.sql.Types.LONGVARBINARY));
        registerType(new MySQLDataType("TINYBLOB", "", java.sql.Types.LONGVARBINARY));
        registerType(new MySQLDataType("VARBINARY", "(M)", java.sql.Types.VARBINARY));
        registerType(new MySQLDataType("BINARY", "(M)", java.sql.Types.BINARY));
        registerType(new MySQLDataType("LONG VARCHAR", "", java.sql.Types.LONGVARCHAR));
        registerType(new MySQLDataType("MEDIUMTEXT", "", java.sql.Types.LONGVARCHAR));
        registerType(new MySQLDataType("LONGTEXT", "", java.sql.Types.LONGVARCHAR));
        registerType(new MySQLDataType("TEXT", "", java.sql.Types.LONGVARCHAR));
        registerType(new MySQLDataType("TINYTEXT", "", java.sql.Types.LONGVARCHAR));
        registerType(new MySQLDataType("CHAR", "(M)", java.sql.Types.CHAR));
        registerType(new MySQLDataType("NUMERIC", "[(M[,D])] [ZEROFILL]", java.sql.Types.NUMERIC));
        registerType(new MySQLDataType("DECIMAL", "[(M[,D])] [ZEROFILL]", java.sql.Types.DECIMAL));
        registerType(new MySQLDataType("INTEGER", "[(M)] [UNSIGNED] [ZEROFILL]", java.sql.Types.INTEGER));
        registerType(new MySQLDataType("INTEGER UNSIGNED", "[(M)] [ZEROFILL]", java.sql.Types.INTEGER));
        registerType(new MySQLDataType("INT", "[(M)] [UNSIGNED] [ZEROFILL]", java.sql.Types.INTEGER));
        registerType(new MySQLDataType("INT UNSIGNED", "[(M)] [ZEROFILL]", java.sql.Types.INTEGER));
        registerType(new MySQLDataType("MEDIUMINT", "[(M)] [UNSIGNED] [ZEROFILL]", java.sql.Types.INTEGER));
        registerType(new MySQLDataType("MEDIUMINT UNSIGNED", "[(M)] [ZEROFILL]", java.sql.Types.INTEGER));
        registerType(new MySQLDataType("SMALLINT", "[(M)] [UNSIGNED] [ZEROFILL]", java.sql.Types.SMALLINT));
        registerType(new MySQLDataType("SMALLINT UNSIGNED", "[(M)] [ZEROFILL]", java.sql.Types.SMALLINT));
        registerType(new MySQLDataType("FLOAT", "[(M,D)] [ZEROFILL]", java.sql.Types.REAL));
        registerType(new MySQLDataType("DOUBLE", "[(M,D)] [ZEROFILL]", java.sql.Types.DOUBLE));
        registerType(new MySQLDataType("DOUBLE PRECISION", "[(M,D)] [ZEROFILL]", java.sql.Types.DOUBLE));
        registerType(new MySQLDataType("REAL", "[(M,D)] [ZEROFILL]", java.sql.Types.DOUBLE));
        registerType(new MySQLDataType("VARCHAR", "(M)", java.sql.Types.VARCHAR));
        registerType(new MySQLDataType("ENUM", "", java.sql.Types.VARCHAR));
        registerType(new MySQLDataType("SET", "", java.sql.Types.VARCHAR));
        registerType(new MySQLDataType("DATE", "", java.sql.Types.DATE));
        registerType(new MySQLDataType("TIME", "", java.sql.Types.TIME));
        registerType(new MySQLDataType("DATETIME", "", java.sql.Types.TIMESTAMP));
        registerType(new MySQLDataType("TIMESTAMP", "[(M)]", java.sql.Types.TIMESTAMP));
    }

    @Override
    public JDBCReverseEngineeringStrategy getReverseEngineeringStrategy() {
        return new MySQLReverseEngineeringStrategy(this);
    }

    @Override
    public String getUniqueName() {
        return "MySQLDialect";
    }

    @Override
    public String getDriverClassName() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String getDriverURLTemplate() {
        return "jdbc:mysql://<host>/<db>";
    }

    @Override
    public boolean supportsSchemaInformation() {
        return false;
    }

    @Override
    public SQLGenerator createSQLGenerator() {
        return new MySQLSQLGenerator(this);
    }
}
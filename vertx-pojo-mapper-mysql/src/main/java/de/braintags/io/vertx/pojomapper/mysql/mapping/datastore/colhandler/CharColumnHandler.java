/*
 * #%L
 * vertx-pojongo
 * %%
 * Copyright (C) 2015 Braintags GmbH
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */

package de.braintags.io.vertx.pojomapper.mysql.mapping.datastore.colhandler;

import de.braintags.io.vertx.pojomapper.mapping.IField;
import de.braintags.io.vertx.pojomapper.mapping.datastore.IColumnInfo;
import de.braintags.io.vertx.pojomapper.mysql.mapping.datastore.SqlColumnInfo;
import scala.Char;

/**
 * Handles char and creates CHAR from it Properties for scale and precision are not used
 * 
 * @author Michael Remme
 * 
 */

public class CharColumnHandler extends AbstractSqlColumnHandler {

  /**
   * Constructor for a ByteColumnHandler
   */
  public CharColumnHandler() {
    super(Char.class, char.class);
  }

  @Override
  protected StringBuilder generateColumn(IField field, IColumnInfo ci) {
    return new StringBuilder(String.format("%s CHAR( 1 ) ", ci.getName()));
  }

  @Override
  public void applyMetaData(SqlColumnInfo column) {
    // nothing to do here
  }

}

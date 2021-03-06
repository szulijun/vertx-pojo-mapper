/*
 * #%L
 * vertx-pojo-mapper-mysql
 * %%
 * Copyright (C) 2017 Braintags GmbH
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package de.braintags.vertx.jomnigate.mysql.mapping;

import de.braintags.vertx.jomnigate.mapping.IProperty;
import de.braintags.vertx.jomnigate.mapping.IPropertyMapper;
import de.braintags.vertx.jomnigate.mapping.IPropertyMapperFactory;
import de.braintags.vertx.jomnigate.mapping.impl.DefaultPropertyMapper;

/**
 * 
 * 
 * @author Michael Remme
 * 
 */
public class SqlPropertyMapperFactory implements IPropertyMapperFactory {

  /*
   * (non-Javadoc)
   * 
   * @see
   * de.braintags.vertx.jomnigate.mapping.IPropertyMapperFactory#getPropertyMapper(de.braintags.vertx.jomnigate.mapping.
   * IField)
   */
  @Override
  public IPropertyMapper getPropertyMapper(IProperty field) {
    if (field == null)
      throw new NullPointerException("parameter must be specified: field");
    return new DefaultPropertyMapper();
  }

}

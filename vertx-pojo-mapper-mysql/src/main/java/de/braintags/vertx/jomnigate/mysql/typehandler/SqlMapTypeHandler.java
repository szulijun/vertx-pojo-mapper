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
package de.braintags.vertx.jomnigate.mysql.typehandler;

import de.braintags.vertx.jomnigate.json.typehandler.handler.MapTypeHandler;
import de.braintags.vertx.jomnigate.mapping.IProperty;
import de.braintags.vertx.jomnigate.typehandler.ITypeHandlerFactory;
import de.braintags.vertx.jomnigate.typehandler.ITypeHandlerResult;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;

/**
 * 
 * @author Michael Remme
 * 
 */

public class SqlMapTypeHandler extends MapTypeHandler {

  /**
   * @param typeHandlerFactory
   */
  public SqlMapTypeHandler(ITypeHandlerFactory typeHandlerFactory) {
    super(typeHandlerFactory);
  }

  @Override
  public void fromStore(Object source, IProperty field, Class<?> cls, Handler<AsyncResult<ITypeHandlerResult>> handler) {
    try {
      JsonArray sourceArray = source == null ? null : new JsonArray((String) source);
      super.fromStore(sourceArray, field, cls, handler);
    } catch (Exception e) {
      fail(e, handler);
    }
  }

  @Override
  protected Object encodeResultArray(JsonArray result) {
    return result.encode();
  }

}

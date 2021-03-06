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

import java.math.BigDecimal;

import de.braintags.vertx.jomnigate.dataaccess.query.impl.GeoSearchArgument;
import de.braintags.vertx.jomnigate.datatypes.geojson.GeoPoint;
import de.braintags.vertx.jomnigate.datatypes.geojson.Position;
import de.braintags.vertx.jomnigate.exception.TypeHandlerException;
import de.braintags.vertx.jomnigate.mapping.IProperty;
import de.braintags.vertx.jomnigate.typehandler.AbstractTypeHandler;
import de.braintags.vertx.jomnigate.typehandler.ITypeHandlerFactory;
import de.braintags.vertx.jomnigate.typehandler.ITypeHandlerResult;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

/**
 * 
 * 
 * @author Michael Remme
 * 
 */
public class SqlGeoPointTypeHandler extends AbstractTypeHandler {
  /**
   * Comment for <code>THIS_IS_NOT_A_VALID_POINT_DEFINITON</code>
   */
  private static final String THIS_IS_NOT_A_VALID_POINT_DEFINITON = "This is not a valid POINT definiton: ";
  private static final String CREATE_STRING = " POINT( %s %s) ";

  /**
   * Constructor with parent {@link ITypeHandlerFactory}
   * 
   * @param typeHandlerFactory
   *          the parent {@link ITypeHandlerFactory}
   */
  public SqlGeoPointTypeHandler(ITypeHandlerFactory typeHandlerFactory) {
    super(typeHandlerFactory, GeoPoint.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.braintags.vertx.jomnigate.typehandler.ITypeHandler#fromStore(java.lang.Object,
   * de.braintags.vertx.jomnigate.mapping.IField, java.lang.Class, io.vertx.core.Handler)
   */
  @Override
  public void fromStore(Object source, IProperty field, Class<?> cls,
      Handler<AsyncResult<ITypeHandlerResult>> resultHandler) {
    success(parse((String) source), resultHandler);
  }

  /**
   * parses a String like POINT( 15.5 13.3 ) into a GeoPoint
   * 
   * @param positionString
   * @return
   */
  private GeoPoint parse(String positionString) {
    if (positionString == null) {
      return null;
    }
    int open = positionString.indexOf("POINT(");
    if (open < 0) {
      throw new TypeHandlerException(THIS_IS_NOT_A_VALID_POINT_DEFINITON + positionString);
    }
    String positions = positionString.substring(open + 6);
    int close = positions.indexOf(')');
    if (close < 0) {
      throw new TypeHandlerException(THIS_IS_NOT_A_VALID_POINT_DEFINITON + positionString);
    }
    positions = positions.substring(0, close).trim();
    String[] posDefs = positions.split(" ");
    if (posDefs.length != 2) {
      throw new TypeHandlerException(THIS_IS_NOT_A_VALID_POINT_DEFINITON + positionString);
    }
    Position pos = new Position(new BigDecimal(posDefs[0]).doubleValue(), new BigDecimal(posDefs[1]).doubleValue());
    return new GeoPoint(pos);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.braintags.vertx.jomnigate.typehandler.ITypeHandler#intoStore(java.lang.Object,
   * de.braintags.vertx.jomnigate.mapping.IField, io.vertx.core.Handler)
   */
  @Override
  public void intoStore(Object source, IProperty field, Handler<AsyncResult<ITypeHandlerResult>> resultHandler) {
    if (source instanceof GeoPoint) {
      encode((GeoPoint) source, resultHandler);
    } else if (source instanceof GeoSearchArgument) {
      encode((GeoSearchArgument) source, field, resultHandler);
    } else {
      fail(new UnsupportedOperationException("unsupported type: " + source.getClass().getName()), resultHandler);
    }

  }

  private void encode(GeoPoint source, Handler<AsyncResult<ITypeHandlerResult>> resultHandler) {
    if (source != null && source.getCoordinates() != null && source.getCoordinates().getValues().size() == 2) {
      String content = String.format(CREATE_STRING, source.getCoordinates().getValues().get(0),
          source.getCoordinates().getValues().get(1));
      success(new SqlFunction("ST_GeomFromText", content), resultHandler);
    } else {
      success(null, resultHandler);
    }
  }

  private void encode(GeoSearchArgument source, IProperty field, Handler<AsyncResult<ITypeHandlerResult>> resultHandler) {
    try {
      success(new SqlDistanceSearchFunction(source, field), resultHandler);
    } catch (Exception e) {
      resultHandler.handle(Future.failedFuture(e));
    }
  }

}

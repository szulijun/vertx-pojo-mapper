/*
 * #%L
 * vertx-pojo-mapper-common
 * %%
 * Copyright (C) 2017 Braintags GmbH
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package de.braintags.vertx.jomnigate.observer;

import java.util.List;

import de.braintags.vertx.jomnigate.dataaccess.delete.IDelete;
import de.braintags.vertx.jomnigate.dataaccess.delete.IDeleteResult;
import de.braintags.vertx.jomnigate.dataaccess.query.IQuery;
import de.braintags.vertx.jomnigate.dataaccess.query.IQueryResult;
import de.braintags.vertx.jomnigate.dataaccess.write.IWrite;
import de.braintags.vertx.jomnigate.dataaccess.write.IWriteResult;
import de.braintags.vertx.jomnigate.mapping.IMapper;
import de.braintags.vertx.jomnigate.observer.impl.handler.DefaultObserverHandler;
import io.vertx.core.Future;

/**
 * IObserverHandler is the member of an IMapper which keeps the information about registered {@link IObserver} and
 * executes the observers, which are registered for an event for the parent mapper
 * 
 * @author Michael Remme
 * 
 */
public interface IObserverHandler {

  /**
   * Create a new instance of {@link IObserverHandler}
   * 
   * @param mapper
   *          the mapper to be used
   * @return
   */
  public static IObserverHandler createInstance(IMapper<?> mapper) {
    return new DefaultObserverHandler(mapper);
  }

  /**
   * Get all observers, which are registered for one {@link ObserverEventType} for the mapper of this
   * 
   * @param event
   * @return a list of all fitting IObserver
   */
  List<IObserver> getObserver(ObserverEventType event);

  /**
   * Performs the event {@link ObserverEventType#BEFORE_UPDATE} for the storeObject
   * 
   * @param write
   * @param entity
   * @param context
   * @return
   */
  <T> Future<Void> handleBeforeUpdate(IWrite<T> write, T entity, IObserverContext context);

  /**
   * Performs the event {@link ObserverEventType#BEFORE_INSERT} for the storeObject
   * 
   * @param write
   * @param entity
   * @param context
   * @return
   */
  <T> Future<Void> handleBeforeInsert(IWrite<T> write, T entity, IObserverContext context);

  /**
   * Performs the event {@link ObserverEventType#AFTER_INSERT} to the records in the {@link IWrite}
   * 
   * @param writeObject
   * @param writeResult
   * @param context
   * @return
   */
  <T> Future<Void> handleAfterInsert(IWrite<T> writeObject, IWriteResult writeResult, IObserverContext context);

  /**
   * Performs the event {@link ObserverEventType#AFTER_UPDATE} to the records in the {@link IWrite}
   * 
   * @param writeObject
   * @param writeResult
   * @param context
   * @return
   */
  <T> Future<Void> handleAfterUpdate(IWrite<T> writeObject, IWriteResult writeResult, IObserverContext context);

  /**
   * Performs the event {@link ObserverEventType#BEFORE_LOAD} to the records in the {@link IWrite}
   * 
   * @param queryObject
   * @param context
   * @return
   */
  <T> Future<Void> handleBeforeLoad(IQuery<T> queryObject, IObserverContext context);

  /**
   * Performs the event {@link ObserverEventType#AFTER_LOAD} to the records in the {@link IQuery}
   * 
   * @param queryObject
   * @param queryResult
   * @param context
   * @return
   */
  <T> Future<Void> handleAfterLoad(IQuery<T> queryObject, IQueryResult<T> queryResult, IObserverContext context);

  /**
   * Performs the event {@link ObserverEventType#AFTER_MAPPING} for the given mapper
   * 
   * @param mapper
   * @param context
   * @return
   */
  <T> Future<Void> handleAfterMapping(IMapper<T> mapper, IObserverContext context);

  /**
   * Performs the event {@link ObserverEventType#BEFORE_DELETE} to the records in the {@link IDelete}
   * 
   * @param deleteObject
   * @param context
   * @return
   */
  <T> Future<Void> handleBeforeDelete(IDelete<T> deleteObject, IObserverContext context);

  /**
   * Performs the event {@link ObserverEventType#AFTER_DELETE} to the records in the {@link IDelete}
   * 
   * @param deleteObject
   * @param deleteResult
   * @param context
   * @return
   */
  <T> Future<Void> handleAfterDelete(IDelete<T> deleteObject, IDeleteResult deleteResult, IObserverContext context);

}

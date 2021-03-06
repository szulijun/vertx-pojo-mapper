/*-
 * #%L
 * vertx-pojongo
 * %%
 * Copyright (C) 2017 Braintags GmbH
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package de.braintags.vertx.jomnigate.mongo.init;

import com.google.common.collect.ImmutableSet;

import de.braintags.vertx.jomnigate.mapping.IIndexDefinition;
import de.braintags.vertx.jomnigate.mapping.IMapper;
import de.braintags.vertx.jomnigate.mapping.ISyncResult;
import de.braintags.vertx.jomnigate.mapping.impl.AbstractDataStoreSynchronizer;
import de.braintags.vertx.jomnigate.mongo.MongoDataStore;
import de.braintags.vertx.jomnigate.mongo.MongoUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * MongoDataStoreSynchronizer checks / creates needed indexes
 * 
 * @author Michael Remme
 * 
 */
public class MongoDataStoreSynchronizer extends AbstractDataStoreSynchronizer<JsonObject> {
  private final MongoSyncResult syncResult = new MongoSyncResult();
  private final MongoDataStore ds;

  public MongoDataStoreSynchronizer(final MongoDataStore ds) {
    this.ds = ds;
  }

  @Override
  protected void syncTable(final IMapper mapper, final Handler<AsyncResult<Void>> resultHandler) {
    resultHandler.handle(Future.succeededFuture());
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.braintags.vertx.jomnigate.mapping.impl.AbstractDataStoreSynchronizer#getSyncResult()
   */
  @Override
  protected ISyncResult<JsonObject> getSyncResult() {
    return syncResult;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.braintags.vertx.jomnigate.mapping.impl.AbstractDataStoreSynchronizer#syncIndexes(de.braintags.vertx.
   * pojomapper.mapping.IMapper, de.braintags.vertx.jomnigate.annotation.Indexes, io.vertx.core.Handler)
   */
  @Override
  protected void syncIndexes(final IMapper<?> mapper, final ImmutableSet<IIndexDefinition> indexes,
      final Handler<AsyncResult<Void>> resultHandler) {
    MongoUtil.createIndexes(ds, mapper, result -> {
      if (result.failed()) {
        resultHandler.handle(Future.failedFuture(result.cause()));
      } else {
        resultHandler.handle(Future.succeededFuture());
      }
    });
  }

}

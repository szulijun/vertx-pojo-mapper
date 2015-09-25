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
package de.braintags.io.vertx.pojomapper.mysql;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import de.braintags.io.vertx.pojomapper.datastoretest.DatastoreBaseTest;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

/**
 * 
 * 
 * @author Michael Remme
 * 
 */

public class TestSqlExpressions extends DatastoreBaseTest {
  private static final Logger log = LoggerFactory.getLogger(TestSqlExpressions.class);

  /**
   * 
   */
  public TestSqlExpressions() {
  }

  @Override
  protected VertxOptions getOptions() {
    VertxOptions options = new VertxOptions();
    options.setBlockedThreadCheckInterval(10000);
    options.setWarningExceptionTime(10000);
    return options;
  }

  @Test
  public void simpleTest() {
    CountDownLatch latch = new CountDownLatch(1);
    ((MySqlDataStore) getDataStore()).getSqlClient().getConnection(cr -> {
      if (cr.failed()) {
        log.error("", cr.cause());
        latch.countDown();
      } else {
        SQLConnection conn = cr.result();
        JsonArray array = new JsonArray().add("new name");
        String insertExpression = "insert into MiniMapper set name=\"new update\"; SELECT LAST_INSERT_ID()";

        conn.query(insertExpression, ur -> {
          if (ur.failed()) {
            log.error("", ur.cause());
            latch.countDown();
          } else {
            ResultSet rs = ur.result();
            latch.countDown();
          }
        });

        // conn.updateWithParams(insertExpression, array, ur -> {
        // if (ur.failed()) {
        // log.error("", ur.cause());
        // latch.countDown();
        // } else {
        // UpdateResult res = ur.result();
        // log.info(res.toJson());
        // log.info(res.getKeys());
        //
        // latch.countDown();
        // }
        // });

      }
    });

    try {
      latch.await();
    } catch (InterruptedException e) {
      log.error("", e);
    } finally {
      testComplete();
    }

  }

}
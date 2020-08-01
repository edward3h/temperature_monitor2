package org.ethelred.temperature_monitor2

import groovy.sql.Sql
import groovy.util.logging.Log

/**
 * TODO
 *
 * @author eharman* @since 2020-07-31
 */
@Log
class Store implements AutoCloseable {
    def db = Sql.newInstance("jdbc:sqlite:temperature.db", "org.sqlite.JDBC").tap { db ->
        db.execute '''
create table if not exists temperature (
    kind text,
    source text,
    temp double,
    outside boolean,
    mode text,
    modesetting text,
    created_at text default CURRENT_TIMESTAMP 
)
'''
        db.execute '''
create table if not exists alert (
    "type" text,
    message text,
    created_at text default CURRENT_TIMESTAMP 
)
'''
    }

    @Override
    void close() throws Exception {
        if (db) db.close()
    }

    Store leftShift(Iterable<Temperature> temps) {
        tap {
            db.withBatch("insert into temperature (kind, source, temp, outside, mode, modesetting) values (?, ?, ?, ?, ?, ?)") { s ->
                temps.each { t -> s.addBatch(*(t as List)) }
            }
            log.info ("inserted ${temps.size()} temperatures")
        }
    }

    Store leftShift(Alert alert) {
        tap {
            db.executeInsert("insert into alert(type, message) values ($alert.type, $alert.message)")
        }
        log.info("inserted alert ${alert}")
    }

    Alert getLastAlert() {
        def row = db.firstRow("select type, message, created_at from alert order by created_at desc limit 1")
        if (row) {
            new Alert(row)
        }
        else
        {
            null
        }
    }

    List<Temperature> getRecentOutsideTemps() {
        def rows = db.rows("select * from temperature where created_at > datetime('now', '-1 hour') order by created_at desc")
        rows.collect{row -> new Temperature(row)}
    }
}

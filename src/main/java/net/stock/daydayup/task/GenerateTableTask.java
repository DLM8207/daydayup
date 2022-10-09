/*
 * Copyright © 2020 ctwing
 */
package net.stock.daydayup.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Calendar;

/**
 * 自动建表
 *
 * @author:dailm
 * @create at :2022/10/5 8:12
 */
@Component
public class GenerateTableTask {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 创建日数据表，每月最后一天执行
     * 首先，每月最后一天可能为（28,29,30,31）这几天只需要在这几天调度程序，在程序中判断是否为最后一天，如果是则执行需要执行的代码块
     */
    @Scheduled(cron = "0 59 23 28-31 * ?")
    @Transactional
    public void generateDayTable(){
        final Calendar c = Calendar.getInstance();
        /**
         * c.get(Calendar.DATE) 当前时间
         * c.getActualMaximum(Calendar.DATE) 本月最后一日
         */
        if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            String monthStr = month < 10 ? "0" + month : month + "";
            String tableName = "object_day_value_" + year + "_" + monthStr;
            String sql = "CREATE TABLE IF NOT EXISTS public." + tableName + "\n" +
                    "(\n" +
                    "    day date NOT NULL,\n" +
                    "    stockcode character varying(20) COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                    "    open numeric(8,2),\n" +
                    "    close numeric(8,2),\n" +
                    "    height numeric(8,2),\n" +
                    "    lower numeric(8,2),\n" +
                    "    volume bigint,\n" +
                    "    turnover numeric(12,2),\n" +
                    "    amt_inc_dec numeric(8,2),\n" +
                    "    id character varying(32) COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                    "    inc_dec_rate character varying(8) COLLATE pg_catalog.\"default\",\n" +
                    "    turnover_rate character varying(8) COLLATE pg_catalog.\"default\",\n" +
                    "    price numeric(8,2),\n" +
                    "    yes_close numeric(8,2),\n" +
                    "    CONSTRAINT object_day_value_pkey_" + year + "_" + monthStr + " PRIMARY KEY (id)\n" +
                    ")\n" +
                    "\n" +
                    "TABLESPACE pg_default;";
            String alterSql1 = "ALTER TABLE IF EXISTS public." + tableName + "\n" +
                    "    OWNER to postgres;";
            String alterSql2 = "COMMENT ON TABLE public." + tableName + "\n" +
                    "    IS '日数据" + year + monthStr + "';";

            String alterSql3 = "CREATE INDEX IF NOT EXISTS " + tableName + "_day_index\n" +
                    "    ON public." + tableName + " USING btree\n" +
                    "    (day ASC NULLS LAST)\n" +
                    "    TABLESPACE pg_default;";
            Query query = this.entityManager.createNativeQuery(sql);
            query.executeUpdate();
            query = this.entityManager.createNativeQuery(alterSql1);
            query.executeUpdate();
            query = this.entityManager.createNativeQuery(alterSql2);
            query.executeUpdate();
            query = this.entityManager.createNativeQuery(alterSql3);
            query.executeUpdate();
        }
    }

    public static void main(String[] args) {
        GenerateTableTask tableTask = new GenerateTableTask();
        tableTask.generateDayTable();
    }
}

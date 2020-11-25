package org.uvigo.esei.dm.financialskill.core;

import android.app.Application;

import org.uvigo.esei.dm.financialskill.db.DBManager;

public class FinancialSkillApplication extends Application {

    private DBManager dbManager;

    @Override
    public void onCreate() {
        super.onCreate();
        this.dbManager = new DBManager(this);
    }

    public DBManager getDbManager() {
        return dbManager;
    }
}
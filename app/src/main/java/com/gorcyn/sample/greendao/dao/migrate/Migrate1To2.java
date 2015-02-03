package com.gorcyn.sample.greendao.dao.migrate;

import android.database.sqlite.SQLiteDatabase;

public class Migrate1To2 extends Migration {

    @Override
    public void applyMigration(SQLiteDatabase db, int currentVersion) {
        this.prepareMigration(db, currentVersion);

        db.execSQL("ALTER TABLE USER ADD password TEXT NOT NULL DEFAULT 'changeme'");
    }

    @Override
    public int getTargetVersion() {
        return 1;
    }

    @Override
    public int getMigratedVersion() {
        return 2;
    }

    @Override
    public Migration getPreviousMigration() {
        return null;
    }
}

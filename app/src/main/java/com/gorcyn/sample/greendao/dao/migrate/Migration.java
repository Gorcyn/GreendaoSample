package com.gorcyn.sample.greendao.dao.migrate;

import android.database.sqlite.SQLiteDatabase;

public abstract class Migration {

    abstract void applyMigration(SQLiteDatabase db, int currentVersion);

    abstract int getTargetVersion();

    abstract int getMigratedVersion();

    abstract Migration getPreviousMigration();

    protected void prepareMigration(SQLiteDatabase db, int currentVersion) {
        if (currentVersion < this.getTargetVersion()) {
            Migration previousMigration = this.getPreviousMigration();
            previousMigration.applyMigration(db, currentVersion);
            if (previousMigration.getMigratedVersion() != this.getTargetVersion()) {
                throw new IllegalStateException("Applied migration do not match expected target version.");
            }
        }
    }
}

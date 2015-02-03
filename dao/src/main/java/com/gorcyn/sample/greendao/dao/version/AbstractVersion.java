package com.gorcyn.sample.greendao.dao.version;

import de.greenrobot.daogenerator.Schema;

public abstract class AbstractVersion {

    private static final String PACKAGE = "com.gorcyn.sample.greendao";

    private final Schema schema;

    private final boolean current;

    public AbstractVersion(boolean current) {
        int version = this.getVersionNumber();
        String packageName = PACKAGE;
        if (!current) {
            packageName += ".v" + version;
        }
        this.schema = new Schema(version, packageName + ".model");
        this.schema.setDefaultJavaPackageDao(packageName + ".dao");
        this.schema.enableKeepSectionsByDefault();
        this.current = current;
    }

    public Schema getSchema() {
        return this.schema;
    }

    public boolean isCurrent() {
        return this.current;
    }

    public abstract int getVersionNumber();
}

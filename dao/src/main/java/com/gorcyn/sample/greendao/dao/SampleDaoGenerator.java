package com.gorcyn.sample.greendao.dao;

import com.gorcyn.sample.greendao.dao.version.AbstractVersion;
import com.gorcyn.sample.greendao.dao.version.Version1;
import com.gorcyn.sample.greendao.dao.version.Version2;

import java.util.LinkedList;
import java.util.List;

import de.greenrobot.daogenerator.DaoGenerator;

public class SampleDaoGenerator {

    private static final String SCHEMA_OUTPUT = "app/src-gen/main/java";

    public static void main(String[] args) throws Exception {
        List<AbstractVersion> versions = new LinkedList<>();

        // versions.add(new Version1(true));

        versions.add(new Version1(false));
        versions.add(new Version2(true));

        validateSchemas(versions);

        for (AbstractVersion version : versions) {
            new DaoGenerator().generateAll(version.getSchema(), SCHEMA_OUTPUT);
        }
    }

    private static void validateSchemas(List<AbstractVersion> versions) throws IllegalArgumentException {
        boolean currentExists = false;
        List<Integer> versionNumbers = new LinkedList<>();

        for (AbstractVersion version : versions) {
            if (version.isCurrent()) {
                if (currentExists) {
                    throw new IllegalArgumentException("Unable to process schema version, only one schema can be marked as current.");
                }
                currentExists = true;
            }
            int versionNumber = version.getVersionNumber();
            if (versionNumbers.contains(versionNumber)) {
                throw new IllegalArgumentException("Unable to process schema versions, multiple instances with version number : " + version.getVersionNumber());
            }
            versionNumbers.add(versionNumber);
        }
        if (!currentExists) {
            throw new IllegalArgumentException("Unable to generate schema, one schema must be marked as current.");
        }
    }
}

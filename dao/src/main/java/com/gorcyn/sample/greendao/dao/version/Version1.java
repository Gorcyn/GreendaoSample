package com.gorcyn.sample.greendao.dao.version;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;

public class Version1 extends AbstractVersion {

    public Version1(boolean current) {
        super(current);

        Entity user = this.addUser();
        this.addAddress(user);
    }

    @Override
    public int getVersionNumber() {
        return 1;
    }

    private Entity addUser() {
        Entity user = this.getSchema().addEntity("User");

        user.addIdProperty();
        user.addStringProperty("firstName").notNull();
        user.addStringProperty("lastName").notNull();
        user.addDateProperty("birthDate").notNull();
        user.addStringProperty("email").notNull();

        return user;
    }

    private Entity addAddress(Entity user) {
        Entity address = this.getSchema().addEntity("Address");

        address.addIdProperty();
        address.addIntProperty("number").notNull();
        address.addStringProperty("street").notNull();
        address.addStringProperty("zipCode").notNull();
        address.addStringProperty("city").notNull();
        address.addStringProperty("country").notNull();

        Property userProperty = address.addLongProperty("userId").getProperty();
        address.addToOne(user, userProperty, "user");
        user.addToMany(address, userProperty, "addresses");

        return address;
    }
}

package com.gorcyn.sample.greendao.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.gorcyn.sample.greendao.model.User;

import com.gorcyn.sample.greendao.model.Address;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ADDRESS.
*/
public class AddressDao extends AbstractDao<Address, Long> {

    public static final String TABLENAME = "ADDRESS";

    /**
     * Properties of entity Address.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Number = new Property(1, int.class, "number", false, "NUMBER");
        public final static Property Street = new Property(2, String.class, "street", false, "STREET");
        public final static Property ZipCode = new Property(3, String.class, "zipCode", false, "ZIP_CODE");
        public final static Property City = new Property(4, String.class, "city", false, "CITY");
        public final static Property Country = new Property(5, String.class, "country", false, "COUNTRY");
        public final static Property UserId = new Property(6, Long.class, "userId", false, "USER_ID");
    };

    private DaoSession daoSession;

    private Query<Address> user_AddressesQuery;

    public AddressDao(DaoConfig config) {
        super(config);
    }
    
    public AddressDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ADDRESS' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NUMBER' INTEGER NOT NULL ," + // 1: number
                "'STREET' TEXT NOT NULL ," + // 2: street
                "'ZIP_CODE' TEXT NOT NULL ," + // 3: zipCode
                "'CITY' TEXT NOT NULL ," + // 4: city
                "'COUNTRY' TEXT NOT NULL ," + // 5: country
                "'USER_ID' INTEGER);"); // 6: userId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ADDRESS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Address entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getNumber());
        stmt.bindString(3, entity.getStreet());
        stmt.bindString(4, entity.getZipCode());
        stmt.bindString(5, entity.getCity());
        stmt.bindString(6, entity.getCountry());
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(7, userId);
        }
    }

    @Override
    protected void attachEntity(Address entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Address readEntity(Cursor cursor, int offset) {
        Address entity = new Address( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // number
            cursor.getString(offset + 2), // street
            cursor.getString(offset + 3), // zipCode
            cursor.getString(offset + 4), // city
            cursor.getString(offset + 5), // country
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6) // userId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Address entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNumber(cursor.getInt(offset + 1));
        entity.setStreet(cursor.getString(offset + 2));
        entity.setZipCode(cursor.getString(offset + 3));
        entity.setCity(cursor.getString(offset + 4));
        entity.setCountry(cursor.getString(offset + 5));
        entity.setUserId(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Address entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Address entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "addresses" to-many relationship of User. */
    public List<Address> _queryUser_Addresses(Long userId) {
        synchronized (this) {
            if (user_AddressesQuery == null) {
                QueryBuilder<Address> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.UserId.eq(null));
                user_AddressesQuery = queryBuilder.build();
            }
        }
        Query<Address> query = user_AddressesQuery.forCurrentThread();
        query.setParameter(0, userId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getUserDao().getAllColumns());
            builder.append(" FROM ADDRESS T");
            builder.append(" LEFT JOIN USER T0 ON T.'USER_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Address loadCurrentDeep(Cursor cursor, boolean lock) {
        Address entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        User user = loadCurrentOther(daoSession.getUserDao(), cursor, offset);
        entity.setUser(user);

        return entity;    
    }

    public Address loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Address> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Address> list = new ArrayList<Address>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Address> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Address> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}

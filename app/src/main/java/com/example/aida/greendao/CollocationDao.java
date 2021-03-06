package com.example.aida.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.aida.been.Collocation;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COLLOCATION".
*/
public class CollocationDao extends AbstractDao<Collocation, Long> {

    public static final String TABLENAME = "COLLOCATION";

    /**
     * Properties of entity Collocation.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Clothes = new Property(2, String.class, "clothes", false, "CLOTHES");
        public final static Property Bottoms = new Property(3, String.class, "bottoms", false, "BOTTOMS");
        public final static Property Accessories = new Property(4, String.class, "accessories", false, "ACCESSORIES");
        public final static Property Shoes = new Property(5, String.class, "shoes", false, "SHOES");
        public final static Property Isdelete = new Property(6, int.class, "isdelete", false, "ISDELETE");
    }


    public CollocationDao(DaoConfig config) {
        super(config);
    }
    
    public CollocationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COLLOCATION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"CLOTHES\" TEXT," + // 2: clothes
                "\"BOTTOMS\" TEXT," + // 3: bottoms
                "\"ACCESSORIES\" TEXT," + // 4: accessories
                "\"SHOES\" TEXT," + // 5: shoes
                "\"ISDELETE\" INTEGER NOT NULL );"); // 6: isdelete
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COLLOCATION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Collocation entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String clothes = entity.getClothes();
        if (clothes != null) {
            stmt.bindString(3, clothes);
        }
 
        String bottoms = entity.getBottoms();
        if (bottoms != null) {
            stmt.bindString(4, bottoms);
        }
 
        String accessories = entity.getAccessories();
        if (accessories != null) {
            stmt.bindString(5, accessories);
        }
 
        String shoes = entity.getShoes();
        if (shoes != null) {
            stmt.bindString(6, shoes);
        }
        stmt.bindLong(7, entity.getIsdelete());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Collocation entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String clothes = entity.getClothes();
        if (clothes != null) {
            stmt.bindString(3, clothes);
        }
 
        String bottoms = entity.getBottoms();
        if (bottoms != null) {
            stmt.bindString(4, bottoms);
        }
 
        String accessories = entity.getAccessories();
        if (accessories != null) {
            stmt.bindString(5, accessories);
        }
 
        String shoes = entity.getShoes();
        if (shoes != null) {
            stmt.bindString(6, shoes);
        }
        stmt.bindLong(7, entity.getIsdelete());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Collocation readEntity(Cursor cursor, int offset) {
        Collocation entity = new Collocation( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // clothes
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // bottoms
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // accessories
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // shoes
            cursor.getInt(offset + 6) // isdelete
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Collocation entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setClothes(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBottoms(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAccessories(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setShoes(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIsdelete(cursor.getInt(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Collocation entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Collocation entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Collocation entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

package com.example.dao;

import java.util.List;
import com.example.model.SomeData;

/**
 * Created by Nikolay V. Petrov on 24.08.2017.
 */

public interface SomeDataDao {

        public static final String SQL_FIND_ALL = "select * from " + SomeData.TABLE_NAME;
        public static final String SQL_FIND_BY_ID = SQL_FIND_ALL + " where " + SomeData.ID_COLUMN + " = ?";
        public static final String SQL_INSERT = "insert into " + SomeData.TABLE_NAME + " (" + SomeData.ID_COLUMN + ", " +
                SomeData.DATA_COLUMN + ") values (nextval('" + SomeData.ID_SEQ + "'), ?)";
        public static final String SQL_UPDATE = "update " + SomeData.TABLE_NAME + " set " + SomeData.DATA_COLUMN + " where " +
                SomeData.ID_COLUMN + " = ?";
        public static final String SQL_DELETE = "delete from " + SomeData.TABLE_NAME + " where " + SomeData.ID_COLUMN + " = ?";

        public List<SomeData> findAll();
        public SomeData findById(Long id);
        public void insert(SomeData someData);
        public void update(SomeData someData);
        public void delete(SomeData someData);
}

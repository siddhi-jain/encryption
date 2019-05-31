package com.example.demo.Encryption;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.bson.BSONCallback;
import org.bson.BSONObject;
import org.bson.BasicBSONCallback;

class BasicDBObjectCallback extends BasicBSONCallback {
    @Override
    public BSONObject create() {
        return new BasicDBObject();
    }

    @Override
    protected BSONObject createList() {
        return new BasicDBList();
    }

    @Override
    public BSONCallback createBSONCallback() {
        return new BasicDBObjectCallback();
    }
}

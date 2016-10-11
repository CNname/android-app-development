package fi.jamk.ex5shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ultoros on 10.10.2016.
 */

public class Database extends SQLiteOpenHelper{

    private static final String DB_NAME = "H3409_db";
    private final String DB_TABLE = "shoppingList";
    private final String PRODUCTNAME = "productName";
    private final String COUNT = "count";
    private final String PRICE = "price";

    public Database(Context context) {
        // Context, database name, optional cursor factory, database version
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+DB_TABLE+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+PRODUCTNAME+" TEXT, "+COUNT+" REAL, "+PRICE+" REAL);");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
        onCreate(db);
    }
}

package fi.jamk.ex5shoppinglist;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AddProductDialogFragment.DialogListener {

    private final String DB_TABLE = "shoppingList";
    private final int DELETE_ID = 0;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)  findViewById(R.id.listView);
        // register listView's context menu (to delete row)
        registerForContextMenu(listView);
        db = (new Database(this)).getWritableDatabase();

        queryData();

        // calculate total price
        float points = 0f;
        if (cursor.moveToFirst()) {
            do {
                float score = cursor.getFloat(3); // columnIndex
                points += score;
            } while(cursor.moveToNext());
            Toast.makeText(getBaseContext(), "Total price: " + points, Toast.LENGTH_SHORT).show();
        }
    }

    // query data from database
    public void queryData() {
        // get data with query
        String[] resultColumns = new String[]{"_id","productName","count","price"};
        cursor = db.query(DB_TABLE,resultColumns,null,null,null,null,"productName ASC",null);

        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_item, cursor,
                new String[] {"productName", "count", "price"},      // from
                new int[] {R.id.productName, R.id.count, R.id.price}    // to
                ,0);  // flags

        // put data in listView and show it
        listView.setAdapter(adapter);
        float points = 0f;
        if (cursor.moveToFirst()) {
            do {
                float score = cursor.getFloat(3); // columnIndex
                points += score;
            } while(cursor.moveToNext());
            Toast.makeText(getBaseContext(), "Total price: " + points, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handles item selections
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                AddProductDialogFragment eDialog = new AddProductDialogFragment();
                eDialog.show(getFragmentManager(), "Add a new product");
        }
        return false;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String productName, int count, float price) {
        ContentValues values=new ContentValues(2);
        values.put("productName", productName);
        values.put("count", count);
        values.put("price", price);
        db.insert("shoppingList", null, values);
        // query data
        queryData();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) { }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Delete");
        //menu.add(Menu.NONE, RENAME_ID, Menu.NONE, "Rename");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // delete selected item
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                String[] args = {String.valueOf(info.id)};
                db.delete("shoppingList", "_id=?", args);
                queryData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // close cursor and db connection
    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }


}

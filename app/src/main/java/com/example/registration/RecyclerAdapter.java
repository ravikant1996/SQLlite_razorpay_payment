package com.example.registration;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public static List<DatabaseModel> dbList;
        static Context context;
    public DatabaseHelpher helpher;
    public SQLiteDatabase db;
    public String userId;
    public static final int REQUEST_PERM_WRITE_STORAGE = 102;



    RecyclerAdapter(Context context, List<DatabaseModel> dbList ){
        this.dbList = new ArrayList<DatabaseModel>();
        this.context = context;
        this.dbList = dbList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_row, null);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

       // holder.id.setText(String.valueOf(dbList.get(position).getId()));
        DatabaseModel details = dbList.get(position);

        holder.title.setText(dbList.get(position).getTitle());
        holder.name.setText(dbList.get(position).getName());

       holder.menu.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
//               userId = String.valueOf(dbList.get(position).getTitle());
               final DatabaseModel details = dbList.get(position);
               final int userId = details.getId();
               helpher = new DatabaseHelpher(context);
                   db = helpher.getWritableDatabase();

               PopupMenu popupMenu = new PopupMenu(context, holder.menu);
               popupMenu.inflate(R.menu.popup_menu);
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                  @Override
                   public boolean onMenuItemClick(MenuItem item) {
                       switch (item.getItemId()) {
                           case R.id.edit_query:
                            //   Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(context,EditActivity.class);
                               intent.putExtra("USERID", userId);
                               context.startActivity(intent);
                               break;
                           case R.id.delete_query:
                               db.delete(DatabaseHelpher.STUDENT_TABLE,DatabaseHelpher._ID + " = " + userId,null);
                       //        notifyItemRangeChanged(position,dbList.size());
                               dbList.remove(position);
                               notifyItemRemoved(position);
                               db.close();
                               break;
                           case R.id.CreatePDF:
                                   Intent i = new Intent(context, createPDF.class);
                                   i.putExtra("USERID", userId);
                                   context.startActivity(i);
                           default:
                               break;
                       }
                       return false;
                   }
               });
               popupMenu.show();
           }
       });

    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        public TextView name,title;
        public ImageView menu;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            name = (TextView) itemLayoutView.findViewById(R.id.rvname);
            title = (TextView)itemLayoutView.findViewById(R.id.rvtitle);
            menu = (ImageView)itemLayoutView.findViewById(R.id.imageButton);

            itemLayoutView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position1= getAdapterPosition();
            Intent intent = new Intent(context,DetailsActivity.class);
            Bundle extras = new Bundle();
            extras.putInt("position1",position1);
            intent.putExtras(extras);
            /*
            int i=getAdapterPosition();
            intent.putExtra("position", getAdapterPosition());*/
            context.startActivity(intent);
          //  Toast.makeText(RecyclerAdapter.context, "you have clicked Row " + getAdapterPosition(), Toast.LENGTH_LONG).show();
        }
    }
}



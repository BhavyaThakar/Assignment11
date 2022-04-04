package com.example.contacts_music;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CRVAdapter extends RecyclerView.Adapter<CRVAdapter.CViewHolder>{

    Context context;
    ArrayList<ContactModelClass> contacts = new ArrayList<>();

    @SuppressLint("Range")
    public CRVAdapter(Context context) {
        this.context = context;

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        String name;
        String number = null;
        String id;
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Cursor pcursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = ?", new String[]{id}, null);
                if (pcursor.getCount()>0){
                    while (pcursor.moveToNext()){
                        number = pcursor.getString(pcursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                }
                pcursor.close();
                contacts.add(new ContactModelClass(name,number));
            }
            cursor.close();
        }


    }

    @NonNull
    @Override
    public CViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contact_row,parent,false);
        return new CViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class CViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNumber;
        public CViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNumber = itemView.findViewById(R.id.tv_number);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull CViewHolder holder, int position) {
        holder.tvName.setText(contacts.get(position).name);
        holder.tvNumber.setText(contacts.get(position).number);

    }
}

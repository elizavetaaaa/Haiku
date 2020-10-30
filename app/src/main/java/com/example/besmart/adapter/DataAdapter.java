package com.example.besmart.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.besmart.R;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {

    private Context context;
    private RecOnClickListener recOnClickListener;
    private List<ListItem> listItemArray;
    private SharedPreferences pref;
    private boolean isFav;


    public DataAdapter(Context context, RecOnClickListener recOnClickListener, List<ListItem> listItemArray) {
        this.context = context;
        this.recOnClickListener = recOnClickListener;
        this.listItemArray = listItemArray;
        pref = context.getSharedPreferences("CAT",Context.MODE_PRIVATE);

    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {
        holder.setData(listItemArray.get(position));
    }

    @Override
    public int getItemCount() {
        return listItemArray.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private boolean isFavChecked = false;
        private TextView tvText;
        private ImageButton imButFav;

        public DataHolder(@NonNull View itemView)
        {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvText);
            imButFav = itemView.findViewById(R.id.imBut);
            imButFav.setOnClickListener(this);
        }
        public void setData(ListItem item)
        {
            tvText.setText(item.getText());
            if (!isFav)
                setFav(item, getAdapterPosition());
            else {
                setFavAll();
            }
        }

        private void setFavAll() {
            imButFav.setImageResource(R.drawable.like_active);
        }


        @Override
        public void onClick(View v)
        {
            isFavChecked = !isFavChecked;
            if(isFavChecked)
            {
                imButFav.setImageResource(R.drawable.like_active);
            }
            else
            {
                imButFav.setImageResource(R.drawable.like_inactive);
            }
            if(!isFav){
                recOnClickListener.onItemClicked(getAdapterPosition());}

            else{
                deleteItem();
            }
        }

        private String replaceCharAtPosition(int position, char ch, String st)
        {
            char[] charArray = st.toCharArray();
            charArray[position] = ch;
            return new String(charArray);
        }

        private void saveString(String stToSave)
        {
            ListItem item = listItemArray.get(getAdapterPosition());
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(item.getCat(),stToSave);
            editor.apply();
        }

        private void deleteItem(){
            ListItem item = listItemArray.get(getAdapterPosition());
            String datatoChange = pref.getString(item.getCat(),"none"); // this string we will change
            if(datatoChange == null) return;
            String replacedData = replaceCharAtPosition(item.getPosition(),'0', datatoChange);
            saveString(replacedData);
            listItemArray.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), listItemArray.size());

        }
        private void setFav(ListItem item, int position)
        {
            String fav_data = pref.getString(item.getCat(),"none");
            if(fav_data != null)
            {
                char[] charArray = fav_data.toCharArray();
                switch (charArray[position])
                {
                    case '0':
                        //imButFav.setImageResource(android.R.drawable.btn_star_big_off);
                        imButFav.setImageResource(R.drawable.like_inactive);

                        isFavChecked = false;
                        break;
                    case '1':
                        //imButFav.setImageResource(android.R.drawable.like_active);
                        imButFav.setImageResource(R.drawable.like_active);
                        isFavChecked = true;
                        break;
                }

            }

        }
    }


    public void updateList(List<ListItem> listArray, boolean isFav)
    {
        this.isFav = isFav;
        listItemArray.clear();
        listItemArray.addAll(listArray);
        notifyDataSetChanged();
    }
}
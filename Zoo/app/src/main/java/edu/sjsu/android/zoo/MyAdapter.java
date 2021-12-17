package edu.sjsu.android.zoo;


import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Animal> values;
    private Context mContext;

    public MyAdapter(Context context, List<Animal> myDataset) {
        values = myDataset;
        mContext = context;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder public class
    // each data item is just a string in this case

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtHeader;
        public ImageView image;
        public View layout;
        RelativeLayout parent_layout;

        public ViewHolder(View v) {
            super(v);
            parent_layout = v.findViewById(R.id.parent_layout);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            image = (ImageView) v.findViewById(R.id.icon);
        }

    }


    public void add(int position, Animal item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Animal animal = values.get(position);
        holder.txtHeader.setText(animal.getName());
        holder.image.setImageResource(animal.getImage());

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position == values.size()-1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("This animal is very scary. Would you like to proceed?.");
                        builder.setCancelable(true);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(mContext, Animal_page.class);
                                intent.putExtra("animal_image", String.valueOf(animal.getImage()));
                                intent.putExtra("animal_name", animal.getName());
                                intent.putExtra("animal_description", animal.getDescription());
                                mContext.startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.cancel();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                    else{
                        Intent intent = new Intent(mContext, Animal_page.class);
                        intent.putExtra("animal_image", String.valueOf(animal.getImage()));
                        intent.putExtra("animal_name", animal.getName());
                        intent.putExtra("animal_description", animal.getDescription());
                        mContext.startActivity(intent);
                    }
    }
        });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}



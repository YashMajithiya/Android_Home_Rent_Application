    package com.example.rent_home;
    //importing package
    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.TextView;
    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;
    import java.util.List;
    import Model.Renters;
    //<----------------------connect with your firebase server------------------------------>
    //Public class for notificationAdapter
    public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.viewHolder> {

        List<Renters> mData;
        Context mContext;

        public notificationAdapter(List<Renters> mData, Context mContext) {
            this.mData = mData;
            this.mContext = mContext;
        }

        @NonNull
        @Override
        //it is called when recycleView needs a new viewHolder of the given type to represent the item
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_list_layout, parent, false);
            return new viewHolder(view);
        }

        @Override
        //this method internally calls to update the ViewHolder contain
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {


            holder.nam.setText(mData.get(position).getName());
            holder.contact.setText(mData.get(position).getContactNo());
            holder.adres.setText(mData.get(position).getAddress());


        }
        //override method
        @Override
        //getItemCount method definition
        public int getItemCount() {
            return mData.size();
        } //----------------------
        //initialize all require variable
        public static class viewHolder extends RecyclerView.ViewHolder {
            public TextView nam, contact, adres;
            public Button showButton;
            //viewHolder method
            public viewHolder(@NonNull View itemView) {
                super(itemView);
                //fetch data which is enter by user on run time that data fetched into imageview by find view by id
                nam = itemView.findViewById(R.id.nam);
                contact = itemView.findViewById(R.id.cnctNo);
                adres = itemView.findViewById(R.id.adres);
                showButton = itemView.findViewById(R.id.showBtn);

            }
        }


    }

package com.example.lujayn_15.searchdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.MyViewHolder> implements Filterable {
    ArrayList<BeanSearchProduct> beanSearchProducts=new ArrayList<>();
    ArrayList<BeanSearchProduct> array=new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_search_product;

        public MyViewHolder(View view) {
            super(view);
            tv_search_product = (TextView) view.findViewById(R.id.tv_search_product);

        }
    }

    public AdapterSearch(ArrayList<BeanSearchProduct> beanSearchProducts) {
        this.beanSearchProducts = beanSearchProducts;
        this.array = beanSearchProducts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.tv_search_product.setText(beanSearchProducts.get(position).Title);


    }


    @Override
    public int getItemCount() {
        return beanSearchProducts.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                String s = String.valueOf(charSequence);

                if (s != null && s.length() > 0) {
                    ArrayList<BeanSearchProduct> temp = new ArrayList<>();
                    for (BeanSearchProduct category : array) {
                        if (category.Title.toLowerCase().contains(s.toLowerCase())) {
                            temp.add(category);
                        }
                    }
                    results.values = temp;
                    results.count = temp.size();
                } else {
                    results.values = array;
                    results.count = array.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                beanSearchProducts = (ArrayList<BeanSearchProduct>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

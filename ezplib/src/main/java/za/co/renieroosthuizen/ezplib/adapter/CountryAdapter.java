package za.co.renieroosthuizen.ezplib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import za.co.renieroosthuizen.ezplib.R;
import za.co.renieroosthuizen.ezplib.model.Country;

public class CountryAdapter extends ArrayAdapter<Country> {

	private List<Country> _data;

	public List<Country> getData() {
		return _data;
	}

	public CountryAdapter(Context context, int textViewResourceId,
						  List<Country> objects) {
		super(context, textViewResourceId, objects);

		_data = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View row = convertView;
		ViewHolder holder = null;

		if(row == null)
		{
			LayoutInflater inflater = LayoutInflater.from(getContext());
			row = inflater.inflate(R.layout.adapter_country_layout, parent, false);

			holder = new ViewHolder();
			holder.txtCountryName = (TextView)row.findViewById(R.id.txtCountryName);
			holder.txtPhoneCode = (TextView)row.findViewById(R.id.txtPhoneCode);

			row.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)row.getTag();
		}

		Country value = _data.get(position);
		holder.txtCountryName.setText(value.getName());
		holder.txtPhoneCode.setText(value.getPhoneCode());

		return row;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) 
	{
		View row = convertView;
		ViewHolder holder = null;

		if(row == null)
		{
			LayoutInflater inflater = LayoutInflater.from(getContext());
			row = inflater.inflate(R.layout.adapter_country_layout, parent, false);

			holder = new ViewHolder();
			holder.txtCountryName = (TextView)row.findViewById(R.id.txtCountryName);
			holder.txtPhoneCode = (TextView)row.findViewById(R.id.txtPhoneCode);

			row.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)row.getTag();
		}

		Country value = _data.get(position);
		holder.txtCountryName.setText(value.getName());
		holder.txtPhoneCode.setText(value.getPhoneCode());

		return row;
	}
	
	


	public class ViewHolder
	{
		public TextView txtCountryName;
		public TextView txtPhoneCode;
	}
}

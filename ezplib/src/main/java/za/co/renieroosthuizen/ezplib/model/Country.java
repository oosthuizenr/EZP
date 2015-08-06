package za.co.renieroosthuizen.ezplib.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
@Data
public class Country {

	public String mCode;
	public String mName;
	public String mPhoneCode;
	
	
	public Country(String code, String name, String phoneCode) {
		super();
		this.mCode = code;
		this.mName = name;
		this.mPhoneCode = phoneCode;
	}

	
	
	
}

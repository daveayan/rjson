package rjson;

import rjson.domain.Account;
import rjson.domain.IgnoreDateTransformer;

public class Driver {
	public static void main(String[] args) {
		Account a = new Account();
		Rjson rjson = Rjson.newInstance().with(new IgnoreDateTransformer()).andIgnoreModifiers();
		String json = rjson.toJson(a);
		System.out.println(json);
	}
}
package com.uniovi.tests.pageobjects;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class PO_Properties {
	public PO_Properties() {
	}

	protected static String Path;
	protected static int SPANISH = 0;
	protected static int ENGLISH = 1;
	protected static Locale[] idioms = new Locale[] { new Locale("ES"), new Locale("EN") };

//static Properties p = new Properties();
	public static int getSPANISH() {
		return SPANISH;
	}

	public static int getENGLISH() {
		return ENGLISH;
	}

	@SuppressWarnings("static-access")
	public PO_Properties(String path) // throws FileNotFoundException, IOException
	{
		this.Path = path;
		// p.load(new FileReader(Path));
		// p.getProperty()
	}

	//
	// locale is de index in idioms array.
	//
	public String getString(String prop, int locale) {

		ResourceBundle bundle = ResourceBundle.getBundle(Path, idioms[locale]);
		String value = bundle.getString(prop);
		String result = "";
		try {
			result = new String(value.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}

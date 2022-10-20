package com.tadigital.leadActions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Formatingtocsv {

	
	/* package codechef; // don't place package name! */

	/* Name of the class has to be "Main" only if the class is public. */

	public String processData(String s) {
		// your code goes here
		int variableA = 2123;
		int comma = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ',') {
				comma = comma + 1;
			}
			if (comma == 5) {
				while (i < s.length() && !(s.charAt(i) == '1')) {
					i = i + 1;
				}

				s = s.substring(0, i) + "\n" + s.substring(i, s.length());
				comma = 0;
			}

		}
		return s;
	}

}

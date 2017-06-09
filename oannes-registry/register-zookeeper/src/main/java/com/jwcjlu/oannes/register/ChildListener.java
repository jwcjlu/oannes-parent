package com.jwcjlu.oannes.register;

import java.util.List;

public interface ChildListener {
	
	void childChanged(String path, List<String> children);
}

package entity;

import java.security.Principal;

public class PrincipalImpl implements Principal {

	private String name;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

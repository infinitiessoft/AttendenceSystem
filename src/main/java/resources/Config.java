package resources;

public class Config {

	public static String PAGE_SIZE = "pageSize";
	public static int DEFAULT_PAGE_SIZE = 10;

	private Config() {

	}

	public static String getProperty(String key) {
		if (PAGE_SIZE.equals(key)) {
			return String.valueOf(DEFAULT_PAGE_SIZE);
		}
		return null;
	}

}

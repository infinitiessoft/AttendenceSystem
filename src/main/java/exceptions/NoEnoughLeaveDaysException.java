package exceptions;

public class NoEnoughLeaveDaysException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoEnoughLeaveDaysException(String type, double duration,
			double used, double total, long year) {
		super(
				String.format(
						"You don't have enough available %s %s leave days.(used:%s, total:%s in year %s)",
						duration, type, used, total, year));
	}

}

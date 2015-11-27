package decaf.error;
import decaf.Location;

public class IncompatConOpError extends DecafError {
	
	private String left;

	private String right;
	
	public IncompatConOpError(Location location, String left, String right) {
		super(location);
		// TODO Auto-generated constructor stub
		this.left = left;
		this.right = right;
	}

	@Override
	protected String getErrMsg() {
		// TODO Auto-generated method stub
		return "incompatible condition operates: " + 
				left + " and " + right;
	}

}

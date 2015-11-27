package decaf.error;
import decaf.Location;

/**
 * in the context of ternary operation
 * a=b?c:d;
 * b must have bool type, if not, trigger this error
 * PA2
 */
public class TestTypeError extends DecafError {

	public TestTypeError(Location location) {
		super(location);
		// TODO Auto-generated constructor stub		
	}

	@Override
	protected String getErrMsg() {
		// TODO Auto-generated method stub
		return "test expression must have bool type";
	}

}

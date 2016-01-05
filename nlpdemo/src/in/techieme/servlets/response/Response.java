package in.techieme.servlets.response;

public class Response {

	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";

	private String status;
	private Object result;

	public Response(String s, Object r) {
		this.status = s;
		this.result = r;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}

package ar.com.cipres.services.impl.test;

public class TestExecption {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			Integer.parseInt("e");
		}catch
		(Exception e){
			System.out.println(e.getCause().getMessage());
		}
	}

}

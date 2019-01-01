package retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{

	
	int minretryCount=0;
	int maxretryCount=2;
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		
		if(minretryCount<=maxretryCount) {
			System.out.println("Following Test is failing="+result.getName());
			System.out.println("Retrying count="+(minretryCount+1));
			minretryCount++;
			return true;
		}
		return false;
	}

}

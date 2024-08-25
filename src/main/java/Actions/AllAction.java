package Actions;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class AllAction {

	public static void selectMethod(WebElement ele,String value) throws IOException {
	Select s = new Select(ele);
	s.selectByVisibleText(value);
	}
	
}

package com.copApk.AllBrands;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.utils.AppiumUtility;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ModalPageMob2 {
	@FindBy(id = "com.aviansoft.caronphone.dev:id/txtColorName")
	private WebElement colorname;
	@FindBy(id = "com.aviansoft.caronphone.dev:id/imgColorNext")
	private WebElement colornNextbtn;
	@FindBy(id = "com.aviansoft.caronphone.dev:id/rcvVarient")
	private WebElement recyclerViewVariant;
	@FindBy(xpath = "//android.widget.TextView[@text=\"Detailed Specification\"]")
	private List<WebElement> DetailSpecHeading;

	@FindBy(xpath = "//android.widget.TextView[@resource-id='com.aviansoft.caronphone.dev:id/txtCarStageName' and @text='All ']")
	private WebElement allBtn;
	@FindBy(id = "//android.widget.TextView[@resource-id='com.aviansoft.caronphone.dev:id/txtCarStageName' and @text='Features']")
	private WebElement featurebtn;
	@FindBy(id = "com.aviansoft.caronphone.dev:id/selectionView")
	private List<WebElement> colorLst;
	@AndroidFindBy(uiAutomator = "new UiSelector().text('Fuel')")
	private WebElement impSpec;
	@FindBy(xpath = "//android.widget.LinearLayout[@resource-id='com.aviansoft.caronphone.dev:id/llFeature']")
	private List<WebElement> impSpecLst;
	@FindBy(xpath = "//android.widget.LinearLayout[@resource-id='com.aviansoft.caronphone.dev:id/childRoot']")
	private List<WebElement> specRowLst;
	@FindBy(id = "com.aviansoft.caronphone.dev:id/fabScrollUp")
	private WebElement scrollUpBtn;
	WebDriverWait wait;

	public ModalPageMob2(AppiumDriver driverMob) {
		PageFactory.initElements(new AppiumFieldDecorator(driverMob, Duration.ofSeconds(10)), this);
		wait = new WebDriverWait(driverMob, Duration.ofSeconds(10));
	}

	public LinkedHashMap<String, ArrayList<String>> getAllVariantsLst(AppiumDriver driverMob, int startX, int startY,
			int endY) throws InterruptedException {
		WebElement el = AppiumUtility.scrollUptoText(driverMob, "Variants");
		AppiumUtility.alignElementToTopW3C(driverMob, el);
		LinkedHashMap<String, ArrayList<String>> lmp = new LinkedHashMap<String, ArrayList<String>>();
		Set<String> seenVariants = new LinkedHashSet<>(); // Maintain insertion order
		int previousSize = -1;
		while (seenVariants.size() != previousSize) {
			previousSize = seenVariants.size();
			List<WebElement> visibleItems = recyclerViewVariant
					.findElements(By.id("com.aviansoft.caronphone.dev:id/llSelectionVariant"));
			for (WebElement item : visibleItems) {
				try {
					String v1 = item.findElement(By.id("com.aviansoft.caronphone.dev:id/txtVariantName")).getText();
					System.out.println(v1);
					if (!v1.isEmpty() && !seenVariants.contains(v1)) {
						seenVariants.add(v1);
						String sp = item.findElement(By.id("com.aviansoft.caronphone.dev:id/txtVariantFeatures"))
								.getText();
						String pr = item.findElement(By.id("com.aviansoft.caronphone.dev:id/txtVariantPrice"))
								.getText();
						System.out.println(v1 + "==>" + sp + pr);
						ArrayList<String> al = new ArrayList<String>();
						String[] sp1 = sp.split(", ");
						al.addAll(Arrays.asList(sp1[0], sp1[1], sp1[2], sp1[3], pr));
						lmp.put(v1, al);
					}
				} catch (Exception e) {
					// Handle stale element or visibility issue
					continue;
				}
			}
			// Scroll to reveal more items
			if (driverMob.getPageSource().contains("know your car")) {
				break;
			}
			AppiumUtility.performScrollWithScreenDimensions(driverMob, startX, startY, endY);
			// AppiumUtility.scrollUp(driverMob);
			// AppiumUtility.scrollDownRecyclerView(driverMob, recyclerViewVariant);
			Thread.sleep(200); // wait for scroll animation (tweak as needed)
			System.out.println("Swiped up inside recycler view");
		}
		System.out.println(seenVariants.toString());
		System.out.println(seenVariants.size());
		return lmp;
	}

	public LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> getAllVariantsToCompare(
			AppiumDriver driverMob, int startX, int startY, int endY) throws InterruptedException {
		LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> LMP = new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();
		WebElement el = AppiumUtility.scrollUptoText(driverMob, "Variants");
		AppiumUtility.alignElementToTopW3C(driverMob, el);
		List<WebElement> BtnLst = driverMob
				.findElements(AppiumBy.id("com.aviansoft.caronphone.dev:id/txtCarStageName"));
		ArrayList<String> btnNameLst = new ArrayList<String>();
		for (WebElement btn : BtnLst) {
			btn.click();
			String btnNm = btn.getText().trim();
			btnNameLst.add(btnNm);
			LMP.put(btnNm, getAllVariantsLst(driverMob, startX, startY, endY));
			scrollUpBtn.click();
		}
		System.out.println(btnNameLst.toString());
		return LMP;
	}

	public LinkedHashMap<String, String> getSpec(AppiumDriver driverMob, int startX, int startY, int endY) {
		LinkedHashMap<String, String> seenItems = new LinkedHashMap<>();
		Set<String> seenLabels = new HashSet<>();
		int sameCountAttempts = 0;
		int previousSize = 0;
		while (sameCountAttempts < 1) { // limit to avoid infinite scrolling
			List<WebElement> specRows = driverMob.findElements(By.id("com.aviansoft.caronphone.dev:id/childRoot"));
//			if (specRows.size() == 0) {
//				System.out.println("getSpec:specRows no found");
//				// el.click();
//				// scrollUpBtn.click();
//				break;
//			}
			for (WebElement row : specRows) {
				try {
					String label = row.findElement(By.id("com.aviansoft.caronphone.dev:id/txtSpecHeading")).getText();
					String value = row.findElement(By.id("com.aviansoft.caronphone.dev:id/txtSpecValue")).getText();
					// Only add new entries
					if (!seenLabels.contains(label)) {
						seenItems.put(label, value);
						seenLabels.add(label);
						System.out.println("item " + seenItems.size() + " => " + label + " = " + value);
					}
				} catch (NoSuchElementException e) {
					// Element not found in this row, skip
				}
			}
			if (driverMob.getPageSource().contains("Ex-showroom Price")) {
				break;
			}
			AppiumUtility.performScrollWithScreenDimensions(driverMob, startX, startY, endY);
			if (seenItems.size() == previousSize) {
				sameCountAttempts++; // no new items found
			} else {
				sameCountAttempts = 0; // reset if new items were added
				previousSize = seenItems.size();
			}
			// swipeUp(driverMob);
//			WebElement recyclerView = driverMob
//					.findElement(AppiumBy.id("com.aviansoft.caronphone.dev:id/rcvFeaturesList"));
//			AppiumUtility.scrollDownRecyclerView(driverMob, recyclerView);
//			System.out.println("getSpec:swipeUpInElement");
		}
		return seenItems;
	}

	public LinkedHashMap<String, String> getImpSpec(AppiumDriver driverMob) {
		LinkedHashMap<String, String> seenItems = new LinkedHashMap<>();
		Set<String> seenLabels = new HashSet<>();
		List<WebElement> specRows = driverMob.findElements(By.id("com.aviansoft.caronphone.dev:id/childRoot"));
		for (WebElement row : specRows) {
			try {
				String label = row.findElement(By.id("com.aviansoft.caronphone.dev:id/txtSpecHeading")).getText();
				String value = row.findElement(By.id("com.aviansoft.caronphone.dev:id/txtSpecValue")).getText();
				// Only add new entries
				if (!seenLabels.contains(label)) {
					seenItems.put(label, value);
					seenLabels.add(label);
					System.out.println("item " + seenItems.size() + " => " + label + " = " + value);
				}
			} catch (NoSuchElementException e) {
				// Element not found in this row, skip
			}
		}
		return seenItems;
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> clickAllImpSpecGetInfo(AppiumDriver driverMob,
			int startX, int startY, int endY) throws InterruptedException {
		String[] ele = { "Fuel", "Transmission", "Engine", "Performance", "Suspension & Steering", "Wheels",
				"Dimensions", "Weights & Capacity", "Warranty" };
		AppiumUtility.alignElementToTopW3C(driverMob, AppiumUtility.scrollUptoText(driverMob, "Specifications"));
		LinkedHashMap<String, LinkedHashMap<String, String>> LMP = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		Set<String> seenVariants = new LinkedHashSet<>(); // Maintain insertion order
		int previousSize = -1;
		while (seenVariants.size() != previousSize) {
			previousSize = seenVariants.size();
			List<WebElement> visibleItems = driverMob.findElements(By.id("com.aviansoft.caronphone.dev:id/llFeature"));
			int i = 0;
			for (WebElement item : visibleItems) {
				try {
					AppiumUtility.alignElementToTopW3C(driverMob, item);
					String s1 = item.getDomAttribute("content-desc");
					Assert.assertEquals(s1, ele[i]);
					i++;
					System.out.println(s1);
					if (!s1.isEmpty() && !seenVariants.contains(s1)) {
						seenVariants.add(s1);
						item.click();
						List<WebElement> spec = driverMob
								.findElements(By.id("com.aviansoft.caronphone.dev:id/childRoot"));
						LinkedHashMap<String, String> lmp = new LinkedHashMap<String, String>();
						for (WebElement sp : spec) {
							String spNm = sp.findElement(By.id("com.aviansoft.caronphone.dev:id/txtSpecHeading"))
									.getText();
							String spVal = sp.findElement(By.id("com.aviansoft.caronphone.dev:id/txtSpecValue"))
									.getText();
							lmp.put(spNm, spVal);
						}
						LMP.put(s1, lmp);
						item.click();
					}
				} catch (Exception e) {
					// Handle stale element or visibility issue
					continue;
				}
			}
			// Scroll to reveal more items
			if (driverMob.getPageSource().contains("Ex-showroom Price")) {
				break;
			}
			AppiumUtility.performScrollWithScreenDimensions(driverMob, startX, startY, endY);
			// AppiumUtility.scrollUp(driverMob);
			// AppiumUtility.scrollDownRecyclerView(driverMob, recyclerViewVariant);
			Thread.sleep(200); // wait for scroll animation (tweak as needed)
			System.out.println("Swiped up inside recycler view");
		}
		System.out.println(seenVariants.toString());
		scrollUpBtn.click();
		return LMP;
	}

	public ArrayList<String> getModalPageMobColors() throws InterruptedException {
		Thread.sleep(2000);
		ArrayList<String> al = new ArrayList<String>();
		for (WebElement cl : colorLst) {
			al.add(colorname.getText());
			colornNextbtn.click();
			Thread.sleep(1000);
		}
		return al;
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> getModalPageMobFeature(AppiumDriver driverMob,
			int startX, int startY, int endY) {
		LinkedHashMap<String, LinkedHashMap<String, String>> LMP = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		AppiumUtility.scrollUptoText(driverMob, "Features").click();
		AppiumUtility.alignElementToTopW3C(driverMob, impSpecLst.get(0));
		impSpecLst.get(0).click();
		LMP.put("Interior", getSpec(driverMob, startX, startY, endY));
		scrollUpBtn.click();
		AppiumUtility.scrollUptoText(driverMob, "Features").click();
		AppiumUtility.alignElementToTopW3C(driverMob, impSpecLst.get(1));
		impSpecLst.get(1).click();
		LMP.put("Exterior", getSpec(driverMob, startX, startY, endY));
		scrollUpBtn.click();
		return LMP;
	}

	public LinkedHashMap<String, String> getModalPageMobSafetySpec(AppiumDriver driverMob, int startX, int startY,
			int endY) {
		AppiumUtility.scrollUptoText(driverMob, "Safety").click();
		AppiumUtility.alignElementToTopW3C(driverMob, impSpecLst.get(0));
		impSpecLst.get(0).click();
		LinkedHashMap<String, String> safety = getSpec(driverMob, startX, startY, endY);
		return safety;
	}

	public void elementInRecyclerView(AppiumDriver driver, String text) {
		WebElement recyclerView = driver.findElement(AppiumBy.id("com.aviansoft.caronphone.dev:id/rcvFeaturesList"));
		boolean found = false;
		while (!found) {
			List<WebElement> elements = recyclerView
					.findElements(AppiumBy.id("com.aviansoft.caronphone.dev:id/txtHead"));
			for (WebElement el : elements) {
				if (el.getText().equalsIgnoreCase(text)) {
					found = true;
					el.click();
					break;
				}
			}
			if (!found) {
				// Scroll inside recyclerView
				AppiumUtility.scrollDownRecyclerView(driver, recyclerView);
			}
		}
	}
}

package com.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import static java.time.Duration.ofMillis;
import java.time.Duration;
import java.util.Arrays;

public class AppiumUtility {
	public static void performScrollWithScreenDimensions(AppiumDriver driver, int startX, int startY, int endY) {
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence swipe = new Sequence(finger, 1);
		swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
		swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), startX, endY));
		swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		driver.perform(Arrays.asList(swipe));
	}

	public static void scrollUp(AppiumDriver driver) {
		Dimension size = driver.manage().window().getSize();
		int startX = size.width / 2;
		int startY = (int) (size.height * 0.75);
		int endY = (int) (size.height * 0.25);
		performScrollWithScreenDimensions(driver, startX, startY, endY);
	}

	public static boolean isElementInViewport(AppiumDriver driver, WebElement element) {
		try {
			// Get element's position and size
			Point elementLocation = element.getLocation();
			Dimension elementSize = element.getSize();
			int elementTop = elementLocation.getY();
			int elementBottom = elementTop + elementSize.getHeight();
			// Get device screen height (viewport)
			Dimension screenSize = driver.manage().window().getSize();
			int screenHeight = screenSize.getHeight();
			// Adjust for Android status bar (optional, depends on your layout)
			int visibleTop = 0;
			int visibleBottom = screenHeight;
			// Return true if the element is within vertical screen bounds
			return (elementTop >= visibleTop && elementBottom <= visibleBottom);
		} catch (Exception e) {
			System.out.println("Element not in viewport or not present: " + e.getMessage());
			return false;
		}
	}

	public static void scrollDownRecyclerView(AppiumDriver driver, WebElement recyclerView) {

		// Get location and size of RecyclerView
		Point location = recyclerView.getLocation();
		Dimension size = recyclerView.getSize();
		int startX = location.getX() + size.getWidth() / 2;
		int startY = location.getY() + (int) (size.getHeight() * 0.8);
		int endY = location.getY() + (int) (size.getHeight() * 0.2);
		performScrollWithScreenDimensions(driver, startX, startY, endY);
	}

	public static void scrollUpRecyclerView(AppiumDriver driver, WebElement recyclerView) {
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence swipe = new Sequence(finger, 1);
		// Get location and size of RecyclerView
		Point location = recyclerView.getLocation();
		Dimension size = recyclerView.getSize();
		int startX = location.getX() + size.getWidth() / 2;
		int startY = location.getY() + (int) (size.getHeight() * 0.8);
		int endY = location.getY() + (int) (size.getHeight() * 0.2);
		// Create a swipe up gesture
		swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
		swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		swipe.addAction(finger.createPointerMove(ofMillis(700), PointerInput.Origin.viewport(), startX, endY));
		swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		// Perform the action
		driver.perform(Arrays.asList(swipe));
	}

	public static WebElement scrollUptoText(AppiumDriver driverMob, String text) {
		// Scroll to and click "Fuel"
		return driverMob.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
				+ ".scrollIntoView(new UiSelector().text(\"" + text + "\"))"));
	}

	public static void alignElementToTopW3C(AppiumDriver driver, WebElement element) {
		// Get screen size
		Dimension screenSize = driver.manage().window().getSize();
		int screenHeight = screenSize.getHeight();
		int screenWidth = screenSize.getWidth();
		// Calculate desired Y position (30% from top)
		int targetY = (int) (screenHeight * 0.3);
		// Get element position
		Point elementLocation = element.getLocation();
		int elementY = elementLocation.getY();
		// ✅ Check if the element is already in the top 30% of the screen
		if (elementY <= targetY) {
			return; // No need to scroll
		}
		// Calculate how much we need to move the element
		int deltaY = elementY - targetY;
		// If delta is small, skip swipe
		if (Math.abs(deltaY) < 5)
			return;
		// Swipe parameters
		int startX = screenWidth / 2;
		int startY = startX + 300; // middle of screen or below
		int endY = startY + (-deltaY); // move by -deltaY to align element
		// Clamp swipe within screen
		startY = Math.min(screenHeight - 10, Math.max(10, startY));
		endY = Math.min(screenHeight - 10, Math.max(10, endY));
		performScrollWithScreenDimensions(driver, startX, startY, endY);
	}

}

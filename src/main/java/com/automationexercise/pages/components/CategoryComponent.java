package com.automationexercise.pages.components;

import com.automationexercise.pages.BasePage;
import com.automationexercise.pages.CategoryPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component Object for the Category sidebar.
 * Present on home page and product pages - encapsulates category
 * navigation locators and interactions in one place (DRY).
 * <p>
 * Usage:
 *  homePage.category().clickCategory("Women");
 *  homePage.category().clickSubCategory("Women", "Dress");
 */
public class CategoryComponent extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(CategoryComponent.class);

    // ==================== LOCATORS ====================
    private static final By CATEGORY_SIDEBAR = By.cssSelector(".left-sidebar #accordian");

    // Dynamic XPath templates - use buildLcator() for substituted
    private static final String CATEGORY_TMPL     = "//a[@href='#%s']";
    private static final String SUB_CATEGORY_TMPL = "//div[@id='%s']//a[contains(text(),'%s')]";

    // ==================== ACTIONS ====================

    /**
     * Verifies the category sidebar is visible on the page.
     *
     * @return true sidebar is visible
     */
    @Step("Verify category sidebar is visible")
    public boolean isCategorySidebarVisible() {
        boolean visible = isDisplayed(CATEGORY_SIDEBAR);
        log.info("Category sidebar visible: {}", visible);
        return visible;
    }

    /**
     * Clicks a top-level category to expand its subcategories.
     * Uses dynamic locator with via buildLocator() - works for any category name.
     *
     * @param categoryName category name matching href (e.g. "Women", "Men", "Kids")
     */
    @Step("Click '{categoryName}' category")
    public void clickCategory(String categoryName) {
        log.info("Clicking '{}' category", categoryName);
        click(buildLocator(CATEGORY_TMPL, categoryName));
    }

    /**
     * Clicks a subcategory link under a given parent category.
     * Parent category must be expanded before calling this method.
     *
     * @param parentCategory parent category id (e.g. "Women", "Men")
     * @param subCategory    subcategory name (e.g. "Dress", "Tops", "Jeans")
     * @return CategoryPage instance
     */
    @Step("Click subcategory '{subCategory}' under '{parentCategory}'")
    public CategoryPage clickSubCategory(String parentCategory, String subCategory) {
        log.info("Clicking subcategory '{}' under '{}'", subCategory, parentCategory);
        click(buildLocator(SUB_CATEGORY_TMPL, parentCategory, subCategory));
        return new CategoryPage();
    }
}
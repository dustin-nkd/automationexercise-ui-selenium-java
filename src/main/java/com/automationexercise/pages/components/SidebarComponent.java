package com.automationexercise.pages.components;

import com.automationexercise.pages.BasePage;
import com.automationexercise.pages.ProductListPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component Object for the left sidebar.
 * Handles both Category and Brand navigation - present on
 * Products page, Category page and Brand page.
 * <p>
 * Replaces CategoryComponent and BrandComponent - unified sidebar.
 * component with dynamic locators for any category or brand (DRY).
 * <p>
 * Usage:
 *  productsPage.sidebar().isCategorySidebarVisible();
 *  productsPage.sidebar().clickCategory("Women");
 *  productsPage.sidebar().clickSubCategory("Women", "Dress");
 *  productsPage.sidebar().clickBrand("Polo");
 */
public class SidebarComponent extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(SidebarComponent.class);

    // ==================== LOCATORS ====================

    private static final By CATEGORY_SIDEBAR = By.cssSelector(".left-sidebar #accordian");
    private static final By BRAND_SIDEBAR    = By.cssSelector(".brands_products");

    // Dynamic locator templates
    private static final String CATEGORY_TMPL     = "//a[@href='#%s']";
    private static final String SUB_CATEGORY_TMPL = "//div[@id='%s']//a[contains(text(),'%s')]";
    private static final String BRAND_LINK_TMPL   = "//a[contains(@href,'/brand_products/%s')]";

    // ==================== CATEGORY ACTIONS ====================

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
    public ProductListPage clickSubCategory(String parentCategory, String subCategory) {
        log.info("Clicking subcategory '{}' under '{}'", subCategory, parentCategory);
        click(buildLocator(SUB_CATEGORY_TMPL, parentCategory, subCategory));
        return new ProductListPage();
    }

    // ==================== BRAND ACTIONS ====================

    /**
     * Verifies the brand sidebar is visible on the page.
     *
     * @return true if brand sidebar is visible
     */
    @Step("Verify brand sidebar is visible")
    public boolean isBrandSidebarVisible() {
        boolean visible = isDisplayed(BRAND_SIDEBAR);
        log.info("Brand sidebar visible: {}", visible);
        return visible;
    }

    /**
     * Clicks a brand link by name and returns ProductListPage.
     *
     * @param brandName brand name matching URL (e.g. "Polo", "H&M", "Madame")
     * @return ProductListPage instance
     */
    @Step("Click brand: '{brandName}'")
    public ProductListPage clickBrand(String brandName) {
        log.info("Clicking brand: '{}'", brandName);
        click(buildLocator(BRAND_LINK_TMPL, brandName));
        return new ProductListPage();
    }
}
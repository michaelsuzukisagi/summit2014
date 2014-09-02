/*
 * Copyright (C) 2005-2012 Alfresco Software Limited.
 * This file is part of Alfresco
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alfresco.demo.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage
{
    private static final By USERNAME_INPUT_LOCATOR = By.id("loginForm:user-name"); 
    private static final By PASSWORD_INPUT_LOCATOR = By.id("loginForm:user-password");
    private static final By SUBMIT_BTN_LOCATOR = By.id("loginForm:submit");
    private WebDriver driver;
    
    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
    }
    
    public void login(final String username, final String password)
    {
        WebElement usernameInput = driver.findElement(USERNAME_INPUT_LOCATOR);
        usernameInput.sendKeys(username);
        WebElement passwordInput = driver.findElement(PASSWORD_INPUT_LOCATOR);
        passwordInput.sendKeys(password);
        WebElement loginBtn = driver.findElement(SUBMIT_BTN_LOCATOR);
        loginBtn.click();
    }

}

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
package org.alfresco.demo.func;

import org.alfresco.demo.po.HelloWorldPage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(JUnit4.class)
public class HelloWorld
{
    private static WebDriver driver;
    
    @BeforeClass
    public static void setup()
    {
        driver = new FirefoxDriver();
        driver.get("http://localhost:8080/alfresco/service/api/login?u=admin&pw=admin");
        WebElement ticket = driver.findElement(By.tagName("ticket"));
        String token =String.format("?alf_ticket=%s",ticket.getText());
        
        driver.get("http://localhost:8080/alfresco/service/summit/hello" + token);
    }
    @AfterClass
    public static void teardown()
    {
        driver.quit();
    }
    @Test
    public void titleDisplayed()
    {
        HelloWorldPage page = new HelloWorldPage(driver);
        Assert.assertNotNull(page);
        Assert.assertTrue(page.isTitleVisible());
        Assert.assertEquals("Welcome to Summit 2014", page.getTitle());
    }
    /**
     * Message should include the directory name
     * and number of folders currently in that directory.
     */
    @Test
    public void messageIsDisplayed()
    {
        HelloWorldPage page = new HelloWorldPage(driver);
        Assert.assertNotNull(page);
        Assert.assertTrue(page.isMessageVisible());
        Assert.assertEquals("Company Home has 7 folders", page.getMessage());
    }
}

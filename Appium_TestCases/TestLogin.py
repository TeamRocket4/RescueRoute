from appium import webdriver
import time

# Set up desired capabilities
desired_caps = {
    'platformName': 'Android',
    'platformVersion': '12',  # Your emulator's Android version
    'deviceName': 'Android Emulator',
    'appPackage': 'com.ambulance',  # App package name
    'appActivity': 'com.ambulance.MainActivity',  # App main activity
    'noReset': True,
    'automationName': 'UiAutomator2'
}

# Initialize the WebDriver and connect to the Appium server
driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)

# Wait for the app to load
time.sleep(5)

# Find login fields and interact with them
# Assuming the login button and fields are identified by their resource IDs or accessibility ids
username_field = driver.find_element_by_id('com.ambulance:id/username')  # Replace with actual ID
password_field = driver.find_element_by_id('com.ambulance:id/password')  # Replace with actual ID
login_button = driver.find_element_by_id('com.ambulance:id/login_button')  # Replace with actual ID

# Enter the credentials and click the login button
username_field.send_keys('your_username')
password_field.send_keys('your_password')
login_button.click()

# Wait for the result (you can adjust the wait time as needed)
time.sleep(5)

# Verify successful login by checking an element on the home screen after login
try:
    success_element = driver.find_element_by_id('com.ambulance:id/success_element')  # Replace with actual ID
    print("Login successful")
except:
    print("Login failed")

# Close the driver after the test
driver.quit()

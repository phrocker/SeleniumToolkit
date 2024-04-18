# SeleniumToolkit

Toolkit for Selenium testing. This is a project I've maintained for quite some time; however, after using it on a professional project
I've decided to make it public. The Test directory contains a demo agains TestSauceDemo. It simply logs in and does very simple validation
but it functions as an example.

## Yaml DEMO

The demo contains two tests, validLogin and invalidLogin. The RunOrder will be executed in order. The startUrl and stopUrl
define where to begin and where to end ( in case you wish to log out ).

invalidLogin begins by filling in Fields with an incorrect password. The expectation after submission is that
we stay at the same URL, while the validLogin expects to to be directed to an inventory page, after which we will
search for the corresponding IDs to log out

```yaml
variables:
  baseUrl: https://saucedemo.com
RunOrder:
    - validLogin
    - invalidLogin
invalidLogin:
  startUrl: ${baseUrl}/
  actions:
    - IdWait:
        id: login-button
    - FieldFill:
        field: user-name
        value: standard_user
    - FieldFill:
        field: password
        value: fake-password
    - SubmitButton:
        buttonName: login-button
    - ValidateUrl:
        url: /

validLogin:
  startUrl: ${baseUrl}/
  actions:
    - IdWait:
        id: login-button
    - FieldFill:
        field: user-name
        value: standard_user
    - FieldFill:
        field: password
        value: secret_sauce
    - SubmitButton:
        buttonName: login-button
    - ValidateUrl:
        url: inventory.html
    - ClickButton:
        buttonName: bm-burger-button
        type: class
    - IdWait:
        id: logout_sidebar_link
    - NavigateLink:
        id: logout_sidebar_link

```

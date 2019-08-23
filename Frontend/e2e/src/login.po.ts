import { browser, by, element } from 'protractor';

export class LoginPage {
    navigateTo() {
        return browser.get('/');
    }

    getUserNameTextbox() {
        return element(by.name('userName'));
    }

    getPasswordTextbox() {
        return element(by.name('userPassword'));
    }

    getForm() {
        return element(by.css('#loginForm'));
    }
}

import { LoginPage } from './login.po';

describe('Login tests', () => {
    let page: LoginPage;    beforeEach(() => {
        page = new LoginPage();
        page.navigateTo();
        });


    it('Login form should be valid', () => {
        page.getUserNameTextbox().sendKeys('Kokza');
        page.getPasswordTextbox().sendKeys('12345678');
        const form = page.getForm().getAttribute('class');
        expect(form).toContain('ng-valid');
        });

    it('Login form should be invalid', () => {
        page.getUserNameTextbox().sendKeys('');
        page.getPasswordTextbox().sendKeys('');

        const form = page.getForm().getAttribute('class');

        expect(form).toContain('ng-invalid');
    });

    it('Login form should be invalid', () => {
        page.getUserNameTextbox().sendKeys('**');
        page.getPasswordTextbox().sendKeys('');

        const form = page.getForm().getAttribute('class');

        expect(form).toContain('ng-invalid');
    });

    it('Login form should be invalid', () => {
        page.getUserNameTextbox().sendKeys('');
        page.getPasswordTextbox().sendKeys('//');

        const form = page.getForm().getAttribute('class');

        expect(form).toContain('ng-invalid');
    });
});



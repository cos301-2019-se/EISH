import {HttpInterceptor, HttpRequest, HttpHandler, HttpEvent} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable()
export class AccessControlInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const key = 'accessToken';
        const tokenType = 'Bearer ';
        const userToken = sessionStorage.getItem(key);

        if (userToken == null) {
            return next.handle(req);
        }

        const newHttpRequest = req.clone({
            headers: req.headers.set('Authorization', tokenType + userToken),
        });

        return next.handle(newHttpRequest);
    }

}

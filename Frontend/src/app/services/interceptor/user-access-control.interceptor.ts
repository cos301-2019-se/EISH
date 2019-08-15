import {HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';


@Injectable()
export class AccessControlInterceptor implements HttpInterceptor {
    constructor(private route: Router) {}
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

        return next.handle(newHttpRequest).pipe(
            catchError((error: HttpErrorResponse) => {
                if (error.status === 401) {
                    sessionStorage.clear();
                    this.route.navigate(['/']);
                }
                // tslint:disable-next-line: deprecation
                return Observable.throw(error.statusText);
            })
        );
    }

}

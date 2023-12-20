import {Injectable} from "@angular/core";
import {HttpInterceptor, HttpHandler, HttpRequest, HttpEvent, HttpErrorResponse} from "@angular/common/http";
import {catchError, Observable, switchMap, throwError} from "rxjs";
import {AuthService} from "../pages/auth/login/services/auth.service";
import {TokenService} from "../services/token.service";
import {Router} from "@angular/router";

@Injectable()
export class MainHttpInterceptor implements HttpInterceptor {
  public storage: Storage = localStorage;
  constructor(private authService: AuthService, private tokenService: TokenService, private router: Router) {}
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('access_token') ?? null;
    return next.handle(this.addToken(req, token)).pipe(
      catchError(error => {
        if (error instanceof HttpErrorResponse) {
          if (error.status === 403) {
            return localStorage.getItem('refresh_token') ? this.refreshToken(req, next) : this.logoutUser();
          }
          return throwError(() => new Error(''));
        } else return throwError(() => new Error(''));
      })
    )
  }

  refreshToken(req: HttpRequest<any>, next: HttpHandler) {
    this.tokenService.removeAccessToken();
    let refreshToken = this.tokenService.getRefreshToken();
    if (refreshToken) {
      return this.authService.refreshToken(refreshToken).pipe(switchMap(authResponse => {
        if (authResponse && authResponse.accessToken && authResponse.refreshToken) {
          this.tokenService.storeTokenInfo(authResponse);
          return next.handle(this.addToken(req, authResponse.accessToken));
        } else {
          this.logoutUser();
          return next.handle(req);
        }
      }));
    }
    this.logoutUser();
    return next.handle(req);
  }

  private logoutUser() {
    this.storage.removeItem('access_token');
    this.storage.removeItem('refresh_token');
    this.storage.removeItem('current_user_role');
    this.router.navigate(['/login']);
    return throwError(() => new Error(''));
  }

  private addToken(req: HttpRequest<any>, accessToken: any) {
    return accessToken ? req.clone({setHeaders: {Authorization: 'Bearer ' + accessToken ?? ""}}) : req;
  }
}
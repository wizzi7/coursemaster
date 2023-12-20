import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpClientModule} from '@angular/common/http';
import {TokenService} from './services/token.service';
import {RegisterModule} from "./pages/auth/register/register.module";
import {RegisterService} from './pages/auth/register/services/register.service';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {MainHttpInterceptor} from "./interceptors/main-http.interceptor";
import {AuthService} from "./pages/auth/login/services/auth.service";
import {UrlGuardService} from "./services/url-guard.service";
import {UserService} from "./services/user.service";
import {CryptoService} from "./services/crypto.service";
import {SearchService} from "./services/search.service";

@NgModule({
  declarations: [
    AppComponent
  ],
  providers: [
    TokenService,
    RegisterService,
    AuthService,
    UrlGuardService,
    UserService,
    CryptoService,
    SearchService,
    {provide: HTTP_INTERCEPTORS, useClass: MainHttpInterceptor, multi: true}
  ],
  bootstrap: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    NgbModule,
    HttpClientModule,
    RegisterModule
  ]
})
export class AppModule {
}

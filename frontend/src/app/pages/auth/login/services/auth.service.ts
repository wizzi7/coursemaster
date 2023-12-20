import {Injectable} from "@angular/core";
import {AuthenticationRequest} from "../models/authentication-request";
import {AuthenticationResponse} from "../../models/authentication-response";
import {ApiSettings} from "../../../../global/api-settings";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "../../../../services/token.service";
import {RefreshToken} from "../models/refresh-token";

@Injectable()
export class AuthService {
  public storage: Storage = localStorage;

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  authenticate(authenticationRequest: AuthenticationRequest) {
    return this.http.post<AuthenticationResponse>(ApiSettings.AUTH_SERVICE_AUTHENTICATE, authenticationRequest);
  }

  refreshToken(token: string) {
    return this.http.post<AuthenticationResponse>(ApiSettings.AUTH_SERVICE_REFRESH_TOKEN, new RefreshToken(token));
  }

  logout() {
    return this.http.post<AuthenticationResponse>(ApiSettings.AUTH_SERVICE_LOGOUT, {});
  }

  loggedIn(): boolean {
    return this.tokenService.getAccessToken() != null;
  }
}